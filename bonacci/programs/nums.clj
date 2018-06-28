(ns nums
  (:use [anglican emit runtime]))

;(define (random-range n) (range 1 (random-integer n)))
;(define (nat-operator) (uniform-draw '(+ - *))) ; No division: Produces fractional types from integers
;(define (integer)  (random-integer N))
;(define (atom) (if (flip) (integer) 'x))
;(define (expression) (uniform-draw
;                      (list
;                       (atom)
;                       (list (nat-operator) 'x (atom))
;                       (list (nat-operator) (atom) 'x))))
;
;(define (create-repeat) (list 'repeat (expression) (list 'lambda '() (expression))))
;(define (create-range)  (list 'range  (quote (unquote (expression))
;                                             (unquote (expression)))))
;(define (list-literal)   (list (map (lambda () (expression))
;                                    (random-range 5))))
;(define (list-type)     (uniform-draw (list (create-repeat) (create-range) (list-literal))))
;(define (concat)        (list 'flatten (map (lambda () (list-type))
;                                           (random-range 5))))

(def N 10)

(defm random-range [n]
  (uniform-discrete 0 n))

(defm nat-operator []
  (uniform-draw [+ - *]))

(defm to-dist [x]
  (normal x 0.0000001))

(defm observe-eq [a b]
  (observe (to-dist a) b))

(defm integer []
  (random-range N))

(defm list-atom []
  (if (flip 0.5) 
    (sample (integer)) 
    'x))

;(defm expression []
  ;(uniform-draw [()]))

(defm list-function []
  ;(fn [x] (list-atom)))
  `(fn [~(symbol 'x)] ~(list-atom)))

(defm test-function [f x]
  (eval `(~(eval f) 4)))

(defquery nums
  (let [a  (sample (integer))
        b  (sample (integer))
        op (sample (nat-operator))
        n  (sample (uniform-discrete 1 N))
        t  (list-function)
        l  (map #(test-function t %) (range n))]

    (println l)
    ;(println ((eval t) 3))
    (loop [elements l
           known-list (list 2 2 2 2)]
      (if (or (empty? elements)
              (empty? known-list))
        nil
        (let [elem (first elements)
              known (first known-list)]
          (do
            (observe-eq elem known)
            (recur (rest elements) (rest known-list))
            ))))
    ;(observe-eq l (list 2 2 2 2))
    ;(observe-eq (apply op (list a b)) 7)
    ;(observe-eq (apply (eval t) (list 2)) 2)
    ;(predict [a b op])))
    (predict [t])
    ))

