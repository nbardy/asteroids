(ns asteroids.newgame
  (:use [asteroids.components :only [position color]]))

(defn create []
  [{:name "ship" 
    :color (color "green")
    :pos (position 10 10)}

   {:name "enemy" 
    :color (color "red")
    :pos (position 10 10)}])
