package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue<T> {
    //Invariant: for each i, a[i] != null, size = len(a)
    //immutable(n): for i = 0...n - 1 : a'[i] == a[i]

    //Pred: element != null
    //Post: size' == size + 1 && elements[size] == element && immutable(size)
    void enqueue(T element);

    //Pred: size > 0
    //Post: size' == size - 1 && immutable(size') && R = elements[size'] && elements'[size'] == null
    T dequeue();

    //Pred: size > 0
    //Post: size' == size && immutable(size) && R = elements[size - 1]
    T element();

    //Pred: true
    //Post: immutable(size) && R == size && size' == size
    int size();

    //Pred: true
    //Post: immutable(size) && R == (size == 0) && size' == size
    boolean isEmpty();

    //Pred: true
    //Post: size'== 0
    void clear();

    //Pred: predicate != null
    //Post: immutable(size) && size' == size && for i...result.size predicate(result.elements[i]) == true &&
    // for each elem in result.elements, result.elements.indexOf(elem) <= elements.indexOf(elem)
    Queue<T> filter(Predicate<T> predicate);

    //Pred: function != null
    //Post: immutable(size) && size' == size && for i...result.size result.elements[i] == function.apply(elements)
    Queue<T> map(Function<T, T> function);
}
