(ns lein-pocketbook.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :as pprint]
            [clojure.string :as string]))

(defn pprint
  [x]
  (with-out-str (pprint/pprint x)))

(defn roll
  "Rolls a sequence into a map"
  [xs]
  (apply hash-map xs))

(defn unroll
  "Unrolls a map into a sequence"
  [m]
  (mapcat identity m))

;; FML. are we serious?
(defn apply-kwargs
  "Apply for dumb kwargs functions"
  [f m]
  (apply f (unroll m)))

(defn- read-cache
  [cache-f]
  (try (-> cache-f
           io/reader
           java.io.PushbackReader.
           edn/read)
       ;; start fresh if something is wrong (e.g. corrupted/no file)...
       (catch Exception _
         nil)))

(defn disk-memoize
  [f]
  (fn [& args]
    (let [f-name     (string/replace (str f) #"@.*$" "")
          cache-file (io/file "target" (str f-name ".cache.edn"))
          _          (.mkdirs (io/file "target"))
          cache      (read-cache cache-file)]
      (if (contains? cache args)
        (get cache args)
        (let [ret       (apply f args)
              new-cache (assoc cache args ret)]
          (spit cache-file  new-cache)
          ret)))))
