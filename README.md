# clojurellm-data
Clojure LLM - Dataset curation for fine tuning an LLM for Clojure.

| Dataset  | Location | Size | Launch Main | Launch Sample |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Clojure email groups  | [data/clojure_mailgroup](/data/clojure_mailgroup) | 25.31MB | clj -X:email-clojure-main | clj -X:email-clojure-sample |
| Clojurescript email groups  | [data/clojurescript_mailgroup](./data/clojurescript_mailgroup) | 3.1MB | clj -X:email-clojurescript-main | clj -X:email-clojurescript-sample |
| Clojurians chat logs  | [data/clojurians_chat](./data/clojurians_chat)  | N/A | N/A | N/A |
| Clojurians forum  | [data/clojurians_forum](./data/clojurians_forum)  | N/A | N/A | N/A |
| General programming  | [data/general_programming](./data/general_programming)  | N/A | N/A | N/A |
| Clojure/script Projects  | [data/projects](./data/projects)  | N/A | N/A | N/A |
| Stackoverflow  | [data/stackoverflow](./data/stackoverflow)  | N/A | N/A | N/A |
| Synthetic Clojure  | [data/synthetic](./data/synthetic)  | N/A | N/A | N/A |
| Clojure web crawl  | [data/web_crawl](./data/web_crawl)  | N/A | N/A | N/A |


## ClojureLLM Data Management Policy

1. <details open><summary>Intro</summary>

    1. <details open><summary>What</summary>

        The purpose of the data in this repository is for the fine-tuning of an LLM for a Clojure coding assistant.

        The purpose of this document is to outline the policies and procedures by which data custodians acting on behalf of the ClojureLLM project manage the security and safety of the data being used in the project, as well as other information useful to different stakeholders. These documents are sometimes called "datasheets."
       </details>

    2. <details><summary>Who</summary>

        ClojureLLM Data is developed and supported by members of the Clojure community for the benefit of the Clojure community.

        In this document, any ClojureLLM developer working on the data in this repository shall be referred to as a "Data Custodian."
       </details>

    3. <details><summary>Support and Funding</summary>

        Infrastructure for this project is currently funded by the ClojureLLM team but we will have a method for folks to contribute funds for training runs soon.
       </details>

   </details>

______

2. <details open><summary>Data Sources</summary>

    1. <details><summary>Clojure Code Data Sources</summary>

       ClojureLLM will use the following sources of Clojure code for data.

       - Github/gitlab Clojure project crawl
           - https://github.com/phronmophobic/dewey
        - Synthetically generated clojure code projects and code conversations
        - Santacoder fine-tune stack for Clojure
           - https://huggingface.co/mrm8488/santacoder-finetuned-the-stack-clojure

        Not all of these sources may be used and others may be added to this list over time.
       </details>

    2. <details><summary>Clojure Conversation Data Sources</summary>

       ClojureLLM will use the following sources of Clojure code for data.

        - Clojurians Slack message (as question answer pairs)
           - https://clojurians-log.clojureverse.org/
        - Clojureverse forum logs
           - https://clojureverse.org/
        - Stackoverflow questions tagged with Clojure
           - https://stackoverflow.com/questions/tagged/clojure
        - Clojure/script mailing lists
           - https://www.mail-archive.com/clojure@googlegroups.com/
           - https://www.mail-archive.com/clojurescript@googlegroups.com/
        - Crawl Clojure RSS feed history
           - https://planet.clojure.in/atom.xml

        Not all of these sources may be used and others may be added to this list over time.
        </details>

    2. <details><summary>Non-Clojure Code Data Sources</summary>

        ClojureLLM may leverage some existing and/or future datasets, made available in the larger open source community, so as to facilitate the translation of programming concepts from other languages into Clojure.
        - programming datasets
           - https://www.bigcode-project.org/
        - programming instruction sets from other coding assistants
           - https://github.com/yaodongC/awesome-instruction-dataset
       </details>

    </details>

______

3. <details open><summary>Collection</summary>

    1. <details><summary>Scraping</summary>
    
       ClojureLLM Data Custodian may use any Clojure web scraping tool they'd like, but [skyscraper](https://github.com/nathell/skyscraper) is recommended.

       Before scraping any given site, ensure the copyright of the site does not prohibit the usage of its code-related data for LLM training for any reason.

       The script for a given dataset should be added as a launch alias in the project `deps.edn` in order to run the script.
    
    </details>

    2. <details><summary>Storing</summary>

        Due to storage constraints on Github, ClojureLLM will not be storing entire datasets in the repo. User's running particular pipelines will execute the download/scraping scripts for the dataset they're working on instead.

        However, it is advised to store a small _sample_ of the dataset that the scripts will produce, so that folks can experiment without having to run the scrape.

        > Note: We'd like to keep the repo under 100MB in general
        </details>

______

