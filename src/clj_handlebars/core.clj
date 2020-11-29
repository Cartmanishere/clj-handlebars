(ns clj-handlebars.core
  (:require [clj-handlebars.loaders :refer [classpath-template-loader]]
            [clj-handlebars.utils :refer [clj-map->java-map]])
  (:import [com.github.jknack.handlebars Handlebars]))

(defn init-hbs
  "Initialize an instance of Handlebars with a `loader` source. Optionally
  provide a `cache` which is added to the Handlebars."
  ([]
   (init-hbs (classpath-template-loader) nil))
  ([loader]
   (init-hbs loader nil))
  ([loader cache]
   (let [hbs (Handlebars. loader)]
     (if cache
       (doto hbs
         (.with cache))
       hbs))))

(defn render
  "Render a file using a Handlebars instance. If Handlebars is not provided,
  a default one is loaded."
  ([tname data]
   (render (init-hbs) tname data))
  ([hbs tname data]
   (-> (.compile hbs tname)
       (.apply (clj-map->java-map data)))))
