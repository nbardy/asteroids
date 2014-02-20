(ns asteroids.newgame
  (:require [asteroids.render.player :as player]
            [asteroids.render.background :as background])
  (:use [asteroids.components :only [in-air PI distance direction position color frame renderable groups timestamp velocity]]))

; Groups 
;   viewport: contains the viewport
;   controlable: affected by user input
;   physics: affected by the "physics engine" 

(defn create []
  {:timestamp [(timestamp)]
   :background [(renderable background/render)
                (distance 3)
                (position 0 200)]

   :viewport [(position 0 0)]

   :player [(renderable player/render)
            (position 200 200)
            (in-air false)
            (velocity 0 0)
            (direction 0)
            (frame 8)
            (color "red")]
   })
