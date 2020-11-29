(ns clj-handlebars.utils
  (:require [clojure.walk :refer [postwalk]]))

(defn clj-map->java-map
  "Convert Clojure hash-map of `data` into a Java HashMap for passing to the
  Handlebars render function."
  [data]
  (postwalk
   #(cond
      (map? %) (java.util.HashMap. ^java.util.Map %)
      (keyword? %) (name %)
      :else %)
   data))
