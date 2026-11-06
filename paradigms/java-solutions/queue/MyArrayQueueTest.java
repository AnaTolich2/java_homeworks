package queue;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>();
        for (int i = 0; i < 12; i++) {
            queue.enqueue("e_" + i);
        }
        while (!queue.isEmpty()){
            System.out.println(queue.dequeue());
        }
    }
}
