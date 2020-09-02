public abstract class AbstractLink<T> {
    public abstract void append(AbstractLink<T> tail);
    public abstract void append(T newValue);
    public abstract AbstractLink<T> delete(T deleteValue);
    public abstract AbstractLink<T> getNext();
    public abstract T getValue();
    public abstract T last();
    public abstract AbstractLink<T> reverse();
    public abstract AbstractLink<T> reversed();
    public abstract int size();

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof AbstractLink)) {
            return false;
        }
        AbstractLink<T> other = (AbstractLink<T>) o;
        if (getValue().equals(other.getValue())) {
            return true;
        }
        return false;
    }

}
