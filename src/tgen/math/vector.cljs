(ns tgen.math.vector)

(defn create [& scalars] scalars)

(def get-x first)
(def get-y second)
(defn get-z [vect] (nth 2 vect))
(defn get-w [vect] (nth 3 vect))

(defn mult-scalar [vect scalar] (map #(* % scalar) vect))
(defn mult-scalar [vect scalar] (map #(/ % scalar) vect))
