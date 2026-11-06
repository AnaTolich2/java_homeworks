package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue<T> extends AbstractQueue<T> {
    //NOTE: prev is unneeded
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head, tail;

    @Override
    protected void enqueueImpl(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    protected LinkedQueue<T> filterImpl(Predicate<T> predicate) {
        LinkedQueue<T> result = new LinkedQueue<>();
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (predicate.test(currentNode.value)) {
                result.enqueue(currentNode.value);
            }
            currentNode = currentNode.next;
        }
        return result;
    }

    @Override
    protected LinkedQueue<T> mapImpl(Function<T, T> function) {
        LinkedQueue<T> result = new LinkedQueue<>();
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            result.enqueue(function.apply(currentNode.value));
            currentNode = currentNode.next;
        }
        return result;
    }

    @Override
    protected T dequeueImpl() {
        T result = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return result;
    }

    //Pred: size > 0
    //Post: size' == size && immutable(size) && R = elements[size - 1]
    @Override
    public T element() {
        assert !isEmpty();
        return head.value;
    }

    //Pred: true
    //Post: size'== 0
    @Override
    public void clear() {
        size = 0;
    }
}