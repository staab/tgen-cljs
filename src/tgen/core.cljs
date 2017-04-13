(ns ^:figwheel-always tgen.core
    (:require [figwheel.client :as fw]
              [tgen.hooks :refer [hooks]]
              [tgen.state :refer [initial-state]]
              [tgen.engine :refer [create-scene]]))

(enable-console-print!)

(def STATE (atom {}))

(defn get-state [] (clj->js @STATE))

(defn get-scene [] (clj->js ((get-in @STATE [:scene :get-scene]))))

(defn start-app
  []
  (println "starting")
  (swap! STATE assoc :scene (create-scene hooks initial-state)))

(defn stop-app
  []
  (println "stopping")
  (if-let [destroy-scene (get-in @STATE [:scene :destroy])]
    (destroy-scene)))

(fw/start {:on-jsload #(do (stop-app) (start-app))})

(start-app)
