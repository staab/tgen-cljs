(ns tgen.state)

(def initial-state
  {:world-size 10
   :camera {:pos [5 5 5] :look-at [0 0 0]}
   :lights
   {:ambient {:type :ambient :color 0x402020 :intensity 1}
    :point1 {:type :point :color 0xccccff :pos [30 30 30] :intensity 1}
    :point2 {:type :point :color 0xccccff :pos [-30 -30 -30] :intensity 1}}
   :meshes
   [{:id :cube
     :type :box
     :pos [0 0 0]
     :dim [1 1 1]
     :mat {:type :basic :color 0x00ff00}}]})

