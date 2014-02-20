(ns asteroids.render.player
  (:use [clojure.string :only [join]])
  (:require [asteroids.lib.lib :refer [get-component]]))

(def textures
  (vec (for [x (range 8)]
    (js/PIXI.Texture.fromImage (join ["images/man/frame-" x ".png"])))))

(defn player-texture [frame]
  (get textures (.floor js/Math frame)))

(defn render [entity viewport] 
  (let [position (get-component :position entity)
        frame (get-component :frame entity)
        direction (get-component :direction entity)
        scale (if (-> direction :deg (= 180)) -1 1)
        current-frame (:current frame)
        sprite (js/PIXI.Sprite. (player-texture current-frame))]
    (let [offsetx (- (:x position) (:x (get-component :position viewport)))]
           (set! (.-position.x sprite) offsetx)
           (set! (.-position.y sprite) (:y position))
           (set! (.-anchor.x sprite) 0.5)
           (set! (.-anchor.y sprite) 1)
           (set! (.-scale.x sprite) scale)
           sprite)))
