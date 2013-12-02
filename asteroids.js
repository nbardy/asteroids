goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.array', 'goog.object', 'goog.string.StringBuffer']);
goog.addDependency("../clojure/string.js", ['clojure.string'], ['cljs.core', 'goog.string', 'goog.string.StringBuffer']);
goog.addDependency("../asteroids/render.js", ['asteroids.render'], ['cljs.core', 'clojure.string']);
goog.addDependency("../asteroids/components.js", ['asteroids.components'], ['cljs.core']);
goog.addDependency("../asteroids/newgame.js", ['asteroids.newgame'], ['cljs.core', 'asteroids.components']);
goog.addDependency("../asteroids/core.js", ['asteroids.core'], ['cljs.core', 'asteroids.render', 'asteroids.newgame']);