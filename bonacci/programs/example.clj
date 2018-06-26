(ns example
  (:use [anglican emit runtime]))

(defquery example
  (let [bet (sample (beta 5 3))
        i (uniform-discrete 0 10)]
    (println (sample i))
    (observe (flip bet) true)
    ;(predict (= t 1))
    (predict (> bet 0.7))))

