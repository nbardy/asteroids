(ns asteroids.render.background
  (:use [asteroids.lib.lib :only [get-component]]))

(defn debug [messages] 
  (js* "debugger;")
  (.log js/console messages))

(def ground-texture (js/PIXI.Texture.fromImage "images/background/gs2.png"))

(defn render [entity viewport] 
  (let [sprite 
        (js/PIXI.TilingSprite. ground-texture (.-innerWidth js/window) 64)
        position (get-component :position entity)
        vposition (get-component :position viewport)]
    (set! (.-position.x sprite) 0)
    (set! (.-position.y sprite) 
          (- (:y position) (:y vposition)))
    (set! (.-tilePosition.x sprite) 
          (- (:x position) (:x vposition)))
    (set! (.-tilePosition.y sprite) 64)
    sprite))
