import java.util.Iterator;

public abstract class AbstractForwardList<T> implements Iterable<T> {

    abstract protected AbstractLink<T> getHead();

    abstract public void append(AbstractForwardList<T> list2);
    abstract public void append(T newValue);
    abstract public void delete(T oldValue);
    abstract public boolean empty();
    abstract public T first();
    abstract public T last();
    abstract public void reverse();
    abstract public AbstractForwardList<T> reversed();
    abstract public int size();

    @Override
    public String toString() {
        if (empty()) {
            return "()";
        }
        StringBuilder s = new StringBuilder("(");
        int count = 0;
        for (T value : this) {
            if (count > 0) {
                s.append(", ");
            }
            s.append(value);
            count++;
        }
        s.append(")");
        return s.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof AbstractForwardList)) {
            return false;
        }
        AbstractForwardList<T> other = (AbstractForwardList<T>) o;
        int size = size();
        if (size != other.size()) {
            return false;
        }
        if (size == 0) {
            return true;
        }
        AbstractLink<T> lhs = this.getHead();
        AbstractLink<T> rhs = other.getHead();
        if (! lhs.getValue().equals(rhs.getValue())) {
            return false;
        }
        for (int i = 1; i < size; i++) {
            lhs = lhs.getNext();
            rhs = rhs.getNext();
            if (! lhs.equals(rhs)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkIterator<>(getHead());
    }

}

class LinkIterator<T> implements Iterator<T> {

    private AbstractLink<T> current = null;

    LinkIterator(AbstractLink<T> head) {
        this.current = head;
    }

    public boolean hasNext() {
        return current != null;
    }

    public T next() {
        T value = current.getValue();
        current = current.getNext();
        return value;
    }

}
