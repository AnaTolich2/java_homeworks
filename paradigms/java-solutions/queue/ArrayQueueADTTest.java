package queue;

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT<String> queueADT = new ArrayQueueADT<>();
        for (int i = 0; i < 12; i++) {
            ArrayQueueADT.enqueue(queueADT, "e_" + i);
        }
        while (!ArrayQueueADT.isEmpty(queueADT)){
            System.out.println(ArrayQueueModule.dequeue());
        }
    }
}
