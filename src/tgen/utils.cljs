(ns tgen.utils
  (:require [cljs.pprint :refer [pprint]]))

(defn clog [v] (pprint v) v)

(defn map-vals [f m] (into {} (for [[k v] m] [k (f v)])))
