(ns asteroids.lib.macros) 

(defmacro component [name params & r]
  `(defn ~name ~params 
     (list ~(keyword name) ~@r)))

(defmacro update-components [entity & cases]
  `(map (fn [co] (case (:type co) ~@cases)) ~entity))
