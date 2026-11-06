package queue;

import java.util.Objects;
//Invariant: for each i, a[i] != null
//immutable(n): for i = 0...n - 1 : a'[i] == a[i]
//Model: a[0]...a[n-1]
public class ArrayQueueADT<T> {
    private T[] elements;
    private int front, rear, size;
    @SuppressWarnings("unchecked")
    private static <T> void ensureCapacity(ArrayQueueADT<T> queue, int capacity) {
        if (capacity > queue.elements.length) {
            T[] newElements = (T[]) new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, queue.front, newElements, 0, queue.size - queue.front);
            System.arraycopy(queue.elements, 0, newElements, queue.size - queue.front, queue.front);
            queue.front = 0;
            queue.rear = queue.size;
            queue.elements = newElements;
        }
    }
    @SuppressWarnings("unchecked")
    public ArrayQueueADT() {
        elements = (T[]) new Object[10];
        front = rear = size = 0;
    }


    //Pred: element != null && queue != null
    //Post: size' == size + 1 && a[size] == element && immutable(size)
    public static <T>void enqueue(ArrayQueueADT<T> queue, T element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.elements[queue.rear] = element;
        queue.rear = (queue.rear + 1) % queue.elements.length;
        queue.size++;
    }
    //Pred: element != null && queue != null
    //Post: immutable(size) - (starting from 1 up to n - 1) && size' = size + 1 && elements[front'] == element
    public static <T>void push(ArrayQueueADT<T> queue, T element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.front = (queue.front - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.front] = element;
        queue.size++;
    }
    //Pred: size > 0 && queue != null
    //Post: size' == size - 1 && immutable(size') && R = elements[rear]  && elements'[rear] == null
    public static <T> T dequeue(ArrayQueueADT<T> queue) {
        assert queue.size > 0;
        T result = queue.elements[queue.front];
        queue.elements[queue.front] = null;
        queue.front = (queue.front + 1) % queue.elements.length;
        queue.size--;
        return result;
    }
    //Pred: size > 0 && queue != null
    //Post: immutable(n) && R == a[0] && size' == size
    public static<T> T peek(ArrayQueueADT<T> queue) {
        assert queue.size > 0;
        return queue.elements[(queue.rear - 1 + queue.elements.length) % queue.elements.length];
    }
    //Pred: size > 0
    //Post: size' = size - 1 && R == elements[rear] && elements'[rear] == null
    public static <T> T remove(ArrayQueueADT<T> queue) {
        assert queue.size > 0;
        queue.rear = (queue.rear - 1 + queue.elements.length) % queue.elements.length;
        T result = queue.elements[queue.rear];
        queue.elements[queue.rear] = null;
        queue.size--;
        return result;
    }
    //Pred: size > 0
    //Post: size' == size && immutable(size) && R = a[size - 1]
    public static <T> T element(ArrayQueueADT<T> queue) {
        assert queue.size > 0;
        return queue.elements[queue.front];
    }
    //Pred: index >= 0 && index < size
    //Post: R == elements[rear - index] && immutable(size) && size' == size
    public static<T> T get(ArrayQueueADT<T> queue, int index) {
        assert index >= 0 && index < queue.size;
        return queue.elements[(queue.rear - 1 - index + queue.elements.length) % queue.elements.length];
    }
    //Pred: index >= 0 && index < size && element != null && queue != null
    //Post: elements'[rear - index] == element &&
    // for i = 0...index - 1, index + 1... n - 1 : a'[i] = a[i]
    public static <T> void set(ArrayQueueADT<T> queue, int index, T element) {
        assert index >= 0 && index < queue.size;
        Objects.requireNonNull(element);
        queue.elements[(queue.rear - 1 - index + queue.elements.length) % queue.elements.length] = element;
    }
    //Pred: true
    //Post: immutable(size) && R == (size == 0) && size' == size
    public static <T> boolean isEmpty(ArrayQueueADT<T> queue) {
        return queue.size == 0;
    }
    //Pred: true
    //Post: immutable(n) && R == size && size' == size
    public static <T> int size(ArrayQueueADT<T> queue) {
        return queue.size;
    }
    //Pred: true
    //Post: size' == 0
    @SuppressWarnings("unchecked")
    public static <T> void clear(ArrayQueueADT<T> queue) {
        queue.elements = (T[]) new Object[10];
        queue.front = queue.rear = queue.size = 0;
    }

}