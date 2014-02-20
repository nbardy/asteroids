(ns asteroids.core
  (:require [asteroids.newgame :as newgame]
            [asteroids.update :as update]
            [asteroids.components :refer [velocity]]
            [asteroids.lib.lib :refer [get-component]])
  (:use [asteroids.renderer :only [renderer]]))

(def render (renderer))

(defn animate [state]
  (js/requestAnimationFrame (fn []
      (animate (update/next-state state 0))))
  (render state))

; Start the game loop
(js/requestAnimationFrame 
  (fn [] (animate (newgame/create))))
