"use strict"

const fold = (operation) => (...funcs) => (...args) => operation(...funcs.map(fn => fn(...args)));

const add = fold((x, y) => x + y);
const multiply = fold((x, y) => x * y);
const subtract = fold((x, y) => x - y);
const divide = fold((x, y) => x / y);
const negate = fold((x) => -x);
const cnst = (x) => () => x;

const variables = ['x', 'y', 'z', 't'];
const variable = (name) => (...args) => args[variables.indexOf(name)];
const tau = () => 2 * Math.PI;
const phi = () => (1 + Math.sqrt(5)) / 2;
const less3 = fold((x, y, z) => (x < y) && (y < z) ? 1 : 0);
const greater4 = fold((x, y, z, v) => (x > y) && (y > z) && (z > v) ? 1 : 0);

const operations = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'tau' : [() => tau, 0],
    'phi' : [() => phi, 0],
    'less3': [less3, 3],
    'greater4': [greater4, 4],
};
//либо такое: - ps написал в комментах доп вариант, думал спросить на праке, пишу узнав об асинх
// const cnsts = {
//     'tau' : tau,
//     'phi' : phi,
// }
// :NOTE: оба варианта норм

const parse = (expression) => {
    const stack = [];
    const tokens = expression.split(/\s+/);
    for (const token of tokens) {
        if (!isNaN(parseFloat(token))) {
            stack.push(cnst(parseFloat(token)));
        } else if (variables.includes(token)) {
            stack.push(variable(token));
        } else if (token in operations) {
            //splice(0) - все равно достает 1 элемент
            //slice требует stack.length -= 1
            const args = operations[token][1] > 0 ? stack.splice(-operations[token][1]) : [];
            stack.push(operations[token][0](...args));
        }
        // else (if token in cnsts){
        //     stack.push(cnsts[token])
        // }
    }
    return stack.pop();
};
