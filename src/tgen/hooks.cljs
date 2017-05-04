(ns tgen.hooks
  (:require [tgen.utils :as u]))

(defn animate-cube
  [state old-state]
  (->>
    (:meshes state)
    (mapv
      (fn [mesh]
        (when (= (:id  mesh) :cube)
          (assoc mesh :dim (mapv inc (:dim mesh))))))
    (assoc state :meshes)))

(def hooks
  [animate-cube])
