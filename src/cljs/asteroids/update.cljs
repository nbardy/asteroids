(ns asteroids.update
  (:require [asteroids.newgame :as newgame]
            [asteroids.components :refer [in-air velocity]]
            [asteroids.lib.lib :refer [get-component]])
  (:require-macros [asteroids.lib.macros :refer [update-components]])
  (:use [asteroids.renderer :only [renderer]]))

(defn map-hash-values [f m]
  (zipmap (keys m) (map f (vals m))))

(defn debug [messages] 
  (js* "debugger;")
  (.log js/console messages))

; Checks if the entity is in the given group
(defn is-in? [entity group]
  (let [groups (or (get-component :groups entity) [])]
      (some #(= group %) groups)))

(defn key-down? [key-name]
  (aget (.state js/Keyboard) key-name))

; TODO: This code needs to be made pretty with a macro/wrapper
(defn move [entity] 
  (let [vel (get-component :velocity entity)
        yvel (:y vel)
        xvel (:x vel)]
    (map (fn [[component-key value]] 
           [component-key
            (case component-key ;choose the
              :position (merge value {:x (+ xvel (:x value))
                                      :y (+ yvel (:y value))})
              :direction (cond (< xvel 0) (merge value {:deg 180 })
                               (> xvel 0) (merge value {:deg 0 })
                               :else value)

              :frame (merge value {:current 
                                   (mod (-> value 
                                            :current 
                                            (+ (/ xvel 7))) 
                                        (:total value))})
              value)])
         entity)))

(defn accel [entity [xaddition yaddition]] 
  (map (fn [[component-key value]] 
         [component-key
          (case component-key ;choose the
            :velocity (merge value {:x (+ xaddition (:x value))
                                    :y (+ yaddition (:y value))})
            value)])
  entity))

(defn move-towards-zero [x]
  (let [r (- x (/ x 6.5))]
  (if (or (< x -0.5) (> x 0.5))
    r
    0)))


  
(defn slowdown [entity] 
  (map (fn [[component-key value]] 
         [component-key
          (case component-key ;choose the
            :velocity (let [speed (:x value)]
                        (merge value {:x (move-towards-zero (:x value))}))
            value)])
  entity))

(defn jump [entity] 
  (if (not (in-air? entity))
    (map (fn [[component-key value]] 
           [component-key
            (case component-key ;choose the
              :in-air true
              value)])
         (accel entity [0 -10]))
    entity))

(defn accel-left [entity] (accel entity [-1 0]))
(defn accel-right [entity] (accel entity [1 0]))

(defn pan [entity speed]
  (map (fn [component] 
         (let [value (second component)
               component-key (first component)]
           [component-key
            (case component-key ;choose the
              :position (merge value {:x (+ speed (:x value))})
              value)]))
       entity))

(defn pan-left [entity] (pan entity -3))
(defn pan-right [entity] (pan entity 3))

(defn in-air? [entity]
  (get-component :in-air entity))

(defn gravity [entity]
  (if (in-air? entity)
    (do 
      (.log js/console "inar")
      (accel entity [0 1]))
    entity))

(defn land [state] 
  (fn [entity]
    (if (< (:y (get-component :position entity)) 
           (:y (get-component :position (:background state))))
      entity
      (map (fn [component] 
             (let [value (second component)
                   component-key (first component)]
               [component-key
                (case component-key ;choose the
                  :in-air false
                  :velocity (merge value {:y 0})
                  value)]))
           entity))))

(defn left-key-down? [& more] (key-down? "left"))
(defn right-key-down? [& more] (key-down? "right"))
(defn up-key-down? [& more] (key-down? "up"))
(defn always [& more] true)

(defn update-if [entity rules]
  (reduce (fn [e [predicate action]] (if predicate (action e) e))
          entity (partition 2 rules)))

(defn update-entity [kw state  & more]
  (let [entity (kw state)]
    (merge state 
           {kw (update-if entity more)})))

(defn update-controllable [state]
  (update-entity :player state
                 (key-down? "up") jump
                 (key-down? "right") accel-right
                 (key-down? "left") accel-left
                 (always) move
                 (always) gravity
                 (always) (land state)
                 (always) slowdown))


(defn update-viewport [state]
  (let [playerx (->> state :player (get-component :position) :x)
        viewx (->> state :viewport (get-component :position) :x)]
    (update-entity :viewport state
               (> (- playerx viewx) 600) pan-right
               (< (- playerx viewx) 200) pan-left)))

(defn update-timestamp [state] state)

(defn next-state [state dt]
    (-> state 
        update-timestamp
        update-controllable
        update-viewport))

(defn current-time []
  (.getTime (js/Date.)))
