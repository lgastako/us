(set-env!
 :source-paths    #{"src/cljx" "src/cljs" "src/clj"}
 :resource-paths  #{"resources"}
 :dependencies '[[adzerk/boot-cljs      "0.0-2814-4" :scope "test"]
                 [adzerk/boot-cljs-repl "0.1.9"      :scope "test"]
                 [adzerk/boot-reload    "0.2.4"      :scope "test"]
                 [boot-garden "1.2.5-1" :scope "test"]
                 [deraen/boot-cljx "0.2.2"]
                 [its-log "0.2.2"` :exclusions [com.keminglabs/cljx
                                                org.clojure/clojure
                                                org.clojure/clojurescript]]
                 [org.omcljs/om "0.8.6"]
                 [pandeiro/boot-http    "0.6.1"      :scope "test"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[boot-garden.core    :refer [garden]])

(deftask build []
  (comp (speak)

        (cljx)
        (cljs)

        (garden :styles-var 'us.styles/screen
                :output-to "css/garden.css")))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced
                       ;; pseudo-names true is currently required
                       ;; https://github.com/martinklepsch/pseudo-names-error
                       ;; hopefully fixed soon
                       :pseudo-names true}
                 garden {:pretty-print false})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none
                       :unified-mode true
                       :source-map true}
                 reload {:on-jsload 'us.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
