(ns magic-cube.core)

(def two-cube [2 2 2 2 2 2 2])
(def four-cube [3 2 3 2 2 4 2 3 2 3 2 3 2 2 2 2 2 2 2 2 3 3 2 2 2 2 2 3 4 2 2 2 4 2 3 2 2 2 2 2 2 2 2 2 4 2])

(def directions [:x-plus :x-minus :y-plus :y-minus :z-plus :z-minus])

(defn orthogonal-directions [direction]
  (case direction
    (:x-plus :x-minus) [:y-plus :y-minus :z-plus :z-minus]
    (:y-plus :y-minus) [:x-plus :x-minus :z-plus :z-minus]
    (:z-plus :z-minus) [:x-plus :x-minus :y-plus :y-minus]
   ))

(defn alter-coordinates [x y z move amount]
  (if (not (or (nil? move) (nil? amount)))
    (case move
      :x-plus [(+ x amount) y z]
      :x-minus [(- x amount) y z]
      :y-plus [x (+ y amount) z]
      :y-minus [x (- y amount) z]
      :z-plus [x y (+ z amount)]
      :z-minus [x y (- z amount)])))

(= [0 1 0] (alter-coordinates 0 0 0 :y-plus 1)) ;; Should alter y-plus coordinate
(= [1 2 3] (alter-coordinates 1 2 5 :z-minus 2)) ;; Should alter z-minus coordinate

(defn valid-cube? [cube moves size]
  (loop [x 0 y 0 z 0 cube cube moves moves been-to {}]
    (if (empty? moves)
      true
      (if (and (<= 0 x (- size 1)) (<= 0 y (- size 1)) (<= 0 z (- size 1)) (not (contains? been-to [x y z])))
        (let [[xp yp zp] (alter-coordinates x y z (first moves) (- (first cube) 1))]
          (recur xp yp zp (rest cube) (rest moves) (assoc been-to [x y z] true)))))))

(= true (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-minus :z-plus] 2)) ; Simple arrangement should be valid
(= nil (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-plus :x-plus] 2)) ; Out-of-bounds arrangement shound be invalid

(defn solve
  ([cube size] (partition (count cube) (filter (complement nil?) (flatten (solve cube [:y-plus] size)))))
  ([cube moves size]
   (if (valid-cube? cube moves size)
     (if (= (count cube) (count moves))
       moves
       (for [move (orthogonal-directions (last moves))]
         (solve cube (conj moves move) size))))))

(solve two-cube 2)
