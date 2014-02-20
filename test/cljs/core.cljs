(ns asteroids.test.core
  (:require-macros [asteroids.test.macros :refer 
                    [is]])
  (:require [asteroids.components :refer [velocity]]))

(is "true is true" (= true true))

(is "Base Velocity Test" (= (velocity 2 3) 
                            (velocity 2 3)))

(is "Component First item name" (= (first (velocity 2 3))
                                   :velocity))
