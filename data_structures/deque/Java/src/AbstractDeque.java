public abstract class AbstractDeque<T> implements Deque<T>, Iterable<T> {

    // public API

    public abstract void addFirst(T value);
    public abstract void addLast(T value);

    public abstract T get(int index);

    public abstract void put(int index, T value);

    public abstract T removeFirst();
    public abstract T removeLast();

    public abstract int size();

    // common methods

    public void printDeque() {
        System.out.println("Deque: " + this);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("(");
        int count = 0;
        for (T value : this) {
            if (count > 0) {
                s.append(", ");
            }
            s.append(value);
            count++;
        }
        return s.append(")").toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof AbstractDeque)) {
            return false;
        }
        AbstractDeque<T> other = (AbstractDeque<T>) o;
        int size = size();
        if (size != other.size()) {
            return false;
        }
        if (size == 0) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (! get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

}
