public interface Deque<T> {

    void addFirst(T value);

    void addLast(T value);

    default boolean empty() {
        return size() == 0;
    };

    T get(int index);

    void put(int index, T value);

    T removeFirst();

    T removeLast();

    int size();
}
