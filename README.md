# magic-cube

A Clojure library designed to solve the [snake cube](http://en.wikipedia.org/wiki/Snake_cube).

![Solved snake cube](http://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Snakecube_2.jpg/320px-Snakecube_2.jpg)

## Usage

The input for `solve` is a sequence of cube segments and a cube size. For example:

![Snake cube unsolved](http://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Snakecube_1.jpg/320px-Snakecube_1.jpg)

is represented by `three-cube` in the code below, with segments starting from the bottom left of the picture.

    => (def three-cube [3 2 2 3 2 3 2 2 3 3 2 2 2 3 3 3 3])
    #'three-cube
    => (solve three-cube 3)
    [:y-plus :z-plus :y-minus :x-plus :z-plus :x-minus :y-plus :x-plus :z-minus :y-minus 
     :z-plus :x-minus :z-plus :x-plus :z-minus :y-plus :z-plus]
     
The output of `solve` represents the direction each segment should go, starting with the first segment in `three-cube`.

## License

MIT
