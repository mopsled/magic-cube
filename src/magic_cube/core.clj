(ns magic-cube.core)

(defn orthogonal-directions [direction]
  (case direction
    (:x-plus :x-minus) [:y-plus :y-minus :z-plus :z-minus]
    (:y-plus :y-minus) [:x-plus :x-minus :z-plus :z-minus]
    (:z-plus :z-minus) [:x-plus :x-minus :y-plus :y-minus]))

(defn alter-coordinates [[x y z] f move]
  (case move
    (:x-plus :x-minus) [(f x) y z]
    (:y-plus :y-minus) [x (f y) z]
    (:z-plus :z-minus) [x y (f z)]))

(defn touched-coordinates [coor move amount]
  (for [i (range 1 (inc amount))]
    (case move
      (:x-plus :y-plus :z-plus) (alter-coordinates coor #(+ % i) move)
      (:x-minus :y-minus :z-minus) (alter-coordinates coor #(- % i) move))))

(defn valid-cube? [cube moves size]
  (loop [cube cube moves moves touched {'(0 0 0) true} last-touched '(0 0 0)]
    (if (empty? moves)
      true
      (let [touching (touched-coordinates last-touched (first moves) (- (first cube) 1))]
        (if (and (not (some #(get touched %) touching)) (every? #(<= 0 % (- size 1)) last-touched))
          (recur (rest cube) (rest moves) (reduce #(assoc %1 %2 true) touched touching) (last touching)))))))

(defn solve
  ([cube size] (solve cube [:y-plus] size))
  ([cube moves size]
   (if (valid-cube? cube moves size)
     (if (= (count cube) (count moves))
       moves
       (some #(solve cube (conj moves %) size) (orthogonal-directions (last moves)))))))
