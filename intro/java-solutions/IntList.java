public class IntList {
    int[] array;
    private int length;
    private int realLength = 0;
    private int pointer = -1;
    public IntList(){
        array = new int[0];
        length = 0;
    }
    public IntList(int length){
        array = new int[length];
        this.length=length;
        realLength = length;
        pointer = length-1;
    }
    private void addLength() {
        if (length == 0){
            this.array = new int[1];
            this.length++;
        }
        else if (realLength >= length) {
            int[] b = new int[(length * 4)/3 + 2];
            System.arraycopy(array, 0, b, 0, length);
            this.array = b;
            this.length = (length * 4)/3 + 2;
        }
    }
    public void assign(int index,int x){
        array[index] = x;
    }
    public void addToIndex(int index,int x){
        array[index] += x;
    }
    public void append(int x){
        pointer++;
        addLength();
        this.array[pointer] = x;
        realLength++;
    }
    public int get(int i){
        return array[i];
    }
    public int size(){
        return realLength;
    }
}
