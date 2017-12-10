(defproject lein-pocketbook "0.1.4-SNAPSHOT"

  :description "A Leiningen plugin to add sources / javadocs to your dev dependencies so that you can jump to them in emacs."

  :url "http://gitlab.com/vise890/lein-pocketbook"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :profiles {:ci  {:deploy-repositories
                   [["clojars" {:url           "https://clojars.org/repo"
                                :username      :env
                                :password      :env
                                :sign-releases false}]]}}

  :eval-in-leiningen true)
