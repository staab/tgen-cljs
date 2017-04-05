(ns tgen.scene
    (:require [three]
              [tgen.state :refer [STATE]]))

(defn create-scene
  [world-size init render]
  (let [width (.-innerWidth js/window)
        height (.-innerHeight js/window)
        scene (js/THREE.Scene.)
        camera (js/THREE.PerspectiveCamera. 50
                                            (/ width height)
                                            1
                                            (js/Math.pow world-size 2))
        ambient (js/THREE.AmbientLight. 0x402020)
        point1 (js/THREE.PointLight. 0xccccff 1 (js/Math.pow world-size 2))
        point2 (js/THREE.PointLight. 0xccccff 1 (js/Math.pow world-size 2))
        renderer (js/THREE.WebGLRenderer.)
        cam-dist world-size
        ;; An "alive" flag to let us kill the animation refresh when we tear down:
        RUNNING (atom true)]

   (.. point1 -position (set 300, 300, 1000))
   (.. point2 -position (set -300, -300, 1000))
   (.. camera -position (set cam-dist cam-dist cam-dist))
   (.setSize renderer width, height)
   (.appendChild (.-body js/document) (.-domElement renderer))
   (.lookAt camera (js/THREE.Vector3. 0 0 0))
   (.add scene camera)
   (.add scene ambient)
   (.add scene point1)
   (.add scene point2)

   (let [render-data (init scene)]
     (letfn [(animate
               []
               ; Throttle framerate a bit so we don't send our computer to space
               (when @RUNNING (js/setTimeout #(js/requestAnimationFrame animate) 50))
               (render render-data)
               (.render renderer scene camera)
               (.update (:stats @STATE)))]
       (animate)))

   {:stopper #(reset! RUNNING false)
    :renderer renderer}))
