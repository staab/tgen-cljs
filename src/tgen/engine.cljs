(ns tgen.engine
  (:require [tgen.utils :as u]
            [three]))

(defn create-camera
  [{:keys [fov aspect near far]}]
  (js/THREE.PerspectiveCamera. fov aspect near far))

(defn create-light
  [{:keys [kind color intensity]}]
  (cond
    (= kind :ambient) (js/THREE.AmbientLight. color intensity)
    :else (throw (js/Error. (str "invalid light kind " kind)))))

(defn mount-scene
  [state]
  (let [width (.-innerWidth js/window)
        height (.-innerHeight js/window)
        scene (js/THREE.Scene.)
        world-size (:world-size state)
        camera (create-camera (merge
                                {:fov 50
                                 :aspect (/ width height)
                                 :near 1
                                 :far (js/Math.pow world-size 2)}
                                (:camera state)))
        lights (u/map-vals create-light (u/clog (:lights state)))]
    {:camera camera
     :lights lights}))

(defn create-scene
  [hooks initial-state]
  (let [state (atom initial-state)
        scene (atom (mount-scene initial-state))]
    {:get-state (fn [] @state)
     :get-scene (fn [] @scene)
     :destroy (fn [] (println "destroying"))}))

