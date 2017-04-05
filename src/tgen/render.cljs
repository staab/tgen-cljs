(ns tgen.render)

(defn render-scene
  [{cube :cube}]
  (set! (.. cube -rotation -x)
        (+ 0.1 (.. cube -rotation -x)))
  (set! (.. cube -rotation -y)
        (+ 0.1 (.. cube -rotation -y))))