(ns magic-cube.core)

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

(defn valid-cube? [cube moves size]
  (loop [x 0 y 0 z 0 cube cube moves moves been-to {}]
    (if (empty? moves)
      true
      (if (and (every? #(<= 0 % (- size 1)) [x y z]) (not (get been-to [x y z])))
        (let [[xp yp zp] (alter-coordinates x y z (first moves) (- (first cube) 1))]
          (recur xp yp zp (rest cube) (rest moves) (assoc been-to [x y z] true)))))))
(defn solve
  ([cube size] (solve cube [:y-plus] size))
  ([cube moves size]
   (if (valid-cube? cube moves size)
     (if (= (count cube) (count moves))
       moves
       (some #(solve cube (conj moves %) size) (orthogonal-directions (last moves)))))))
