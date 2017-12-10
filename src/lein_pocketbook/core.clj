(ns lein-pocketbook.core
  (:require [cemerick.pomegranate :as pom]
            [cemerick.pomegranate.aether :as aether]
            [lein-pocketbook.utils :as u])
  (:import java.io.File))

;; NOTE: ::args ::  {:repositories {} :coordinates [[]]}
;;       i.e. a *MAP* but in the same style as the kwargs for fns in the pomegranate.aether ns

(defn fully-resolve-dependencies
  "Returns ::args with all the transitive dependencies added."
  ([args retrieve?]
   ;; TODO i think we need to be smarter about keeping exclusions here
   (let [deps-graph (u/apply-kwargs aether/resolve-dependencies
                                    (merge args {:retrieve retrieve?}))]
     (assoc args
            :coordinates
            (keys deps-graph))))
  ([args]
   (fully-resolve-dependencies args false)))
(def fully-resolve-dependencies-memo (u/disk-memoize fully-resolve-dependencies))

(defn classify
  "Classify a dependency coordinate with given classifier.

  e.g. (classify [foo \"1.4.0\" :exclusions [bar]] \"sources\")"
  [[group+name version & modifiers :as _coordinate] classifier]
  (into [group+name version]
        (-> modifiers
            u/roll
            (assoc :classifier classifier)
            u/unroll)))

(defn swap-out-sources+javadocs
  "Update the :coordinates in :args to have 'sources' and 'javadoc' classifiers."
  [args]
  (letfn [(s+j [c]
            [(classify c "sources") (classify c "javadoc")])]
    (update args :coordinates (partial mapcat s+j))))

;; TODO this approach is sloooooow
(defn resolvable?
  "Return whether the given dependency coordinate is resolvable in the given repositories."
  [repositories coordinate]
  (leiningen.core.main/info "Checking " coordinate)
  (try
    ;; TODO try to resolve the file locally first?
    (aether/resolve-artifacts* :repositories repositories :coordinates [coordinate])
    true
    ;; FIXME import aether somehow
    (catch Exception #_org.eclipse.aether.resolution.ArtifactResolutionException _
           false)))
(def resolvable?-memo (u/disk-memoize resolvable?))

(defn remove-unresolvable
  "Keeps only the resolvable :coordinates in args."
  [{:keys [repositories]
    :as args}]
  (let [resolvable? (partial resolvable?-memo repositories)]
    (update args :coordinates (partial filter resolvable?))))

(defn ->source+javadoc-args
  "Returns a map in the ::args format from the given args, with all the resolvable sources and javadoc dependencies."
  [args]
  (-> args
      fully-resolve-dependencies-memo
      swap-out-sources+javadocs
      remove-unresolvable))

;; (defn ->source+javadoc-jars
;;   [args]
;;   (-> args
;;       ->source+javadoc-args
;;       (fully-resolve-dependencies true)
;;       :coordinates
;;       (->> (map (comp (fn [^File f] (.getAbsolutePath f)) :file meta)))))

;; (defn add-sources+javadocs
;;   "Tries to add the source and javadoc artefacts to the current classpath.

;;   Expects a *MAP* but in the same style as the kwargs for fns in the pomegranate.aether ns.

;;   NOTE pulls down the entire graph..."
;;   [args]
;;   (u/apply-kwargs pom/add-dependencies
;;                   (->source+javadoc-args args)))
