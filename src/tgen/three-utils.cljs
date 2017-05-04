(ns tgen.three-utils
  (:require [three]
            [tgen.utils :as u]))

; Constructors

(defn create-camera
  [{:keys [fov aspect near far]}]
  (js/THREE.PerspectiveCamera. fov aspect near far))

; Updaters

(defn position-camera
  [camera [pos-x pos-y pos-z] [look-at-x look-at-y look-at-z]]
  (.. camera -position (set pos-x pos-y pos-z))
  (.lookAt camera (js/THREE.Vector3. look-at-x look-at-y look-at-z)))

; Light multimethods

(defmulti set-light :type)

(defmethod set-light :ambient
  [{:keys [color intensity]}]
  (js/THREE.AmbientLight. color intensity))

(defmethod set-light :point
  [{:keys [color intensity distance pos]}]
  (let [light (js/THREE.PointLight. color intensity distance)
        [x y z] pos]
    (.. light -position (set x y z))
    light))

; Material multimethods

(defmulti set-mat :type)

(defmethod set-mat :basic
  [mat]
  (js/THREE.MeshBasicMaterial. (clj->js (dissoc mat :type))))

; Mesh multimethods

(defmulti set-mesh :type)

(defmethod set-mesh :box
  ([{[pos-x pos-y pos-z] :pos [dim-x dim-y dim-z] :dim mat :mat}]
    (let [geometry (js/THREE.BoxGeometry. dim-x dim-y dim-z)
          box (js/THREE.Mesh. geometry (set-mat mat))]
      ; Debug code
      (js/THREE.BoxHelper. box 0xffff00)
      box))
  ([cur prev mesh]
   (u/clog mesh)))
