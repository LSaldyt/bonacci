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

(defm expression []
  (uniform-draw [(list-atom) (list (sample (nat-operator)) (list-atom) (list-atom))]))

(defm list-function []
  ;(fn [x] (list-atom)))
  ;`(fn [~(symbol 'x)] ~(list-atom)))
  `(fn [~(symbol 'x)] ~(sample (expression))))

(defm test-function [f x]
  ((eval f) x))
  ;(eval `(~(eval f) ~x)))

(defquery nums
  (let [;a  (sample (integer))
        ;b  (sample (integer))
        ;op (sample (nat-operator))
        ;n  (sample (uniform-discrete 1 N))
        t  (list-function)
        ;e  (sample (expression))
        ;l  (map #(test-function t %) (range n))
        ;ld (map #(test-function (list-function) %) (range n))
        ]
    (println t)
    (println (eval t))
    (println ((eval t) 2))
    (println (test-function t 2))
    ;(println (eval `(~(eval t) ~2)))
    ;(println e)
    ;(println l)
    ;(println ((eval t) 3))
    ;(observe ld (list 2 2 2 2))
    ;(loop [elements l
    ;       known-list (list 2 2 2 2)]
    ;  (if (or (empty? elements)
    ;          (empty? known-list))
    ;    nil
    ;    (let [elem (first elements)
    ;          known (first known-list)]
    ;      (do
    ;        (observe-eq elem known)
    ;        (recur (rest elements) (rest known-list))
    ;        ))))
    ;(observe-eq l (list 2 2 2 2))
    ;(observe-eq (apply op (list a b)) 7)
    ;(observe-eq (apply (eval t) (list 2)) 2)
    ;(predict [a b op])))
    ;(observe-eq (test-function t 1) 4)
    ;(observe-eq (test-function t 2) 4)
    ;(observe-eq (test-function t 3) 9)
    ;(observe-eq (test-function t 4) 16)
    (predict t)
    ;(observe)
    ;(predict t)
    ))

