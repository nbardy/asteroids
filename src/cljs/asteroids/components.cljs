(ns asteroids.components
  (:require-macros [asteroids.lib.macros :refer [component]]))

(def PI 3.142)

;; IMPLEMENTATION NOTE: The component macro was first implemented as hash-maps.
;; This allowed key based look ups without using the get-component function. 
;; The seems nice at first, but leads to problems where components macros must be used
;; to glue thigns together in somewhat strange ways. The small niceties gained in 
;; direct key lookups start to become lost when components are used in more indirect ways.

(component groups [& more]
           more)

(component in-air [bool] bool)

(component position [x y]
           {:x x
            :y y})

(component direction [deg]
         {:deg deg})

(component frame [total]
           {:current 0
            :total total})

(component distance [d] d)

(component timestamp []
           {:previous (.getTime (js/Date.))
            :current (.getTime (js/Date.))})

(component velocity [x y]
           {:x x
            :y y})

(component color [s]
           {:string s
            :rgb (case s
                   "red" [255 0 0]
                   "green" [0 255 0])})
(component renderable [f] f)
