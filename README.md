# lein-pocketbook [![Clojars Project](https://img.shields.io/clojars/v/lein-pocketbook.svg)](https://clojars.org/lein-pocketbook) [![pipeline status](https://gitlab.com/vise890/lein-pocketbook/badges/master/pipeline.svg)](https://gitlab.com/vise890/lein-pocketbook/commits/master)

A Leiningen plugin to add sources / javadocs to your dev dependencies so that
you can jump to them in emacs.

## [API Docs](https://vise890.gitlab.io/lein-pocketbook/)

## Usage

Add something like this to your `project.clj`
```clojure
  :plugins [[lein-pocketbook "0.1.4-SNAPSHOT"]]
```

**TODO** `profiles.clj` is the place to put it, test and make sure it works...

```clojure
;; NOTE: untested
{:user {:plugins [[lein-pocketbook "0.1.4-SNAPSHOT"]]}}
```

## Outstanding Issues

- disk caching is very hacky
- why does it do it twice? (`lein repl`)
- it will overwrite any deps with the "sources" or "javadoc" classifier that are also part of the full dep graph
- doesn't add java srcs

## License

Copyright © 2017 Martino Visintin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
