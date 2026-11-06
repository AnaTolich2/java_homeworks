
(defn op-abstract [f]
      (fn [& xs]
          (fn [vars]
              (apply f (map #(% vars) xs)))))
(defn constant [val]
      (fn [x] val))
(defn variable [name]
      (fn [vars] (vars name)))
(def add (op-abstract +))
(def subtract (op-abstract -))
(def multiply (op-abstract *))
(defn n-divide
      ([x] (/ 1.0 (double x)))
      ([x & xs]
       (reduce (fn [a b] (/ a (double b))) (double x) xs)))
(def divide (op-abstract n-divide))
(defn negate [expr]
      (fn [vars]
          (- (expr vars))))
(defn sum-sinh [& xs] (reduce + (map #(Math/sinh %) xs)))
(defn sum-cosh [& xs] (reduce + (map #(Math/cosh %) xs)))
(def sumSinh (op-abstract sum-sinh))
(def sumCosh (op-abstract sum-cosh))

(def meanSinh (op-abstract (fn [& xs]
                               (/ (reduce + (map #(Math/sinh %) xs)) (count xs)))))
(def meanCosh (op-abstract (fn [& xs]
                               (/ (reduce + (map #(Math/cosh %) xs)) (count xs)))))

(def operations
  {'+        add
   '-        subtract
   '*        multiply
   '/        divide
   'negate   negate
   'sumSinh  sumSinh
   'sumCosh  sumCosh
   'meanSinh meanSinh
   'meanCosh meanCosh})


(defn parseFunction [expr-str]
      (letfn [(parse [expr]
                     (cond
                       (number? expr) (constant expr)
                       (symbol? expr) (variable (name expr))
                       (list? expr)
                       (let [[op & args] expr]
                            (apply (operations op) (map parse args)))))]
             (parse (read-string expr-str))))

;;=========== Object expression ===========
(defn sumcb [& args] (apply + (map #(Math/pow % 3) args)))
(defn cbnorm [& args] (Math/cbrt (apply + (map #(Math/pow % 3) args))))
(defn proto-get
      ([this key] (proto-get this key nil))
      ([this key default]
       (cond
         (contains? this key) (this key)
         (contains? this :prototype) (proto-get (this :prototype) key default)
         :else default)))

(defn proto-call
      [this key & args]
      (apply (proto-get this key) this args))

(defn field [key]
      (fn ([this] (proto-get this key))
          ([this default] (proto-get this key default))))

(defn method [key]
      (fn [this & args] (apply proto-call this key args)))

(def __value (field :value))
(def __name (field :name))
(def __operands (field :operands))
(def __op (field :op))
(def __operand (field :operand))
(def __symbol (field :symbol))
(def _evaluate (method :evaluate))
(def _diff (method :diff))
(def _toString (method :toString))
(def _toStringPostfix (method :toStringPostfix))
(defn evaluate [expr vars] (_evaluate expr vars))
(defn diff [expr var-name] (_diff expr var-name))
(defn toString [expr] (_toString expr))
(defn toStringPostfix [expr] (_toStringPostfix expr))
(def _toStringInfix (method :toStringInfix))
(defn toStringInfix [expr] (_toStringInfix expr))
(def _diff-impl (method :diff-impl))
(defn diff-impl [expr var-name] (_diff-impl expr var-name))

(def OperationPrototype
  {:op              nil
   :evaluate        (fn [this vars]
                        (apply (__op this)
                               (map #(_evaluate % vars) (__operands this))))
   ;:diff            (fn [this var-name]
   ;                   (let [ops (__operands this)
   ;                         d-ops (map #(_diff % var-name) ops)]
   ;                     ()))
   ;:diff-impl
   :diff            nil
   :toString        (fn [this]
                        (str "(" (__symbol this) " "
                             (clojure.string/join " " (map toString (__operands this)))
                             ")"))
   :toStringPostfix (fn [this]
                        (str "("
                             (clojure.string/join " " (map toStringPostfix (__operands this)))
                             " " (__symbol this) ")"))
   :toStringInfix   (fn [this]
                        (let [[a b] (__operands this)]
                             (str "(" (toStringInfix a) " " (__symbol this) " " (toStringInfix b) ")")))})

(defn _Operation [this & operands]
      (assoc this :operands operands))
(declare CbNorm Add Subtract SumCb Multiply Divide Negate ZERO)
(defn create-operation-constructor [proto]
      (fn [& operands]
          (apply _Operation {:prototype proto} operands)))
(def ConstantPrototype
  {:evaluate        (fn [this _] (__value this))
   :diff            (fn [_ _] ZERO)
   :toString        (fn [this] (str (__value this)))
   :toStringPostfix (fn [this] (str (__value this)))
   :toStringInfix   (fn [this] (str (__value this)))})
(defn _Constant [this value]
      {:value value :prototype this})

(def Constant
  (fn [value] (_Constant ConstantPrototype value)))
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def VariablePrototype
  {:evaluate        (fn [this vars]
                        (get vars (Character/toString (Character/toLowerCase (last (__name this))))
                             0))
   :diff            (fn [this var-name]
                        (if (= (__name this) var-name) ONE ZERO))
   :toString        (fn [this] (__name this))
   :toStringPostfix (fn [this] (__name this))
   :toStringInfix   (fn [this] (__name this))})

(defn _Variable [this name]
      (assoc this :name name))

(def Variable
  (fn [name]
      (_Variable {:prototype VariablePrototype} name)))
(def NegatePrototype
  {:evaluate        (fn [this vars]
                        (- (_evaluate (__operand this) vars)))
   :diff            (fn [this var-name]
                        (Negate (_diff (__operand this) var-name)))
   :toString        (fn [this]
                        (str "(negate " (toString (__operand this)) ")"))
   :toStringPostfix (fn [this]
                        (str "(" (toStringPostfix (__operand this)) " negate)"))
   :toStringInfix   (fn [this]
                        (str "negate " (toStringInfix (__operand this))))})
;Negate with OperationPrototype
(defn _Negate [this operand]
      (assoc this :operand operand))

(def Negate
  (fn [operand] (_Negate {:prototype NegatePrototype} operand)))

(def AddPrototype
  {:prototype OperationPrototype
   :op        +
   :diff      (fn [this var-name]
                  (let [d-ops (map #(_diff % var-name) (__operands this))]
                       (apply Add d-ops)))
   :symbol    '+})

(def SubtractPrototype
  {:prototype OperationPrototype
   :op        -
   :diff      (fn [this var-name]
                  (let [d-ops (map #(_diff % var-name) (__operands this))]
                       (apply Subtract d-ops)))
   :symbol    '-})

(def MultiplyPrototype
  {:prototype OperationPrototype
   :op        *
   :diff      (fn [this var-name]
                  (let [ops (__operands this)
                        d-ops (map #(_diff % var-name) ops)]
                       (apply Add
                              (map-indexed
                                (fn [i _]
                                    (apply Multiply
                                           (concat
                                             (take i ops)
                                             [(nth d-ops i)]
                                             (drop (inc i) ops))))
                                ops))))
   :symbol    '*})

(def DividePrototype
  {:prototype OperationPrototype
   :op        n-divide
   :diff      (fn [this var-name]
                  (let [ops (__operands this)
                        d-ops (map #(_diff % var-name) ops)]
                       (if (= 1 (count ops))
                         (let [d1 (first d-ops)]
                              (Negate (Divide d1 (Multiply (first ops) (first ops)))))
                         (let [num (first ops)
                               denoms (rest ops)
                               denom (apply Multiply denoms)
                               d-num (first d-ops)
                               d-denoms (rest d-ops)
                               d-denom (apply Add
                                              (map-indexed
                                                (fn [i _]
                                                    (apply Multiply
                                                           (concat
                                                             (take i denoms)
                                                             [(nth d-denoms i)]
                                                             (drop (inc i) denoms))))
                                                denoms))]
                              (Divide
                                (Subtract
                                  (Multiply d-num denom)
                                  (Multiply num d-denom))
                                (Multiply denom denom))))))
   :symbol    '/})

(def SumCbPrototype
  {:prototype OperationPrototype
   :op        sumcb
   :diff      (fn [this var-name]
                  (let [ops (__operands this)
                        d-ops (map #(_diff % var-name) ops)]
                       (apply Add
                              (map (fn [operand d]
                                       (Multiply d
                                                 (Multiply (Constant 3)
                                                           (Multiply operand operand))))
                                   ops d-ops))))
   :symbol    'sumCb})

(def CbNormPrototype
  {:prototype OperationPrototype
   :op        cbnorm
   :diff      (fn [this var-name]
                  (let [ops (__operands this)
                        sumcb-exp (apply SumCb ops)
                        d-sumcb (_diff sumcb-exp var-name)]
                       (Divide
                         d-sumcb
                         (Multiply
                           (Constant 3)
                           (Multiply (apply CbNorm ops)
                                     (apply CbNorm ops))))))
   :symbol    'cbNorm})
(def IPowPrototype
  {:prototype OperationPrototype
   :op        (fn [a b] (Math/pow a b))
   :diff      (fn [this var-name]
                  ZERO)
   :symbol    '**})

(def IPow (create-operation-constructor IPowPrototype))

(def ILogPrototype
  {:prototype OperationPrototype
   :op        (fn [a b]
                  (/ (Math/log (Math/abs b))
                     (Math/log (Math/abs a))))
   :diff      (fn [this var-name]
                  ZERO)
   :symbol    (symbol nil "//")})

(def ILog (create-operation-constructor ILogPrototype))

(def Add (create-operation-constructor AddPrototype))
;(def Add (create-operation-constructor + '+ (fn [args] (diffme))))
(def Subtract (create-operation-constructor SubtractPrototype))
(def Multiply (create-operation-constructor MultiplyPrototype))
(def Divide (create-operation-constructor DividePrototype))
(def SumCb (create-operation-constructor SumCbPrototype))
(def CbNorm (create-operation-constructor CbNormPrototype))

(def obj-operations
  {'+                Add
   '-                Subtract
   '*                Multiply
   '/                Divide
   'negate           Negate
   'sumCb            SumCb
   'cbNorm           CbNorm
   '**               IPow
   (symbol nil "//") ILog})


(defn parseObject [expr-str]
      (letfn [(parse [expr]
                     (cond
                       (number? expr) (Constant expr)
                       (symbol? expr) (Variable (name expr))
                       (list? expr)
                       (let [[op & args] expr]
                            (apply (obj-operations op) (map parse args)))))]
             (parse (read-string expr-str))))

;=======Comb Parser=======
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
(defn -show [result]
      (if (-valid? result)
        (str
          "-> " (pr-str (-value result))
          " | " (pr-str (apply str (-tail result))))
        "!"))
(defn tabulate [parser inputs]
      (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (-show (parser input))))
            inputs))
(def _empty (partial partial -return))
(defn _char [p]
      (fn [[c & cs]]
          (if (and c (p c))
            (-return c cs))))
(defn _map [f]
      (fn [result]
          (if (-valid? result)
            (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b]
      (fn [input]
          (let [ar ((force a) input)]
               (if (-valid? ar)
                 ((_map (partial f (-value ar)))
                  ((force b) (-tail ar)))))))
(defn _either [a b]
      (fn [input]
          (let [ar (a input)]
               (if (-valid? ar)
                 ar
                 ((force b) input)))))
(defn +char [chars]
      (_char (set chars)))
(defn +char-not [chars]
      (_char (comp not (set chars))))
(defn +map [f parser]
      (comp (_map f) parser))
(def +ignore
  (partial +map (constantly 'ignore)))
(defn- iconj [coll value]
       (if (= value 'ignore)
         coll
         (conj coll value)))
(defn +seq [& parsers]
      (reduce (partial _combine iconj) (_empty []) parsers))
(defn +seqf [f & parsers]
      (+map (partial apply f) (apply +seq parsers)))
(defn +seqn [n & parsers]
      (apply +seqf #(nth %& n) parsers))
(defn +or [parser & parsers]
      (reduce _either parser parsers))
(defn +opt [parser]
      (+or parser (_empty nil)))
(defn +star [parser]
      (letfn [(rec [] (+or (+seqf cons parser (delay (rec))) (_empty ())))]
             (rec)))
(defn +plus [parser]
      (+seqf cons parser (+star parser)))
(defn +str [parser]
      (+map (partial apply str) parser))
(def *digit (+char "0123456789"))
(def *string
  (+seqn 1
         (+char "\"")
         (+str (+star (+char-not "\"")))
         (+char "\"")))
(def *space (+char " \t\n\r"))
(defn _parser [parser]
      (let [pp (_combine (fn [v _] v) parser (_char #{\u0000}))]
           (fn [input] (-value (pp (str input \u0000))))))
(def *ws (+ignore (+star *space)))
(def *null (+seqf (constantly nil) (+char "n") (+char "u") (+char "l") (+char "l")))
(def *all-chars (mapv char (range 32 128)))
(def +parser _parser)
(def *letter (+char (filter Character/isLetter *all-chars)))
(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit)))))
;end of copied code

(def *number (+map read-string (+str (+seq (+str (+seqf cons (+opt (+char "-")) (+plus *digit))) (+str (+opt (+seqf cons (+char ".") (+plus *digit))))))))
(defn *any-operator [args] (+map (comp obj-operations symbol) (+seqn 0 *ws (+str (+plus (+or *letter (+char args)))) *ws)))
(def *operator-token (*any-operator "+-/*"))
(def *number-token (+map Constant (+seqn 0 *ws *number *ws)))
(def *variable-token (+map Variable (+str (+seqn 0 *ws (+plus (+char "xyzXYZ")) *ws))))
(defn *literal [value name] (apply +seqf (constantly value) (map (comp +char str) name)))
;===Postfix
(declare *postfix-expr)
(declare *parenthesis)
(def *primitive (+seqn 0 *ws (delay (+or *number-token *variable-token *parenthesis)) *ws))
(def *parenthesis (+seqn 0 (+ignore (+char "(")) (delay *postfix-expr) (+ignore (+char ")"))))
(def *postfix-expr (+map (fn [[args op]] (apply op args)) (+seq (+plus *primitive) *operator-token)))
(defn parseObjectPostfix [expr] (let [s (if (string? expr) expr (pr-str expr)) result ((+plus *primitive) s)] (first (-value result))))
;=====Infix=====
(defn *lit-token [name] (+seqn 0 *ws (*literal (obj-operations name) (str name)) *ws))
(def *negate-op (*lit-token 'negate))
(declare *infix-expr)
(declare *infix-parenthesis)
(def *infix-primary (+seqn 0 *ws (delay (+or *number-token *variable-token *infix-parenthesis)) *ws))
(defn parenthesis-with [[open close]] (+seqn 0 (+ignore (+char (str open))) (delay *infix-expr) (+ignore (+char (str close)))))
(def *infix-parenthesis (apply +or (map parenthesis-with (partition 2 "()[]<>{}"))))
(def *infix-unary
  (+or (+map (fn [[ops x]] (reduce (fn [acc op] (op acc)) x ops)) (+seq (+star *negate-op) (delay *infix-primary))) *infix-primary))
(defn make-infix-level [next-priority ops-symb right?]
      (let [ops (mapv *lit-token ops-symb)]
           (if right?
             (letfn [(parser [] (+map (fn [[x opt]]
                                          (if opt
                                            (let [[op y] opt] (op x y))
                                            x))
                                      (+seq (delay next-priority) (+opt (+seq *ws (apply +or ops) *ws (delay (parser)))))))] (parser))
             (+map (fn [[first rest]] (reduce (fn [acc [op y]] (op acc y)) first rest)) (+seq (delay next-priority) (+star (+seq *ws (apply +or ops) *ws (delay next-priority))))))))
(def *infix-powlog (make-infix-level *infix-unary ['**, (symbol nil "//")] true))
(def *infix-muldiv (make-infix-level *infix-powlog ['* '/] false))
(def *infix-addsub (make-infix-level *infix-muldiv ['+ '-] false))
(def *infix-expr *infix-addsub)
(defn parseObjectInfix [expr] ((+parser *infix-expr) expr))
;(parseObjectInfix "         0.0 ")
