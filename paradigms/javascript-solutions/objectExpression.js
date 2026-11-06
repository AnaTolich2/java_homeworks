const OperationPrototype = {
    evaluate(x, y, z) {
        return this.calc(...this.operands.map(op => op.evaluate(x, y, z)));
    },
    toString() {
        return this.operands.map(op => op.toString()).join(' ') + ' ' + this.opSymbol;
    },
    diff(variable) {
        return this.diffImpl(...this.operands.map(op => op.diff(variable)));
    },
    prefix() {
        return '(' + this.opSymbol + ' ' + this.operands.map(op => op.prefix()).join(' ') + ')';
    },
    postfix() {
        return '(' + this.operands.map(op => op.postfix()).join(' ') + ' ' + this.opSymbol + ')';
    }
};
const Operation = (calc, opSymbol, diffImpl) => {
    function OperationConstructor(...operands) {
        this.calc = calc;
        this.opSymbol = opSymbol;
        this.diffImpl = diffImpl;
        this.operands = operands;

    }

    OperationConstructor.prototype = OperationPrototype;
    return OperationConstructor;
};

const Add = Operation(
    (a, b) => a + b, '+',
    function (da, db) {
        return new Add(da, db);
    }
);

const Subtract = Operation(
    (a, b) => a - b, '-',
    function (da, db) {
        return new Subtract(da, db);
    }
);

const Multiply = Operation(
    (a, b) => a * b, '*',
    function (da, db) {
        return new Add(
            new Multiply(da, this.operands[1]),
            new Multiply(this.operands[0], db)
        );
    }
);

const Divide = Operation(
    (a, b) => a / b, '/',
    function (da, db) {
        const [a, b] = this.operands;
        return new Divide(
            new Subtract(new Multiply(da, b), new Multiply(a, db)),
            new Multiply(b, b)
        );
    }
);
const SumCb = Operation(
    (...args) => args.reduce((acc, val) => acc + val ** 3, 0), 'sumCb',
    function (...dargs) {
        return this.operands
            .map((op, i) => new Multiply(dargs[i], new Multiply(new Const(3), new Multiply(op, op))))
            .reduce((acc, expr) => new Add(acc, expr), new Const(0));
    }
);

const Rmc = Operation(
    (...args) => Math.cbrt(args.reduce((acc, val) => acc + val ** 3, 0) / args.length), 'rmc',
    function (...dargs) {
        const n = this.operands.length;
        const avg = new Rmc(...this.operands);
        const factor = new Divide(new Const(1), new Multiply(new Multiply(avg, avg), new Const(n)));

        return dargs
            .map((darg, i) =>
                new Multiply(darg, new Multiply(this.operands[i], new Multiply(this.operands[i], factor)))
            )
            .reduce((acc, expr) => new Add(acc, expr), new Const(0));
    }
);
const Negate = Operation(
    a => -a, 'negate',
    function (da) {
        return new Negate(da);
    }
);

const prototypeNormalN = (n) => {
    const NormalN = Operation(
        (...args) => {
            const sumSq = args.reduce((acc, val) => acc + val * val, 0);
            return Math.exp(-sumSq / 2) / Math.pow(2 * Math.PI, n / 2);
        },
        'normal' + n,
        function (...dargs) {
            const sum = this.operands.map((fi, i) =>
                new Multiply(fi, dargs[i])
            ).reduce((acc, term) =>
                new Add(acc, term), new Const(0)
            );
            const normalExpr = new NormalN(...this.operands);

            return new Multiply(new Negate(sum), normalExpr);
        }
    );
    return NormalN;
};
const Normal1 = prototypeNormalN(1);
const Normal2 = prototypeNormalN(2);
const Normal3 = prototypeNormalN(3);
const Normal4 = prototypeNormalN(4);
const Normal5 = prototypeNormalN(5);

function Const(value) {
    const cnst = {
        evaluate: function () {
            return this.value
        },
        toString: function () {
            return this.value.toString()
        },
        diff: function () {
            return new Const(0)
        },
        prefix: function () {
            return this.value.toString()
        },
        postfix: function () {
            return this.value.toString()
        }
    };
    cnst.value = value;
    return cnst;
}

