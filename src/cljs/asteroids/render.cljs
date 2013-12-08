(ns asteroids.render
  (:use [clojure.string :only [join]]))

(defn get-component [key entity]
  (some #(when (=(:type %) key) %) entity))

(defn man-texture [frame]
  (js/PIXI.Texture.fromImage (join ["images/man/frame-" frame ".png"])))


(defn man 
  ([x y frame scale] (let [sprite (js/PIXI.Sprite. (man-texture frame))]
           (set! (.-position.x sprite) x)
           (set! (.-position.y sprite) y)
           (set! (.-anchor.x sprite) 0.5)
           (set! (.-anchor.y sprite) 0.5)
           (set! (.-scale.x sprite) scale)
           sprite)))


(defn fmap [f m]
  (zipmap (keys m) (map f (vals m))))
 
;NOTE: Currently this is tossing out the old state and old sprites.
;If performance becomes an issue this will need to be migrate to mantain a set
;of sprites and compare between states. We can mantain one stage with a clojure
;identical to how we are mantaining one renderer.
;
;This will allow this function to mutate js objects on the stage, while
;keeping the clojure hash-map state immutable


(defn debug [messages] 
  (js* "debugger;")
  (.log js/console messages))

(defn draw [state]
  (def stage (js/PIXI.Stage. 0x221111))
  (doall (fmap 
           (fn [item]  
             (let [position (get-component :position item)
                   frame (get-component :frame item)
                   direction (get-component :direction item)]
               (def scale (if (-> direction :deg (= 180)) -1 1))
               (. stage addChild (man (:x position) 
                                      (:y position)
                                      (:current frame)
                                      scale))))
           state))
  stage)

(defn renderer []
  ;Creature clojure to persist stage
  (let [renderer (js/PIXI.autoDetectRenderer (.-innerWidth js/window) 
                                             (.-innerHeight js/window))]
    (.appendChild (.-body js/document) (.-view renderer))
    (fn [state] 
      (let [stage (draw state)]
        (. renderer render stage)))))
