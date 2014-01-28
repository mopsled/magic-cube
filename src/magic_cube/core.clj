(ns magic-cube.core)

(def four-cube '(3 2 3 2 2 4 2 3 2 3 2 3 2 2 2 2 2 2 2 2 3 3 2 2 2 2 2 3 4 2 2 2 4 2 3 2 2 2 2 2 2 2 2 2 4 2))

(def directions '(:x-plus :x-minus :y-plus :y-minus :z-plus :z-minus))

(defn orthogonal-directions [direction]
  (case direction
    (:x-plus :x-minus) '(:y-plus :y-minus :z-plus :z-minus)
    (:y-plus :y-minus) '(:x-plus :x-minus :z-plus :z-minus)
    (:z-plus :z-minus) '(:x-plus :x-minus :y-plus :y-minus)
   ))

;; TODO: Add valid cube check logic
(defn valid-cube? [cube moves]
  true)

(defn solve
  ([cube] (solve cube '(:y-plus)))
  ([cube moves]
   (if (= (count cube) (count moves))
     moves
     (first (for [move (orthogonal-directions (first moves))]
       (if (valid-cube? cube (conj moves move))
         (solve cube (conj moves move))))))))

(solve four-cube)
