# clj-handlebars

A Clojure library designed to render Handlebars templates.

## Usage

##### 1. Render with default classpath loader
```Clojure
(require '[clj-handlebars.core :refer [init-hbs render]])
(def hbs (init-hbs))

(render hbs "filename" {:key "value"})
```
##### 2. Render with custom http loader
```Clojure
(require '[clj-handlebars.core :refer [init-hbs render]])
(require '[clj-handlebars.loaders :refer [http-template-loader]])

(def loader (http-template-loader "https://bucket-name.s3.Region.amazonaws.com/"))
(def hbs (init-hbs loader)

(render hbs "filename" {:key "value"})
```

##### 3. Render with content fn loader
- Initialize a handlebars loader which invokes a clojure function to return the template data.
- The clojure function takes filename as argument and returns the contents of the file.
```Clojure
(require '[clj-handlebars.core :refer [init-hbs render]])
(require '[clj-handlebars.loaders :refer [content-fn-template-loader])

(defn content-fn
  [location]
  ;; Use location to generate/load the handlebars template
  ;; from datastore.
  (str "This is " location "file's contents"))
 
 (def loader (content-fn-template-loader content-fn))
 (def hbs (init-hbs loader))
 
 (render hbs "filename" {:key "value"})
 ```
  - The handlebars parser invokes the `content-fn` with the `filename` to load the data.
  - Inside the `content-fn`, the contents can be loaded from anywhere for e.g database, cloud store or from disk.

##### 4. Render with Cache
- Along with a loader, you can also specify a cache for the template file.
- We use Google Guava cache. There are two strategies available included here -- 
  + `timeout-cache`: The template file's contents are cached until the timeout expires.
  + `access-cache`: The template file's contents are cached until the timeout since last cache access. If cache is accessed, then timeout timer is reset.
- The cache can be used with any of the above loaders.
```Clojure
(require '[clj-handlebars.cache :refer [timeout-cache access-cache])
(require '[clj-handlebars.core :refer [init-hbs render]])
(require '[clj-handlebars.loaders :refer [http-template-loader]])

(def loader (http-template-loader "https://bucket-name.s3.Region.amazonaws.com/"))

;; With timeout-cache
(def hbs (init-hbs loader (timeout-cache 600)))
;; With access-cache
(def hbs (init-hbs loader (access-cache 600)))

(render hbs "filename" {:key "value"})
```
- The timeout value is specified in terms of *seconds*.
## License

Copyright © 2021

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

