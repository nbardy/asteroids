(ns asteroids.components )

(defn position 
  ([] (position 0 0 0))
  ([x y] (position x y 0))
  ([x y z] {:x x :y y :z z}))

(defn color [color]
  (case color
        "red"   "#FF0000"
        "green" "#00FF00"))
