(defproject tgen "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3297"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]

  :npm {:dependencies [three "0.84.0"
                       three-orbit-controls "72.0.0"
                       stats.js "0.16.0"]}

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.5"]
            [lein-npm "0.6.2"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :figwheel true
              :compiler {:main tgen.core
                         :asset-path "js/compiled/out"
                         :output-to "resources/public/js/compiled/tgen.js"
                         :output-dir "resources/public/js/compiled/out"
                         :foreign-libs [{:file "node_modules/stats.js/build/stats.min.js"
                                         :provides ["stats"]}
                                        {:file "node_modules/three/build/three.js"
                                         :provides ["three"]}]
                         :source-map-timestamp true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/tgen.js"
                         :main tgen.core
                         :optimizations :advanced
                         :foreign-libs [{:file "node_modules/stats.js/build/stats.min.js"
                                         :provides ["stats"]}
                                        {:file "node_modules/three/build/three.min.js"
                                         :provides ["three"]}]
                         :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"]})
