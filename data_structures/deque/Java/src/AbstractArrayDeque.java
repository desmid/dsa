import java.util.Iterator;

public abstract class AbstractArrayDeque<T> extends AbstractDeque<T> {

    public boolean DEBUG = false;

    public static final int   DEFAULT_CAPACITY = 8;
    public static final int   MINIMUM_RUNNING_CAPACITY = 16;
    public static final float MINIMUM_USAGE_FACTOR = 0.25f;

    protected static final float EXPAND_FACTOR = 1.5f;
    protected static final float SHRINK_FACTOR = 0.25f;

    // circular array
    protected T[] items;

    protected int capacity;
    protected int size;

    // next available indices; we start at the middle
    protected int first;
    protected int last;


    // constructors

    public AbstractArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public AbstractArrayDeque(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            System.err.printf("Warning: capacity %d too small, using %d\n",
                              capacity, DEFAULT_CAPACITY);
            capacity = DEFAULT_CAPACITY;
        }
        reallocate(capacity);
    }

    public AbstractArrayDeque(AbstractArrayDeque<T> other) {
        reallocate(other.capacity);
        for (int i = 0; i < other.size; ++i) {
            addLast(other.get(i));
        }
    }


    // public API

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T value) {
        if (size == capacity) {
            increaseCapacity();
        }
        items[first] = value;
        decrementFirst();
    }

    @Override
    public void addLast(T value) {
        if (size == capacity) {
            increaseCapacity();
        }
        items[last] = value;
        incrementLast();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        incrementFirst();
        if (usageFactor() < MINIMUM_USAGE_FACTOR) {
            shrinkCapacity();
        }
        return items[first];
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        decrementLast();
        if (usageFactor() < MINIMUM_USAGE_FACTOR) {
            shrinkCapacity();
        }
        return items[last];
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        index = mapIndex(index);
        return items[index];
    }

    @Override
    public void put(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        index = mapIndex(index);
        items[index] = value;
    }


    // additional API

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(this);
    }

    public float usageFactor() {
        return 1.0f * size / capacity;
    }

    public int capacity() {
        return capacity;
    }

    public void printState(String msg) {
        System.out.println(msg); printState();
    }

    public void printState() {
        System.out.printf("size/capacity=%d/%d first=%d last=%d usage=%4.2f\n",
                          size, capacity, first, last, usageFactor());
    }


    // protected methods

    protected abstract T[] allocator(int newCapacity);


    // private methods

    private int mapIndex(int index) {
        return (first + index + 1) % capacity;
    }

    private void incrementFirst() {
        first = (first + 1) % capacity;
        size--;
    }

    private void decrementFirst() {
        if (first == 0) {
            first = capacity;
        }
        first--;
        size++;
    }

    private void incrementLast() {
        last = (last + 1) % capacity;
        size++;
    }

    private void decrementLast() {
        if (last == 0) {
            last = capacity;
        }
        last--;
        size--;
    }

    private void increaseCapacity() {
        int newCapacity = (int) (EXPAND_FACTOR * capacity);
        reallocate(newCapacity);
    }

    private void shrinkCapacity() {
        int newCapacity = (int) (SHRINK_FACTOR * capacity);
        if (newCapacity > MINIMUM_RUNNING_CAPACITY && newCapacity > size) {
            reallocate(newCapacity);
        }
    }

    private void reallocate(int newCapacity) {
        if (newCapacity < 2) {
            throw new IllegalArgumentException("capacity must be at least 2");
        }

        if (size > 0 && DEBUG) {
            String msg = (capacity > newCapacity ? "SHRINK! " : "EXPAND! ");
            printState(msg + capacity + " to " + newCapacity);
        }

        items = copyItems(allocator(newCapacity), size);
        capacity = newCapacity;
        first = capacity - 1;
        last = size % capacity;

        if (size > 0 && DEBUG) {
            printState();
        }
    }

    private T[] copyItems(T[] dest, int size) {
        for (int i = 0; i < size; ++i) {
            int index = mapIndex(i);
            dest[i] = items[index];

            if (DEBUG) {
                System.out.println("copy " + i + " " + index + " " + items[index]);
            }
        }
        return dest;
    }

}

class ArrayIterator<T> implements Iterator<T> {

    AbstractArrayDeque<T> deque;
    int size;
    int current;

    ArrayIterator(AbstractArrayDeque<T> deque) {
        this.deque = deque;
        this.size = deque.size();
        this.current = 0;
    }

    public boolean hasNext() {
        return current != size;
    }

    public T next() {
        return deque.get(current++);
    }
}
