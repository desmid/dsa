public class ArrayDequeInteger extends AbstractArrayDeque<Integer> {

    public ArrayDequeInteger() {
        super(DEFAULT_CAPACITY);
    }

    public ArrayDequeInteger(int capacity) {
        super(capacity);
    }

    public ArrayDequeInteger(ArrayDequeInteger other) {
        super(other);
    }

    @Override
    protected Integer[] allocator(int newCapacity) {
        return new Integer[newCapacity];
    }

    public static ArrayDequeInteger of(Integer... args) {
        ArrayDequeInteger queue = new ArrayDequeInteger();
        for (Integer item : args) {
            queue.addLast(item);
        }
        return queue;
    }

}
