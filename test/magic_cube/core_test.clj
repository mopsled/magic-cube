(ns magic-cube.core-test
  (:require [clojure.test :refer :all]
            [magic-cube.core :refer :all]))

(deftest orthogonal-directions-test
  (testing "Orthogonal directions to +x are y and z directions"
    (is (= [:y-plus :y-minus :z-plus :z-minus] (orthogonal-directions :x-plus)))))

(deftest alter-coodinates-plus
  (testing "Should alter +y coordinate"
    (is (= [0 1 0] (alter-coordinates 0 0 0 :y-plus 1)))))

(deftest alter-coodinates-minus
  (testing "Should alter -z coordinate"
    (is (= [1 2 3] (alter-coordinates 1 2 5 :z-minus 2)))))

(deftest valid-cube-simple-test
  (testing "Simple arrangement should be valid"
    (is (= true (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-minus :z-plus] 2)))))

(deftest valid-cube-out-of-bounds-test
  (testing "Out-of-bounds arrangement should be valid"
    (is (= nil (valid-cube? [2 2 2 2] [:y-plus :x-plus :y-plus :x-plus] 2)))))

(deftest solve-very-simple-test
  (testing "A one-piece puzzle should have one solution"
    (is (= 1 (count (solve [2] 2))))))

(deftest solve-simple-test
  (testing "A two-peice puzzle should have four solutions"
    (is (= 4 (count (solve [2 2] 2))))))