4. <details open><summary>Sanatization</summary>

    1. <details><summary>Remove Garbage</summary> 
    
       We're only interested in the Clojure code and the human langauge related to the Clojure code. However, those values will usually be embedded within HTML, JSON and various document formats. That data should be purged from the dataset.
       </details>

    2. <details><summary>Deduplication</summary> 
    
       It is possible that some the code or conversation data exists in more than one location on the internet. So it's possible for there to be duplicates the scraped data. Therefore, it is the responsibility of the data custodian defining the download script to eliminate duplication of data both within their dateset as well as the rest of the datasets in the repo.
       </details>

    3. <details><summary>Remove ClojureLLM Outputs</summary>
    
       ClojureLLM outputs may end up in chat logs and we don't want to waste test space allocated for human training data. This likely won't be a huge problem - just be sure to avoid including massive amounts of outputs, especially from ClojureLLM.

       We may eventually develop some data watermarks and tools to later help automatically detect ClojureLLM code in text for possible elision from the dataset.
       </details>

    4. <details><summary>Toxicity and Bias</summary> 
    
       Make an effort to remove toxicity, sarcasm, hyperbole, bias, personal opinions, jokes, or anything not related to Clojure code or advice around the usage and understanding of Clojure code and other related programming technologies.

       We plan on having LLM based sentiment/semantic classification tools in the future to help automate the detection of toxicity and general divergence from the target content for ClojureLLM. Different datasets will then be able to leverage those tools.
       </details>

    5. <details> <summary>PII</summary>

       Data custodians should make an effort to remove Personally Identifiable Information, including but not limited to:
       - credit card numbers
       - personal names (except library authors)
       - emails (except library/solution contact info)
       - home addresses
       - social security numbers
       - phone numbers
       - financial data
       - publicly accessble IP addresses (not local)
       - employer of speaker
       - social network handles
       - anyone mentioning their name explicitly

        We plan to provide PII scanning tools for datasets that can be used generically from all of the dataset collection scripts.
        </details>

    6. <details><summary>Injection Attack Detection</summary>
    
       One potential danger of LLMs is the ability for an attacker to surruptitiously poisons public datasets with data that either corrupts the data or injects prompts or information into the data that produces undesirable inference or side-effects in the LLM training on and infering on the data.

       We are still learning about this mode of attack, but as our understanding increases, we plan to automate the detection and removal of these instances from ClojureLLM datasets.
       </details>

    7. <details><summary>User Anonymization</summary>

       Data custodians should make an effort to anonymize the users associated with Clojure code and conversations around Clojure code.

       This includes:
       - cross-conversation anonymization
       - psuedo names will be wellknown names
           - ["Bob" "Alice" "Jamal" "Myleen" "Oliver" etc]
       - 50% male / 50% famale names (open to comment)
       - psuedo names will be ethnically / culturally diverse
       - Redact descriptions of human likenesses ("oh, no, I have green eyes")
       - Redact any mentioning children or family members

       These items are open to feedback and expansion. In general, we want to represent a diversity of backgrounds for a dataset that is helpful to existing and future Clojurists around the world.

       Tools for anonymizing users will be shared across the different dataset pipelines as they are built out.

       </details>

</details>

______

5. <details open><summary>Data Enrichment</summary>

    1. <details><summary>Clojure Code Generation</summary>
    
       A large part of this project will involve the synthetic generation of large amounts of Clojure code, so as to give the ClojureLLM a very deep intuition around how the Clojure compiler behaves.

       This is open question and we hope the community will give feedback on how best to accomplish this.

       Eventually we may use Clojure code generation tools to help grow out the other datasets in this repository as well.
       </details>

    2. <details><summary>Conversation Grammar</summary>

       There is often grammatical and syntactical errors in common language between humans. We can correct these errors though with tools that will automatically fix those mistakes, which can increase the comprehensibility of the training data.

       Again, data custodians that build tools for cleaning up grammar in a particular dataset should make an effort to make those same tools available in the rest of the datasets in this repository.
       </details>

______

6. <details open><summary>Usage</summary>

    1. <details><summary>Code Completion</summary>
    
       One model will be used primarily for Code Completion. This model will be smaller, fit in more applications and will execute faster, for more immediate feedback while the Clojurist is typing.
       </details>

    2. <details><summary>Code Conversation / Pair Programming</summary>

       Another model will be used for pair programming, asking ClojureLLM questions and getting a written response in natural language explaining the answer.

       This model will be necessarily larger, to understand more general natural language concepts, translating between them and code concepts. It will also be slower, as the Clojurist will see the words in the response be written out in realtime.
       </details>

______

7. <details open><summary>Distribution</summary>

    1. <details><summary>Open / Restricted</summary>
    
       Some models that we'll be starting off with may have licenses that restrict what we can do with them. Some allow for commercial use, others do not. We intend on supporting and working on both.

       If the best model available, that can provide the best experience for Clojure devs, is a restricted model, we may still want to use that in some projects, like an open source LSP server that can use an LLM. Because an open source project like that is not commercial, it is free to use models that have a commercial restriction. We're not going to go with a lesser model for that purpose, just because it cannot be used commercially.

       That being said, a stated purpose of this project is to also make available the development of commercial Clojure applications on top of LLM-based technologies.
       </details>

    2. <details><summary>Large / Small</summary>
    
       As stated above, code completion models will likely need to be smaller, in order to be fast and useful. Conversational models will likely need to be larger.

       That being said, this space is evolving fast and smaller and larger models with different performance characteristics will continue coming out and we intend to experiment with many of them.
       </details>

______

8. <details open><summary>Maintenance</summary>

    1. <details><summary>Community Feedback</summary>
    
       The direction of this project is an open community effort and it is likely to change as things progress, so we encourage everyone to engage and provide feedback on what can be improved and where you'd like to see things go.

       Feel free to file a PR to update this document or file an issue if you have an questions or concerns.
       </details>

    2. <details><summary>Dataset Versioning and Updates</summary>
    
       Some datasets will be made available in the Releases of this project. A zip file of all the datasets will be made available on huggingface.
       </details>
