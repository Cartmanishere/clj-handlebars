(ns clj-handlebars.cache
  (:import [com.github.jknack.handlebars.cache GuavaTemplateCache]
           [com.google.common.cache CacheBuilder]
           [java.util.concurrent TimeUnit]))

(defn add-timeout-cache
  "A guava cache that expires after `ttl` secs to Handlebars."
  [ttl]
  (-> (CacheBuilder/newBuilder)
      (.expireAfterWrite ttl TimeUnit/SECONDS)
      (.build)))

(defn add-access-cache
  "A guava cache that saves the accessed element for some time
  specified by `timeout` in secs. The cache key only expires if it has
  not been accessed for the specified timeout."
  [timeout]
  (-> (CacheBuilder/newBuilder)
      (.expireAfterAccess timeout TimeUnit/SECONDS)
      (.build)))
