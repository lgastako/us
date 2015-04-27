(ns us.infra)

#+clj
(defmacro defcomp [& args]
  `(do
     (require '[om-tools.core :refer-macros [defcomponentk]])
     (defcomponentk ~@args)))
