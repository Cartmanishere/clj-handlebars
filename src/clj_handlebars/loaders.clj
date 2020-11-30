(ns clj-handlebars.loaders
  (:require [com.rpl.proxy-plus :refer [proxy+]])
  (:import [com.github.jknack.handlebars.io ClassPathTemplateLoader FileTemplateLoader
            URLTemplateLoader AbstractTemplateSource]
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

(defn content-fn-template-source
  [content-fn location]
  (reify
    TemplateSource
    (content [this _]
      ;; The 2nd argument is the charset. Don't really have a use for this now.
      (content-fn location))
    (lastModified [this] ;; TODO: If possible, add support for custom `lastmodified`.
      -1)
    (filename [this] ;; Need this method implemented even if we are are not using filename
      "")))

(defn content-fn-template-loader
  "In this template loader, you need to pass a `content-fn`. This function is called with
  the path of the template (incl prefix, suffix) and it should return the template string.
  You can define any source or method of fetching the content in this `content-fn`."
  ([content-fn]
   (content-fn-template-loader content-fn "" default-suffix))
  ([content-fn prefix]
   (content-fn-template-loader content-fn prefix default-suffix))
  ([content-fn prefix suffix]
   ;; Note: This uses clojure proxy because proxy+ does not allow to add a method
   ;; not defined in the base class.
   (doto (proxy
             [AbstractTemplateLoader] []
           (sourceAt [location]
             (content-fn-template-source content-fn (proxy-super resolve location)))
           (resolve [path]
             (str (. this getPrefix)
                  path
                  (. this getSuffix))))
     (.setPrefix prefix)
     (.setSuffix suffix))))
