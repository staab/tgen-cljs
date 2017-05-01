(ns tgen.engine
  (:require [tgen.utils :as u]
            [tgen.three-utils :refer [create-camera
                                      set-light
                                      position-camera
                                      set-mesh]]
            [three]))

(defn mount-scene
  [state]
  (let [width (.-innerWidth js/window)
        height (.-innerHeight js/window)
        scene (js/THREE.Scene.)
        world-size (:world-size state)
        camera-state (:camera state)
        camera (create-camera (merge
                                {:fov 50
                                 :aspect (/ width height)
                                 :near 1
                                 :far (js/Math.pow world-size 2)}
                                camera-state))
        lights (u/map-vals set-light (:lights state))
        renderer (js/THREE.WebGLRenderer.)
        meshes
          (for [{:keys [id] :as data} (:meshes state)]
            (let [mesh (set-mesh data)]
              (.add scene mesh)
              [id mesh]))]
    (position-camera camera (:pos camera-state) (:look-at camera-state))
    (.setSize renderer width height)
    (.appendChild (.-body js/document) (.-domElement renderer))
    (.add scene camera)
    (doseq [light (vals lights)]
      (.add scene light))
    (doseq [mesh (vals meshes)]
      (.add scene mesh))
    ; Add scene to window for three.js inspector
    (set! (.-scene js/window) scene)
    {:camera camera
     :lights lights
     :renderer renderer
     :width width
     :height height
     :scene scene
     :meshes meshes}))

(defn render-scene
  [scene state hooks stop-animating]
  (when (not @stop-animating)
    (let [scene-data @scene
          renderer (:renderer scene-data)
          three-scene (:scene scene-data)
          camera (:camera scene-data)]
      (.render renderer three-scene camera)
      ; Throttle framerate a bit so we don't send our computer to space
      (js/setTimeout
        (fn [] (js/requestAnimationFrame
                 #(render-scene scene state hooks stop-animating)))
        500))))

(defn create-scene
  [hooks initial-state]
  (let [state (atom initial-state)
        scene (atom (mount-scene initial-state))
        stop-rendering (atom false)]
    (render-scene scene state hooks stop-rendering)
    {:state @state
     :scene @scene
     :destroy (fn []
                (swap! stop-rendering (constantly true))
                (when-let [renderer (:renderer @scene)]
                  (.remove (aget renderer "domElement"))))}))


