# lein-pocketbook [![Clojars Project](https://img.shields.io/clojars/v/lein-pocketbook.svg)](https://clojars.org/lein-pocketbook) [![pipeline status](https://gitlab.com/vise890/lein-pocketbook/badges/master/pipeline.svg)](https://gitlab.com/vise890/lein-pocketbook/commits/master)

A Leiningen plugin to add sources / javadocs to your dev dependencies so that
you can jump to them / look them up in Emacs.

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

- Why does it do it twice? (try `lein repl`);
- 1 jump only. Jumping to Java from Clojure works.. but once you're in Java-land, you
  can't jump around anymore...
  - This needs to be implemented at the Emacs end of things I think;
- No way to provide `:exclusions`, all the deps will be searched for sources and Javadocs;
- It uses the (now deprecated) [lein middleware plugin
  mechanism](https://github.com/technomancy/leiningen/blob/master/doc/PLUGINS.md#project-middleware);
- Disk caching is very hacky, but it is otherwise waaay too slow. `lein clean`
  should suffice for clearing the cache;
- Does not add Java language sources (but these can be easily installed with
  your package manager);
- Possibly overwrites specified deps with `:sources` / `:javadoc` classifier:
  (seems very borderline though)
  - e.g. say, for some weird reason, you have these deps:
  ```clojure
  [com.foo/bar "0.1.2"]
  [com.foo/bar "0.1.3" :classifier "sources"]`
  ```
  `lein-pocketbook` will likely add both sources:
  ```clojure
  [com.foo/bar "0.1.2" :classifier "sources"]
  [com.foo/bar "0.1.3" :classifier "sources"]`
  ;; same for `javadoc`s
  ```

## License

Copyright © 2017 Martino Visintin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
