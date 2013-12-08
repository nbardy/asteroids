Asteroids
=========


Dependencies
------------
  - [npm](npmjs.org)
  - [lein](leiningen.org)
  - [clojurescript
Browserify and Beefy
```
npm install browserify beefy -g
```

How to Build
------------
lein cljsb




File Structure
--------------
    main.js -- Included JS libraries
    index.html -- Entry point
    package.json -- manages JS dependencies through [npm](npmjs.org)
    project.clj -- manages clojure dependencies through [lein](leiningen.org)
    images -- image resources
    src
        asteroids
            core.cljs -- Starts the main game loop.
            component.cljs -- Holds the components(Everything is made up of components)
            newgame.cljs -- Creates the initial game state.
            render.cljs -- Render the game state to the screen.
