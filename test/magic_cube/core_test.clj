
(ns magic-cube.core-test
  (:require [clojure.test :refer :all]
            [magic-cube.core :refer :all]))

(def two-cube [2 2 2 2 2 2 2])
(def three-cube [3 2 2 3 2 3 2 2 3 3 2 2 2 3 3 3 3])
(def four-cube [3 2 3 2 2 4 2 3 2 3 2 3 2 2 2 2 2 2 2 2 3 3 2 2 2 2 2 3 4 2 2 2 4 2 3 2 2 2 2 2 2 2 2 2 4 2])

(deftest orthogonal-directions-test
  (testing "Orthogonal directions to +x are y and z directions"
    (is (= [:y-plus :y-minus :z-plus :z-minus] (orthogonal-directions :x-plus)))))

(deftest alter-coodinates-plus-test
  (testing "Should alter +y coordinate"
    (is (= [0 1 0] (alter-coordinates 0 0 0 inc :y-plus)))))

(deftest alter-coodinates-minus-test
  (testing "Should alter -z coordinate"
    (is (= [1 2 4] (alter-coordinates 1 2 5 dec :z-minus)))))

(deftest touched-coodinates-one-test
  (testing "Should return one touched coordinate when moving once"
    (is (= '([0 1 0]) (touched-coordinates 0 0 0 :y-plus 1)))))

(deftest touched-coodinates-two-test
  (testing "Should return two touched coordinates when moving through two spaces"
    (is (= '([1 2 2] [1 2 1]) (touched-coordinates 1 2 3 :z-minus 2)))))

(deftest valid-cube-simple-test
  (testing "Simple arrangement should be valid"
    (is (= true (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-minus :z-plus] 2)))))

(deftest valid-cube-out-of-bounds-test
  (testing "Out-of-bounds arrangement should be valid"
    (is (= nil (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-plus :x-plus] 2)))))

(deftest solve-very-simple-test
  (testing "A one-piece puzzle should have a one-move solution"
    (is (= 1 (count (solve [2] 2))))))

(deftest solve-simple-test
  (testing "A two-peice cube should have a seven-move solution"
    (is (= 7 (count (solve two-cube 2))))))

(deftest solve-three-cube-test
  (testing "A 3x3x3 cube should have a 17-move solution"
    (is (= 17 (count (solve three-cube 3))))))