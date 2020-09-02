public class ArrayDequeString extends AbstractArrayDeque<String> {

    public ArrayDequeString() {
        super(DEFAULT_CAPACITY);
    }

    public ArrayDequeString(int capacity) {
        super(capacity);
    }

    public ArrayDequeString(ArrayDequeString other) {
        super(other);
    }

    @Override
    protected String[] allocator(int newCapacity) {
        return new String[newCapacity];
    }

    public static ArrayDequeString of(String... args) {
        ArrayDequeString queue = new ArrayDequeString();
        for (String item : args) {
            queue.addLast(item);
        }
        return queue;
    }

}
