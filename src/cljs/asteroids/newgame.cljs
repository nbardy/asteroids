(ns asteroids.newgame
  (:use [asteroids.components :only [PI direction position color frame]]))

(defn create []
  {:player [(position 0 50)
            (direction 0)
            (frame 8)
            (color "red")]
            })
