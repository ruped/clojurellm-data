(ns clojure-llm.collect.email-groups.clojurescript
  (:require
   [clojure.data.json :as json]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [clojure-llm.collect.data.names :as names]
   [clojure-llm.collect.data.toxic :as toxic]
   [clojure-llm.collect.email-groups.common :as c]
   [reaver]
   [injest.path :refer [x>> =>>]]
   [skyscraper.core :as core :refer [defprocessor]]))

(def base-page "https://www.mail-archive.com/clojurescript@googlegroups.com/")

(def first-page (str base-page "msg08581.html"))

(def seed
  [{:url first-page
    :processor :landing-page
    :page 1}])

(def p-atom (atom #{}))

(defn pad-zeros [n]
  (let [c (count (str n))]
    (if-not (< c 5)
      (str n)
      (let [diff (- 5 c)
            nstr (str (apply str (take diff (cycle ["0"]))) n)]
        nstr))))

(defn update-url [url]
  (let [base-count (if (str/starts-with? url "https://") (count base-page) 0)
        old-url (->> url
                     (drop-last 5)
                     (drop (+ 3 base-count))
                     (apply str))
        ends-with-hash? (str/ends-with? old-url "#")
        cleaned-url (-> old-url (str/replace "#" ""))
        p (->> cleaned-url Long/valueOf dec)
        p (if ends-with-hash? (dec p) p)
        p (if (@p-atom p) (dec p) p)]
    (swap! p-atom conj p)
    (when  (< -1 p)
      (let [new-url (str "msg" (pad-zeros p) ".html")]
        new-url))))

(defprocessor :landing-page
  :cache-template "clojurescript-google-mail-group/:page"
  :process-fn (fn [doc context]
                (concat
                 (reaver/extract-from doc ".content"
                                      [:subject :body]
                                      ".msgHead" reaver/text
                                      ".msgBody" reaver/text)
                 (when-let [next-page (-> context :url update-url)]
                   (let [next-page (if-not (= next-page "#")
                                     next-page
                                     (-> context :url update-url))]
                     [{:url (str base-page next-page)
                       :processor :landing-page
                       :page (inc (:page context))}])))))

(defn run []
  (core/scrape seed
               :html-cache true
               :download-error-handler
               (fn [_ _ c]
                 [(assoc c :url (str base-page (->> c :url update-url)))])
               :parse-fn core/parse-reaver))

(def raw-data-file-name "data/clojurescript_mailgroup/clojure_llm_clojurescript_mailgroup.json")
(def data-file-name "data/clojurescript_mailgroup/clojure_llm_clojurescript_mailgroup_prompts.json")

(def sample-raw-data-file-name "data/clojurescript_mailgroup/clojure_llm_clojurescript_mailgroup_sample.json")
(def sample-data-file-name "data/clojurescript_mailgroup/clojure_llm_clojurescript_mailgroup_prompts_sample.json")

(defn -download-emails []
  (reset! p-atom #{})
  (let [res (run)]
    (spit raw-data-file-name "[\n")
    (->> res
         (interpose ",")
         (partition-all 2)
         (map (fn [[p c]]
                (str (json/write-str (dissoc p :page)) c \newline)))
         (mapv #(spit raw-data-file-name % :append true)))
    (spit raw-data-file-name "]" :append true)))

#_(-download-emails)

(defn -process-emails []
  (let [res (c/mk-data (c/get-mail raw-data-file-name))]
    (spit data-file-name "[\n")
    (x>> res
         (interpose ",")
         (partition-all 2)
         (map (fn [[p c]]
                (str (json/write-str (dissoc p :page)) c \newline)))
         (mapv #(spit data-file-name % :append true)))
    (spit data-file-name "]" :append true)))

#_(-process-emails)

(defn -main [& args]
  (-download-emails)
  (-process-emails))

#_(-main)

(defn create-sample []
  (reset! p-atom #{})
  (let [res (run)
        res2 (=>> res
                  (take 1000)
                  (interpose ",")
                  (partition-all 2)
                  (map (fn [[p c]]
                         (if-not c
                           (str (json/write-str (dissoc p :page)) \newline)
                           (str (json/write-str (dissoc p :page)) c \newline)))))]
    (spit sample-raw-data-file-name "[\n")
    (->> res2
         (mapv #(spit sample-raw-data-file-name % :append true)))
    (spit sample-raw-data-file-name "]" :append true)))

#_(create-sample)

(defn -process-sample-emails [& _args]
  (let [res (c/mk-data (c/get-mail sample-raw-data-file-name))]
    (spit sample-data-file-name "[\n")
    (=>> res
         (interpose ",")
         (partition-all 2)
         (map (fn [[p c]]
                (str (json/write-str (dissoc p :page)) c \newline)))
         (map #(spit sample-data-file-name % :append true)))
    (spit sample-data-file-name "]" :append true)))

#_(-process-sample-emails)


(defn -sample-main [& args]
  (create-sample)
  (-process-sample-emails))

#_(-sample-main)
