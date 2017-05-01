(ns ^:figwheel-always tgen.core
    (:require [tgen.hooks :refer [hooks]]
              [tgen.state :refer [initial-state]]
              [tgen.engine :refer [create-scene]]))

(enable-console-print!)

(defonce STATE (atom {}))

(defn start-app
  []
  (when-let [destroy-scene (get-in @STATE [:scene :destroy])]
    (destroy-scene))
  (swap! STATE assoc :scene (create-scene hooks initial-state)))

(start-app)
