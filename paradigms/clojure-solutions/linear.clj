(defn vector-of-scalars? [v]
  (and (vector? v)
       (or (empty? v) (every? number? v))))

(defn same-length-vectors? [& vs]
  (or (every? empty? vs) (apply = (map count vs))))

(defn matrix? [m]
  (and (vector? m)
       (every? vector-of-scalars? m)
       (apply = (map count m))))

(defn same-shape-matrices? [& ms]
  (and (every? matrix? ms)
       (apply = (map count ms))
       (apply = (map #(map count %) ms))))

(defn v-abstract [f]
  (fn [& xs]
    {:pre [(and (every? vector-of-scalars? xs) (apply same-length-vectors? xs))]
     :post [(apply same-length-vectors? (cons % xs))]}
    (apply mapv f xs)))

(def v+ (v-abstract +))
(def v- (v-abstract -))
(def v* (v-abstract *))
(def vd (v-abstract /))

(defn v*s
  ([v] v)
  ([v s & ss]
   {:pre [(vector-of-scalars? v)
          (every? number? (cons s ss))]
    :post [(same-length-vectors? % v)]
    }
   (let [scale (reduce * s ss)]
     (mapv #(* % scale) v))))


(defn scalar [& vs]
  {:pre [(and (every? vector-of-scalars? vs) (apply same-length-vectors? vs))]
   :post [(number? %)]}
  (reduce + (apply v* vs)))

(defn vect
  ([v]
   {:pre [(vector-of-scalars? v)
          (= 3 (count v))]
    :post [(vector-of-scalars? %)
           (= 3 (count %))]}
   v)
  ([v1 v2]
   {:pre [(vector-of-scalars? v1)
          (vector-of-scalars? v2)
          (= 3 (count v1)) (= 3 (count v2))]
    :post [(vector-of-scalars? %)
           (= 3 (count %))]}
   [(- (* (v1 1) (v2 2)) (* (v1 2) (v2 1)))
    (- (* (v1 2) (v2 0)) (* (v1 0) (v2 2)))
    (- (* (v1 0) (v2 1)) (* (v1 1) (v2 0)))])
  ([v1 v2 & vs]
   (reduce vect (vect v1 v2) vs)))

(defn m-abstract [f]
  (fn [& ms]
    {:pre [(apply same-shape-matrices? ms)]
     :post [(apply same-shape-matrices? (cons % ms))]}
    (apply mapv (v-abstract f) ms)))

(def m+ (m-abstract +))
(def m- (m-abstract -))
(def m* (m-abstract *))
(def md (m-abstract /))

(defn m*s
  ([m] m)
  ([m s & ss]
   {:pre [(matrix? m)
          (every? number? (cons s ss))]
    :post [(same-shape-matrices? % m)]
    }
   (let [scale (reduce * s ss)]
     (mapv #(v*s % scale) m))))



(defn transpose [m]
  {:pre [(matrix? m)]
   :post [(or (and (empty? (first m))(empty? %)) (matrix? %))]
   }
  (apply mapv vector m))

(defn m*v [m v]
  {:pre [(matrix? m)
         (vector-of-scalars? v)
         (= (count (first m)) (count v))]
   :post [(and (vector-of-scalars? %) (= (count m) (count %)))]
   }
  (mapv #(scalar % v) m))

(defn m*m [& matrices]
  (reduce
    (fn [a b]
      {:pre [(matrix? a)
             (matrix? b)
             (= (count (first a)) (count b))]
       :post [(and (matrix? %) (= (count %) (count a)))]
       }
      (let [bt (transpose b)]
        (mapv (fn [row]
                (mapv #(scalar row %) bt))
              a)))
    matrices))

(defn expand [x]
  (cond
    (vector? x) x
    (number? x) [x]
    ))

(defn pad-right [v len]
  (vec (concat v (repeat (- len (count v)) 0))))

(defn normalize-args [& args]
  (let [vs (map expand args)
        max-len (apply max (map count vs))]
    (map #(pad-right % max-len) vs)))
(defn b-abstract [f]
  (fn [& args]
    (if (every? number? args)
      (apply f args)
      (let [normed (apply normalize-args args)](apply mapv
             (fn [& xs]
               (apply (b-abstract f) xs))
             normed)))))
(defn n-divide
  ([x] (/ 1.0 (double x)))
  ([x & xs]
   (reduce (fn [a b]
             (/ a (double b)))
           (double x) xs)))
(def b+ (b-abstract +))
(def b- (b-abstract -))
(def b* (b-abstract *))
(def bd (b-abstract n-divide))
;(println (m*v (vector (vector 0.1 4.9)) (vector 5.0 5.0)))