const operations = {
    '+': [Add, 2],
    '-': [Subtract, 2],
    '*': [Multiply, 2],
    '/': [Divide, 2],
    'negate': [Negate, 1],
    'normal1': [Normal1, 1],
    'normal2': [Normal2, 2],
    'normal3': [Normal3, 3],
    'normal4': [Normal4, 4],
    'normal5': [Normal5, 5],
    'rmc': [Rmc, -1],
    'sumCb': [SumCb, -1]
};
const variables = ['x', 'y', 'z'];

function Variable(name) {
    const variable = {
        evaluate: function (x, y, z) {
            return arguments[variables.indexOf(this.name)];
        },
        toString: function () {
            return this.name
        },
        diff: function (v) {
            return new Const(this.name === v ? 1 : 0)
        },
        prefix: function () {
            return this.name;
        },
        postfix: function () {
            return this.name;
        }
    }
    variable.name = name;
    return variable;
}

function parseExpression(expression, isPrefix) {
    let i = 0;
    const n = expression.length;

    function skipWS() {
        while (i < n && expression[i] === ' ') i++;
    }

    function getToken() {
        skipWS();
        if (i >= n) return null;
        if (expression[i] === '(' || expression[i] === ')') return expression[i++];

        let token = '';
        if (expression[i] === '-') {
            token += '-';
            i++;
            if (i >= n || !/\d/.test(expression[i])) return token;
        }
        while (i < n && ![' ', '(', ')'].includes(expression[i])) {
            token += expression[i++];
        }
        return token;
    }

    function parseToken(token) {
        if (token === '(') {
            let operands = [];
            let operatorToken = null;
            if (isPrefix) {
                operatorToken = getToken();
                if (!(operatorToken in operations)) {
                    throw new Error(`Unknown operator: ${operatorToken} at position ${i - operatorToken.length}`);
                }
            }
            let gotOperator = false;
            while (true) {
                skipWS();
                if (expression[i] === ')') {
                    i++; break;
                }
                const start = i;
                const nextToken = getToken();

                if (nextToken === null) throw new Error("Unexpected end of input");

                if (!isPrefix && nextToken in operations) {
                    if (gotOperator) {
                        throw new Error(`Multiple operators in postfix expression at position ${start}`);
                    }
                    operatorToken = nextToken;
                    gotOperator = true;
                    continue;
                }
                operands.push(parseToken(nextToken));
            }

            if (!gotOperator && !isPrefix) {
                throw new Error("Missing operator in postfix expression");
            }
            const [OpClass, arity] = operations[operatorToken];
            if (operands.length !== arity && arity !== -1) {
                throw new Error(`Expected ${arity} operands, got ${operands.length}`);
            }
            return new OpClass(...operands);
        } else if (token === ')') {
            throw new Error(`Unexpected ')' at position ${i - 1}`);
        } else if (token in operations) {
            throw new Error(`Operator '${token}' must be inside parentheses`);
        } else if (/^-?\d+$/.test(token)) {
            return new Const(parseFloat(token));
        } else if (variables.includes(token)) {
            return new Variable(token);
        } else {
            throw new Error(`Unexpected token '${token}' at position ${i - token.length}`);
        }
    }
    if (!expression) throw new Error("Empty expression");
    const result = parseToken(getToken());
    skipWS();
    if (i !== n) throw new Error(`Unexpected characters at position ${i}: '${expression.slice(i)}'`);
    return result;
}

const parsePrefix = (expression) => parseExpression(expression, true);
const parsePostfix = (expression) => parseExpression(expression, false);


const parse = (expression) => {
    const stack = [];
    const tokens = expression.split(/\s+/);
    for (const token of tokens) {
        if (!isNaN(parseFloat(token))) {
            stack.push(new Const(parseFloat(token)));
        } else if (variables.includes(token)) {
            stack.push(new Variable(token));
        } else if (token in operations) {
            const args = operations[token][1] > 0 ? stack.splice(-operations[token][1]) : [];
            stack.push(new operations[token][0](...args));
        }
    }
    return stack.pop();
};
// let expr = parsePostfix("(-53 negate)");
// println(expr)