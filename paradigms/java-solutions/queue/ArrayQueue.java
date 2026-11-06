package queue;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

//Invariant: for each i, a[i] != null
//immutable(n): for i = 0...n - 1 : a'[i] == a[i]
//Model: a[0]...a[n-1]
public class ArrayQueue<T> extends AbstractQueue<T> {

    private T[] elements;
    private int front, rear;

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            T[] newElements = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, front, newElements, 0, size - front);
            System.arraycopy(elements, 0, newElements, size - front, front);
            front = 0;
            rear = size;
            elements = newElements;
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        elements = (T[]) new Object[10];
        front = rear = size = 0;
    }


    @Override
    protected void enqueueImpl(T element) {
        ensureCapacity(size + 1);
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
    }

    //Pred: element != null
    //Post: immutable(size) - (starting from 1 up to n - 1) && size' = size + 1 && elements'[front] == element
    public void push(T element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    @Override
    protected T dequeueImpl() {
        T result = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        return result;
    }

    //Pred: size > 0
    //Post: size' = size - 1 && R == elements[rear] && elements'[rear] == null
    public T remove() {
        assert size > 0;
        rear = (rear - 1 + elements.length) % elements.length;
        T result = elements[rear];
        elements[rear] = null;
        size--;
        return result;
    }

    //Pred: size > 0
    //Post: size' == size && immutable(size) && R = a[size - 1]
    @Override
    public T element() {
        assert size > 0;
        return elements[front];
    }

    //Pred: size > 0
    //Post: immutable(size) && R == a[0]
    public T peek() {
        assert size > 0;
        return elements[(rear - 1 + elements.length) % elements.length];
    }

    //Pred: index >= 0 && index < size
    //Post: R == elements[rear - index] && immutable(n) && size' == size
    public T get(int index) {
        assert index >= 0 && index < size;
        return elements[(rear - 1 - index + elements.length) % elements.length];
    }

    //Pred: index >= 0 && index < size && element != null
    //Post: elements[rear - index] == element && size' == size
    // for i = 0...index - 1, index + 1... size - 1 : a'[i] = a[i]
    public void set(int index, T element) {
        assert index >= 0 && index < size;
        Objects.requireNonNull(element);
        elements[(rear - 1 - index + elements.length) % elements.length] = element;
    }

    //Pred: true
    //Post: immutable(size) && R == (size == 0) && size' == size
    public boolean isEmpty() {
        return size == 0;
    }

    //Pred: true
    //Post: immutable(size) && R == size && size' == size
    public int size() {
        return size;
    }

    //Pred: true
    //Post: size'== 0
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        elements = (T[]) new Object[10];
        front = rear = size = 0;
    }

    @Override
    public Queue<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return filterImpl(predicate);
    }

    @Override
    protected ArrayQueue<T> filterImpl(Predicate<T> predicate) {
        ArrayQueue<T> result = new ArrayQueue<>();
        for (int i = 0; i < size; i++) {
            if (elements[(front + i) % elements.length] != null && predicate.test(elements[(front + i) % elements.length])) {
                result.enqueue(elements[(front + i) % elements.length]);
            }
        }
        return result;
    }

    @Override
    protected ArrayQueue<T> mapImpl(Function<T, T> function) {
        ArrayQueue<T> result = new ArrayQueue<>();
        for (int i = 0; i < size; i++) {
            if (elements[(front + i) % elements.length] != null) {
                result.enqueue(function.apply(elements[(front + i) % elements.length]));
            }
        }
        return result;
    }
}