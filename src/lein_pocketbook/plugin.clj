(ns lein-pocketbook.plugin
  (:require [lein-pocketbook.core :as core]
            [lein-pocketbook.utils :as u]))

;; FIXME ??? why does it do it twice??!!!??? (Or more if i use lein.core.project/merge-profiles)
;; FIXME ??? figure out why p/merge-profiles doesn't work..
;; TODO research how to do this properly w/o middleware

(defn middleware
  "Adds sources and javadocs to project dependencies."
  [project]
  (let [deps                  (:dependencies project)
        repos                 (into {} (:repositories project))
        args                  {:coordinates  deps
                               :repositories repos}
        sources+javadocs-deps (-> args core/->source+javadoc-args :coordinates)
        all-deps              (set (concat deps sources+javadocs-deps))]
    (leiningen.core.main/info "Adding to :dependencies" sources+javadocs-deps)
    (assoc project :dependencies all-deps)))
