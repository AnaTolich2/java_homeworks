package queue;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue<T> implements Queue<T> {
    protected int size;

    @Override
    public int size() {
        return size;
    }

    //Pred: element != null
    //Post: n' == n + 1 && a[n] == element && immutable(n)
    @Override
    public void enqueue(T element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(T element);

    //Pred: size > 0
    //Post: n' == n - 1 && immutable(n') && R = a[n']
    @Override
    public T dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }

    protected abstract T dequeueImpl();

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    //NOTE: order
    //Pred: predicate != null
    //Post: immutable(size) && size' == size && for i...result.size predicate(result.elements[i]) == true &&
    // for each elem in result.elements, result.elements.indexOf(elem) <= elements.indexOf(elem)
    @Override
    public Queue<T> filter(Predicate<T> predicate){
        Objects.requireNonNull(predicate);
        return filterImpl(predicate);
    }
    protected abstract Queue<T> filterImpl(Predicate<T> predicate);

    //Pred: function != null
    //Post: immutable(size) && size' == size && for i...result.size result.elements[i] == function.apply(elements)
    @Override
    public Queue<T> map(Function<T, T> function){
        Objects.requireNonNull(function);
        return mapImpl(function);
    }
    protected abstract Queue<T> mapImpl(Function<T, T> function);
}
