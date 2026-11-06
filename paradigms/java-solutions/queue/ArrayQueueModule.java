package queue;

import java.util.Objects;
//Invariant: for each i, a[i] != null
//immutable(n): for i = 0...n - 1 : a'[i] == a[i]
//Model: a[0]...a[size-1]
public class ArrayQueueModule {
    private static Object[] elements = new Object[10];
    private static int front = 0, rear = 0, size = 0;
    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, front, newElements, 0, size - front);
            System.arraycopy(elements, 0, newElements, size - front, front);
            front = 0;
            rear = size;
            elements = newElements;
        }
    }

    //Pred: element != null
    //Post: size' == size + 1 && a[size] == element && immutable(size)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

    //Pred: size > 0
    //Post: size' == size - 1 && immutable(size') && R = a[size'] && a'[size'] == null
    public static Object dequeue() {
        assert size > 0;
        Object result = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return result;
    }

    //Pred: size > 0
    //Post: size' == size && immutable(size) && R = a[size - 1]
    public static Object element() {
        assert size > 0;
        return elements[front];
    }

    //Pred: true
    //Post: immutable(size) && R == (size == 0) && size' == size
    public static boolean isEmpty() {
        return size == 0;
    }

    //Pred: true
    //Post: immutable(size) && R == size & size' == size
    public static int size() {
        return size;
    }

    //Pred: true
    //Post: size' == 0
    public static void clear() {
        elements = new Object[10];
        front = rear = size = 0;
    }



    //Pred: element != null
    //Post: immutable(size) - (starting from 1 up to n - 1) && size' = size + 1 && elements'[front] == element
    public static void push(Object element) {
        ensureCapacity(size + 1);
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    //Pred: size > 0
    //Post: size' = size - 1 && R == elements[rear] && a'[rear] == null
    public static Object remove() {
        assert size > 0;
        rear = (rear - 1 + elements.length) % elements.length;
        Object result = elements[rear];
        elements[rear] = null;
        size--;
        return result;
    }

    //Pred: size > 0
    //Post: immutable(size) && R == a[size - 1] && size' == size
    public static Object peek() {
        assert size > 0;
        return elements[(rear - 1 + elements.length) % elements.length];
    }

    //Pred: index >= 0 && index < size
    //Post: R == elements'[rear - index] && immutable(size) && size' == size
    public static Object get(int index) {
        assert index >= 0 && index < size;
        return elements[(rear - 1 - index + elements.length) % elements.length];
    }

    //Pred: index >= 0 && index < size && element != null
    //Post: elements'[rear - index] == element && size' == size
    // for i = 0...index - 1, index + 1... size - 1 : a'[i] = a[i]
    public static void set(int index, Object element) {
        assert index >= 0 && index < size;
        Objects.requireNonNull(element);
        elements[(rear - 1 - index + elements.length) % elements.length] = element;
    }
}