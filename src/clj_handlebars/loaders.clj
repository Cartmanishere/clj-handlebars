(ns clj-handlebars.loaders
  (:import [com.github.jknack.handlebars.io ClassPathTemplateLoader FileTemplateLoader]))

(defonce default-suffix ".hbs")

(defn classpath-template-loader
  []
  (ClassPathTemplateLoader.))

(defn filepath-template-loader
  ([base-dir]
   (FileTemplateLoader. base-dir))
  ([base-dir suffix]
   (FileTemplateLoader. base-dir suffix)))
