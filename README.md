# lein-pocketbook

A Leiningen plugin to add sources / javadocs to your dev dependencies so that
you can jump to them in emacs.

## Usage

Add something like this to your `project.clj`
```clojure
  :plugins [[lein-pocketbook "0.1.4-SNAPSHOT"]]
  :middleware [lein-pocketbook.plugin/pocketbook-middleware-dependencies]
```



**TODO** `profiles.clj` is the place to put it, test and make sure it works...

```clojure

{:user {:plugins [[lein-pocketbook "0.1.4-SNAPSHOT"]]
        :middleware [lein-pocketbook.plugin/pocketbook-middleware-dependencies]}}
```


## License

Copyright © 2017 Martino Visintin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
