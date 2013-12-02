(ns asteroids.core
  (:require [asteroids.newgame :as newgame])
  (:use [asteroids.render :only [renderer]]))

(defn log [messages] 
  (.log js/console messages))

(def render (renderer))

(defn next-state [state]
  (conj state {:name "s"}))

(defn animate [state]
  (js/requestAnimationFrame (fn [] (animate (next-state state))))
  (render state))

(js/requestAnimationFrame (fn [] (animate (next-state state))))
