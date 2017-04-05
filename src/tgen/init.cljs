(ns tgen.init)

(defn init-scene
  [scene]
  (let [geometry (js/THREE.BoxGeometry. 1 1 1)
        material (js/THREE.MeshBasicMaterial. (clj->js {:color 0x00FF00
                                                        :wireframe false}))
        cube (js/THREE.Mesh. geometry material)]
    (.add scene cube)
    {:cube cube}))