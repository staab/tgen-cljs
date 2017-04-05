(ns ^:figwheel-always tgen.core
    (:require [figwheel.client :as fw]
              three
              stats
              [tgen.scene :refer [create-scene]]
              [tgen.init :refer [init-scene]]
              [tgen.render :refer [render-scene]]
              [tgen.state :refer [STATE]]))

(enable-console-print!)

(defn start-app
  []
  ; Stats
  (when-not (:stats @STATE)
    (let [stats (js/Stats.)]
      (set! (.. stats -domElement -style -position) "absolute")
      (set! (.. stats -domElement -style -left) "0px")
      (set! (.. stats -domElement -style -top) "0px")
      (.appendChild (.-body js/document) (.-domElement stats))
      (swap! STATE assoc :stats stats)))
  ; Scene
  (swap! STATE merge (create-scene 10 init-scene render-scene)))

(defn stop-app
  []
  (if-let [stopper (:stopper @STATE)]
    (stopper)
    (swap! STATE dissoc :stopper))
  (if-let [renderer (:renderer @STATE)]
    (.. js/document -body (removeChild (.-domElement renderer)))
    (swap! STATE dissoc :renderer))
  (if-let [stats (:stats @STATE)]
    (.removeChild (.-body js/document) (.-domElement stats))
    (swap! STATE dissoc :stats)))



(fw/start {:on-jsload #(do (stop-app) (start-app))})

(start-app)