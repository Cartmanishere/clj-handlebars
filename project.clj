(defproject clj-handlebars "0.1.0-SNAPSHOT"
  :description "Handlebars templates in Clojure"
  :url "http://github.com/Cartmanishere/clj-handlebars"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.github.jknack/handlebars "4.1.2"]
                 [com.github.jknack/handlebars-guava-cache "4.0.0"]
                 [com.rpl/proxy-plus "0.0.5"]]
  :repl-options {:init-ns clj-handlebars.core})
