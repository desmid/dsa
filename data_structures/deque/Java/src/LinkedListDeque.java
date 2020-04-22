import java.util.Iterator;

public class LinkedListDeque<T> extends AbstractDeque<T> {

    static class Link<T> {
        private T value;
        private Link<T> last, next;

        Link(T value) {
            this(value, null, null);
        }

        Link(T value, Link<T> last, Link<T> next) {
            this.value = value;
            this.last = last;
            this.next = next;
        }

        Link<T> getNext() {
            return next;
        }

        T getValue() {
            return value;
        }
    }


    // sentinel references itself for circular structure
    private Link<T> sentinel;
    private int size = 0;


    // constructors

    public LinkedListDeque() {
        sentinel = new Link<>(null);
        sentinel.next = sentinel.last = sentinel;
    }

    @SuppressWarnings("unchecked")
    public LinkedListDeque(LinkedListDeque other) {
        this();
        for (Link link = other.sentinel.next; link != other.sentinel; link = link.next) {
            addLast((T) link.value);
        }
    }


    // public API methods

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T value) {
        Link<T> newLink = new Link<>(value, sentinel, sentinel.next);
        sentinel.next = newLink;
        newLink.next.last = newLink;
        size++;
    }

    @Override
    public void addLast(T value) {
        Link<T> newLink = new Link<>(value, sentinel.last, sentinel);
        sentinel.last = newLink;
        newLink.last.next = newLink;
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Link<T> removeLink = sentinel.next;
        sentinel.next = removeLink.next;
        removeLink.next.last = sentinel;
        size--;
        return removeLink.value;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Link<T> removeLink = sentinel.last;
        sentinel.last = removeLink.last;
        removeLink.last.next = sentinel;
        size--;
        return removeLink.value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        Link<T> link = sentinel.next;
        for (int i = 0; link != sentinel; ++i, link = link.next) {
            if (i == index) {
                break;
            }
        }
        return link.value;
    }

    @Override
    public void put(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        Link<T> link = sentinel.next;
        for (int i = 0; link != sentinel; ++i, link = link.next) {
            if (i == index) {
                break;
            }
        }
        link.value = value;
    }


    // additional methods

    @SafeVarargs
    public static <T> LinkedListDeque<T> of(T... args) {
        LinkedListDeque<T> queue = new LinkedListDeque<>();
        for (T item : args) {
            queue.addLast(item);
        }
        return queue;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        return getRecursiveBody(sentinel.next, index);
    }

    private T getRecursiveBody(Link<T> link, int i) {
        if (i == 0) {
            return link.value;
        }
        return getRecursiveBody(link.next, --i);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(sentinel);
    }

}

class ListIterator<T> implements Iterator<T> {

    private LinkedListDeque.Link<T> sentinel = null;
    private LinkedListDeque.Link<T> current = null;

    ListIterator(LinkedListDeque.Link<T> sentinel) {
        this.sentinel = sentinel;
        this.current = sentinel.getNext();
    }

    public boolean hasNext() {
        return current != sentinel;
    }

    public T next() {
        T value = current.getValue();
        current = current.getNext();
        return value;
    }
}
