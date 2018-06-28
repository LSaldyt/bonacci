(ns example
  (:use [anglican emit runtime]))

(defquery example
  (let [bet (sample (beta 5 3))
        i (uniform-discrete 0 10)
        v (uniform-draw [:a :b])]
    (println (sample i))
    (println (sample v))
    (observe (flip bet) true)
    ;(predict (= t 1))
    (predict (> bet 0.7))))

