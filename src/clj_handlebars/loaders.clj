(ns clj-handlebars.loaders
  (:require [com.rpl.proxy-plus :refer [proxy+]])
  (:import [com.github.jknack.handlebars.io ClassPathTemplateLoader FileTemplateLoader
            URLTemplateLoader]
           [java.net URL]))

(defonce default-suffix ".hbs")

(defn classpath-template-loader
  []
  (ClassPathTemplateLoader.))

(defn filepath-template-loader
  ([base-dir]
   (FileTemplateLoader. base-dir))
  ([base-dir suffix]
   (FileTemplateLoader. base-dir suffix)))

(defn http-template-loader
  ([base-url]
   (http-template-loader base-url default-suffix))
  ([base-url suffix]
   (doto (proxy+
          []
          URLTemplateLoader
          (resolve
           [this path]
           (str (. this getPrefix)
                path
                (. this getSuffix)))
          (getResource
           [this location]
           (URL. location)))
     (.setPrefix base-url)
     (.setSuffix suffix))))
