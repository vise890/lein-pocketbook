(ns lein-pocketbook.plugin
  (:require [lein-pocketbook.core :as core]
            [lein-pocketbook.utils :as u]))

;;;
;; FIXME why does it do it twice??!!!??? (Or more if i use lein.core.project/merge-profiles)
;;;

;; FIXME figure out why p/merge-profiles doesn't work..

(defn pocketbook-middleware-dependencies
  "Adds source and javadoc to project dependencies."
  [project]
  ;; TODO research how to do this properly w/o middleware
  (let [deps                  (:dependencies project)
        repos                 (into {} (:repositories project))
        args                  {:coordinates  deps
                               :repositories repos}
        sources+javadocs-deps (-> args core/->source+javadoc-args :coordinates)
        all-deps              (seq (concat deps sources+javadocs-deps))]
    (leiningen.core.main/info "Adding to :dependencies" (u/pprint sources+javadocs-deps))
    (assoc project :dependencies all-deps)))

(defn pocketbook-middleware-resources
  "Adds source and javadocs jars to dev project resources."
  [project]
  (let [deps                       (:dependencies project)
        repos                      (into {} (:repositories project))
        args                       {:coordinates  deps
                                    :repositories repos}
        dev-resource-paths         (get-in project [:profiles :dev :resource-paths])
        sources+javadocs-resources (core/->source+javadoc-jars args)
        all-dev-resource-paths     (seq (concat dev-resource-paths sources+javadocs-resources))]
    (leiningen.core.main/info "Adding to :dev :resource-paths" (u/pprint sources+javadocs-resources))
    (assoc-in project [:profiles :dev :resource-paths] all-dev-resource-paths)))
