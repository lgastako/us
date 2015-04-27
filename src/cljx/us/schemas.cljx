(ns us.schemas)

(defn make-make [init-state validator]
  (fn []
    (doto (atom init-state)
          (set-validator! validator))))

(def Query s/Str)
