# lein-pocketbook

A Leiningen plugin to add sources / javadocs to your dev dependencies so that
you can jump to them in emacs.

## Usage

Add something like this to your `:user` / `:dev` `:plugins`
```clojure
{:user {:plugins [[lein-pocketbook "0.1.3-SNAPSHOT"]]
        :middleware [lein-pocketbook.plugin/pocketbook-middleware-resources]}}
```

## License

Copyright © 2017 Martino Visintin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
