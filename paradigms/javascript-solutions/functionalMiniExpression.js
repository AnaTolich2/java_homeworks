"use strict"

let fold = (operation) => (...functions) => (...args) => {
    let results = functions.map(fn => fn(...args));
    return operation(...results);
};
let negate = fold((x) => -x);
let cnst = (x) => () => x;
let variable = () => (x) => x;

let parse = expression => {
    let stack = [];
    let token = '';
    expression = expression.toString();
    for (let i = 0; i < expression.length; i++) {
        while (expression[i] === ' ' && i < expression.length) i++;
        if (i >= expression.length) break;
        while (expression[i] !== ' ' && i < expression.length) {
            token += expression[i];
            i++;
        }
        if (!isNaN(parseFloat(token))) {
            stack.push(cnst(parseFloat(token)));
        } else if (token === 'x') {
            stack.push(variable('x'))
        } else if (token === 'negate') {
            stack.push(negate(stack.pop()));
        }
        token = '';
    }
    let f = stack.pop();
    return (x) => f(x);
}
// let expr = subtract(
//     multiply(
//         cnst(2),
//         variable("x")
//     ),
//     cnst(3)
// );
//
// println(expr(5));
