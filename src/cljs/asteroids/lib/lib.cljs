(ns asteroids.lib.lib)

(defn get-component [key entity]
  (some #(when (= (first %) key) (second %)) entity))
