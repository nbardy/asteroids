(ns asteroids.renderer
  (:use [asteroids.lib.lib :only [get-component]]))

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

(defn preload-image [x]
  (.src js/Image x))

(defn draw [state]
  (let [stage (js/PIXI.Stage. "0x221111")]
    (doseq [[id entity] state]
      (let [renderable (get-component :renderable entity)
            viewport (:viewport state)]
        (if renderable
            (. stage addChild (renderable entity viewport))
          )))
    stage))

; Creates a new renderer by returning a render fucntion
(defn renderer []
  ;Creature closure to persist stage
  (let [pixirenderer (js/PIXI.autoDetectRenderer (.-innerWidth js/window) 
                                             (.-innerHeight js/window))]
    (.appendChild (.-body js/document) (.-view pixirenderer))
    (fn [state] (. pixirenderer render (draw state)))))
