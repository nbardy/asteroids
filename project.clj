(defproject asteroids "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]

                 [com.cemerick/clojurescript.test "0.2.1"]
                 [org.clojure/clojurescript "0.0-2030"]]

  :plugins [[lein-cljsbuild "1.0.0-alpha2"]
            [com.cemerick/clojurescript.test "0.2.1"]]


  :source-paths ["src"]

  :cljsbuild {:builds [{:id "asteroids"
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :output-to "asteroids.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :source-map true}}

                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {
                                   :output-to "target/cljs/testable.js"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true}}]
              :test-commands {"unit-tests" ["phantomjs" :runner
                                            "window.literal_js_was_evalutaed=true"
                                            "target/cljs/testable.js" ] } })

