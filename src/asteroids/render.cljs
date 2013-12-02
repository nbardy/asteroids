(ns asteroids.render
  (:use [clojure.string :only [join]]))

(defn log [messages] 
  (.log js/console messages))

(def bunny-texture (js/PIXI.Texture.fromImage "images/bunny.png"))

(log bunny-texture)

(defn bunny 
  ([] (bunny (rand-int 500) (rand-int 500)))
  ([x y] (let [sprite (js/PIXI.Sprite. bunny-texture)]
           (set! (.-position.x sprite) x)
           (set! (.-position.y sprite) y)
           (set! (.-anchor.x sprite) 0.5)
           (set! (.-anchor.y sprite) 0.5)
           sprite)))
 
(defn draw! [stage state]
  (doall (map 
           (fn [x]  
             (. stage addChild (bunny)))
             state)))

(defn renderer []
  (let [renderer (js/PIXI.autoDetectRenderer (.-innerWidth js/window) 
                                             (.-innerHeight js/window))]
    (.appendChild (.-body js/document) (.-view renderer))
    (let [stage (js/PIXI.Stage. 0x221111)] ; Creature clojure to persist stage
      (fn [state] 
        (draw! stage state)
        (. renderer render stage)))))
