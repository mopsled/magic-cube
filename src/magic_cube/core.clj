(ns magic-cube.core)

(defn orthogonal-directions [direction]
  (case direction
    (:x-plus :x-minus) [:y-plus :y-minus :z-plus :z-minus]
    (:y-plus :y-minus) [:x-plus :x-minus :z-plus :z-minus]
    (:z-plus :z-minus) [:x-plus :x-minus :y-plus :y-minus]))

(defn alter-coordinates [x y z f move]
  (if (not (nil? move))
    (case move
      (:x-plus :x-minus) [(f x) y z]
      (:y-plus :y-minus) [x (f y) z]
      (:z-plus :z-minus) [x y (f z)])))

(defn touched-coordinates [x y z move amount]
  (let [move-map {:x-plus inc :x-minus dec :y-plus inc :y-minus dec :z-plus inc :z-minus dec}]
    (loop [x x y y z z touched '() i 0]
      (let [[x y z] (alter-coordinates x y z (move move-map) move)]
        (if (= i amount)
          touched
          (recur x y z (conj touched (list x y z)) (inc i)))))))

(defn valid-cube? [cube moves size]
  (loop [x 0 y 0 z 0 cube cube moves moves touched {(list x y z) true}]
    (if (every? #(<= 0 % (- size 1)) [x y z])
      (if (empty? moves)
        true
        (let [touching (touched-coordinates x y z (first moves) (- (first cube) 1))
              [x y z] (first touching)]
          (if (not (some #(get touched %) touching))
            (recur x y z (rest cube) (rest moves) (reduce #(assoc %1 %2 true) touched touching))))))))

(defn solve
  ([cube size] (solve cube [:y-plus] size))
  ([cube moves size]
   (if (valid-cube? cube moves size)
     (if (= (count cube) (count moves))
       moves
       (some #(solve cube (conj moves %) size) (orthogonal-directions (last moves)))))))