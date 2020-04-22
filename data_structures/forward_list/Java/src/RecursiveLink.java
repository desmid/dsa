class RecursiveLink<T> extends AbstractLink<T> {

    T value;
    RecursiveLink<T> next;

    public RecursiveLink(T value) {
        this.value = value;
        this.next = null;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public RecursiveLink<T> getNext() {
        return next;
    }

    @Override
    public int size() {
        if (next == null) {
            return 1;
        }
        return 1 + next.size();
    }

    @Override
    public void append(T newValue) {
        if (next == null) {
            next = new RecursiveLink<T>(newValue);
            return;
        }
        next.append(newValue);
    }

    @Override
    public void append(AbstractLink<T> tail) {
        if (next == null) {
            next = (RecursiveLink<T>) tail;
            return;
        }
        next.append(tail);
    }

    @Override
    public RecursiveLink<T> delete(T oldValue) {
        if (value == oldValue) {
            return next;
        }
        if (next != null) {
            next = next.delete(oldValue);
        }
        return this;
    }

    @Override
    public T last() {
        if (next == null) {
            return value;
        }
        return next.last();
    }

    @Override
    public RecursiveLink<T> reverse() {
        if (next == null) {
            return this;   // the new head
        }
        RecursiveLink<T> head = next.reverse();
        next.next = this;  // reverse link
        this.next = null;  // propagate null backwards
        return head;
    }

    @Override
    public RecursiveLink<T> reversed() {
        RecursiveLink<T> copy = new RecursiveLink<>(value);
        if (next == null) {
            return copy;   // the new head
        }
        RecursiveLink<T> head = next.reverse();
        next.next = copy;  // reverse link
        return head;
    }

}
