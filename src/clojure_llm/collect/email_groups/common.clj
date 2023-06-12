(ns clojure-llm.collect.email-groups.common
  (:require
   [clojure.data.json :as json]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [clojure-llm.collect.data.names :as names]
   [clojure-llm.collect.data.toxic :as toxic]
   [reaver]
   [injest.path :refer [x>> =>>]]
   [skyscraper.core :as core :refer [defprocessor]]))

(defn remove-message [body]
  (first (str/split body #"You received this message")))

(defn remove-dashes [body]
  (first (str/split body #"--~--~---------")))

(defn get-non-space-tokens [s]
  (=>> s
       (partition-by (complement #{\space \newline}))
       (filter (complement #(= \space (first %))))
       (map #(apply str %))))

(defn might-have-code? [body]
  (when body
    (and
     (str/includes? body "(")
     (str/includes? body ")"))))

(defn attach-data [m]
  (let [s1 (get m "subject")
        s2 (-> s1 (str/replace "[ClojureScript]" ""))
        reply? (or (str/starts-with? s2 "Re:")
                   (str/starts-with? s2 "RE:")
                   (str/starts-with? s2 "re:")
                   (str/starts-with? s2 " Re:")
                   (str/starts-with? s2 " RE:")
                   (str/starts-with? s2 " re:"))
        subject0 (->> s2 (drop-last 32) (apply str))
        subject1 (if-not reply?
                   subject0
                   (apply str (drop 4 subject0)))
        subject-tokens (get-non-space-tokens subject1)
        short? (< (count subject-tokens) 3)
        [subject author] (if short?
                           [(first subject-tokens) (last subject-tokens)]
                           [(apply str (interpose " " subject-tokens)) (apply str (interpose " " (take-last 2 subject-tokens)))])
        [author-first-name author-last-name] (get-non-space-tokens author)
        body (some->> (get m "body") remove-message remove-dashes)
        toxic-body? (seq (filter toxic/bad-names (get-non-space-tokens body)))] ;; check for toxicity
    (-> m
        (assoc :timestamp (->> s2 (take-last 31) (apply str)))
        (assoc :og-subj subject0)
        (assoc :subject subject)
        (assoc :sub-match (apply str (take 15 subject))) ;; match on first 15 characters -- we don't have stable subject end strings
        (assoc :author author)
        (assoc :toxic? (boolean (first toxic-body?)))
        (assoc :author-first-name author-first-name)
        (assoc :author-last-name author-last-name)
        (assoc :might-have-code? (might-have-code? body))
        (assoc :body body)
        (dissoc "subject" "body")
        (merge (if reply?
                 {:reply? true :original? false}
                 {:reply? false :original? true})))))

(defn not-derrida [m]
  (and (or (:original? m) (:might-have-code? m)) ;; if it's a reply, let's make sure it at least has some code
       (not (:toxic? m))                         ;; let's check for toxic language
       (not
        (or                                      ;; a bunch of words in subjects indicative of announcements
         (str/includes? (:subject m) "Call for Contributions")
         (str/includes? (:subject m) "Call for Participation")
         (str/includes? (:subject m) "Job")
         (str/includes? (:subject m) "JOB")
         (str/includes? (:subject m) "CfP")
         (str/includes? (:subject m) "CFP")
         (str/includes? (:subject m) "Call for Papers")
         (str/includes? (:subject m) "is now available")
         (str/includes? (:subject m) "hiring")
         (str/includes? (:subject m) "meeting")
         (str/includes? (:subject m) "meetup")
         (str/includes? (:subject m) "Clojurists Together")
         (str/includes? (:subject m) "Online Meetup")
         (str/includes? (:subject m) "Online meetup")
         (str/starts-with? (:subject m) "[Blog]")
         (str/starts-with? (:subject m) "[BLOG]")
         (str/starts-with? (:subject m) "[CFP]")
         (str/starts-with? (:subject m) "[JOB]")
         (str/starts-with? (:subject m) "{JOB}")
         (str/starts-with? (:subject m) "[ANN]")
         (str/starts-with? (:subject m) "RFC")
         (str/starts-with? (:subject m) "ANN:")
         (str/starts-with? (:subject m) "Announcement")
         (str/starts-with? (:subject m) "Ann:")))))

(defn clean-groups [groups]
  (->> groups
       (map (fn [[k v]]
              (let [original (->> v (filter :original?) first)
                    original-author (:author original)
                    non-author-replies (->> v
                                            (filter #(not= original-author (:author %)))
                                            (into [original]))]
                [k non-author-replies]))) ;; we only care about the original question and direct answers to the original question (not from the original author)
       (into {})))

(defn pair-groups [groups]
  (->> groups
       (map (fn [[k v]]
              (let [original (->> v (filterv :original?) first)
                    [psuedo-first-name psuedo-last-name] (names/rand-first-last) ;; anonymize original author per conversation thread
                    others (->> v (filterv :reply?))
                    original-author-first-name (:author-first-name original)
                    original-author-last-name (:author-last-name original)
                    original-prompt (str (:subject original) "\n"
                                         (:body original))
                    prompt (->> original-prompt
                                get-non-space-tokens
                                (map #(cond
                                        (str/includes? (or % "") (or original-author-first-name "")) psuedo-first-name
                                        (str/includes? (or % "") (or original-author-last-name "")) psuedo-last-name
                                        :else %))
                                (interpose " ")
                                (apply str))]
                [k
                 (->> others
                      (mapv (fn [reply]
                              (let [reply-author-first-name (:author-first-name reply)
                                    reply-author-last-name (:author-last-name reply)
                                    [psuedo-reply-first-name psuedo-reply-last-name] (names/rand-first-last) ;; anonymize reply author per convo prompt pair
                                    response (->> reply
                                                  :body
                                                  get-non-space-tokens
                                                  (map #(cond
                                                          (str/includes? (or % "") (or original-author-first-name "")) psuedo-first-name
                                                          (str/includes? (or % "") (or original-author-last-name "")) psuedo-last-name
                                                          (str/includes? (or % "") (or reply-author-first-name "")) psuedo-reply-first-name
                                                          (str/includes? (or % "") (or reply-author-last-name "")) psuedo-reply-last-name
                                                          :else %))
                                                  (interpose " ")
                                                  (apply str))]
                                {:prompt prompt
                                 :response response}))))])))
       (into {})))

(defn mk-data [mail]
  (x>> mail
       (map attach-data)
       (filter not-derrida)
       (group-by :sub-match)
       (filter #(and (->> % second count (not= 1))
                     (->> % second (filter :original?) first)))
       clean-groups
       pair-groups
       (map second)
       (filter #(> 4 (count %))) ;; limit conversation size to 3 replies
       (apply concat)
       (filterv (comp (complement nil?) seq))))

(defn get-mail [raw-data-file-name]
  (json/read-str (slurp raw-data-file-name)))
