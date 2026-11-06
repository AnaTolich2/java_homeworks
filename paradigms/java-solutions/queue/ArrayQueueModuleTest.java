package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            ArrayQueueModule.enqueue("e_" + i);
        }
        while (!ArrayQueueModule.isEmpty()){
            System.out.println(ArrayQueueModule.dequeue());
        }
    }
}
