(defproject us "0.1.0-SNAPSHOT"
  :description "us - together, we are a friendly a.i."
  :url "http://github.com/lgastako/us"
  :license {:name "us license"}
  :source-paths ["src/clj"
                 "target/gen/clj"
                 "target/gen/cljx"]
  :repl-options {:timeout 200000} ;; Defaults to 30000 (30 seconds)
  :test-paths ["spec/clj"]
  :dependencies [[clj-time "0.9.0"]
                 [compojure "1.3.1"]
                 [crate "0.2.5"]
                 [enlive "1.1.5"]
                 [environ "1.0.0"]
                 [garden "1.2.5"]
                 [http-kit "2.1.19"]
                 [hiccups "0.3.0"]
                 [its-log "0.2.2" :exclusions [org.clojure/clojure]]
                 [om "0.8.0-rc1"]
                 [omdev "0.1.3-SNAPSHOT"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2511" :scope "provided"]
                 [prismatic/dommy "1.0.0"]
                 [prismatic/om-tools "0.3.10"]
                 [prismatic/schema "0.4.0"]
                 [prone "0.8.1"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.3"]
                 [sablono "0.3.4"]
                 [wharf "0.2.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-environ "1.0.0"]
            [lein-sassc "0.10.4"]
            [lein-auto "0.1.1"]]
  :min-lein-version "2.5.0"
  :uberjar-name "us.jar"
  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "target/gen/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}
  :sassc [{:src "src/scss/style.scss"
           :output-to "resources/public/css/style.css"}]
  :auto {"sassc"  {:file-pattern  #"\.(scss)$"}}
  :prep-tasks [["cljx" "once"] "javac" "compile"]
  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/gen/clj"
                   :rules :clj}
                  {:source-paths ["src/cljx"]
                   :output-path "target/gen/cljs"
                   :rules :cljs}]}
  :profiles {:dev {:source-paths ["env/dev/clj"]
                   :test-paths ["test/clj"]
                   :dependencies [[figwheel "0.2.1-SNAPSHOT"]
                                  [figwheel-sidecar "0.2.1-SNAPSHOT"]
                                  [com.cemerick/piggieback "0.1.3"]
                                  [weasel "0.4.2"]
                                  [speclj "3.1.0"]]
                   :repl-options {:init-ns us.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl
                                                     cljx.repl-middleware/wrap-cljx]}
                   :plugins [[lein-figwheel "0.2.1-SNAPSHOT"]
                             [speclj "3.1.0"]
                             [com.keminglabs/cljx "0.5.0" :exclusions [org.clojure/clojure]]]
                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]}
                   :env {:is-dev true}
                   :cljsbuild {:test-commands { "spec" ["phantomjs" "bin/speclj" "resources/public/js/app_test.js"] }
                               :builds {:app {:source-paths ["env/dev/cljs"]}
                                        :test {:source-paths ["src/cljs" "spec/cljs"]
                                               :compiler {:output-to     "resources/public/js/app_test.js"
                                                          :output-dir    "resources/public/js/test"
                                                          :source-map    "resources/public/js/test.js.map"
                                                          :preamble      ["react/react.min.js"]
                                                          :optimizations :whitespace
                                                          :pretty-print  false}}}}}
             :uberjar {:source-paths ["env/prod/clj"]
                       :hooks [leiningen.cljsbuild leiningen.sassc]
                       :env {:production true}
                       :omit-source true
                       :aot :all
                       :cljsbuild {:builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
