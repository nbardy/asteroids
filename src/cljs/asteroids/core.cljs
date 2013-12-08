(ns asteroids.core
  (:require [asteroids.newgame :as newgame])
  (:require-macros [asteroids.lib.macros :refer [update-components]])
  (:use [asteroids.render :only [renderer]]))

(defn get-component [key entity]
  (some #(when (=(:type %) key) %) entity))

(defn map-hash-values [f m]
  (zipmap (keys m) (map f (vals m))))

(defn debug [messages] 
  (js* "debugger;")
  (.log js/console messages))

(def render (renderer))

(defn update-if [entity & more]
  (reduce (fn [e [predicate action]] (if predicate (action e) e))
          entity (partition 2 more)))


(defn update-position [position]
  (condp #(aget %2 %) (.state js/Keyboard)
    "right" (merge position {:x (-> position :x inc) })
    "left" (merge position {:x (-> position :x dec) })
    "down" (merge position {:y (-> position :y inc) })
    "up" (merge position {:y (-> position :y dec) })
    position))

(defn key-down? [key-name]
  (aget (.state js/Keyboard) key-name))

; This code needs to be made pretty
(defn move [entity speed] 
  (map (fn [component] 
         (merge component 
                ; Call the appropriate fn for the component
                ((case (:type component) ;choose the
                   :position (fn [p] {:x (+ (:x p) speed) })
                   :direction (fn [dir] {:deg (if (< speed 0) 180 0)})
                   :frame (fn [frame] {:current (mod 
                                                  (-> frame :current inc) 
                                                  (:total frame))})
                   identity)
                           component))) entity))

(defn move-left [entity] (move entity -2))
(defn move-right [entity] (move entity 2))
    ;; (update-components entity
    ;;                    :position (fn [position]
    ;;                                (merge {:x (+ (:x position) speed) }
    ;;                                       position))
    ;;                    :direction (fn [direction] (merge {:facing :right} direction)))))

(defn apply-gravity [x] x)
(defn jump [x] x)

(defn in-air? [x] false)

(def jump-size 10)
(defn update-entity [entity dt]
  (update-if entity
             (key-down? "right") move-right
             (key-down? "left") move-left
             (key-down? "up") jump
             (in-air? entity) apply-gravity))

(defn next-state [state dt]
  (map-hash-values #(update-entity % dt) state))

(defn animate [state]
  (js/requestAnimationFrame (fn [] (animate (next-state state dt))))
  (render state))

(js/requestAnimationFrame (fn [] (animate (newgame/create))))
