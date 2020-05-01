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


    // public API

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T value) {
        addLinkBetween(sentinel, sentinel.next, value);
    }

    @Override
    public void addLast(T value) {
        addLinkBetween(sentinel.last, sentinel, value);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        return removeLinkAt(sentinel.next);
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        return removeLinkAt(sentinel.last);
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


    // additional API

    @SafeVarargs
    public static <T> LinkedListDeque<T> of(T... args) {
        LinkedListDeque<T> queue = new LinkedListDeque<>();
        for (T item : args) {
            queue.addLast(item);
        }
        return queue;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(sentinel);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("called with index %d on size %d", index, size));
        }
        return getRecursiveBody(sentinel.next, index);
    }


    // private methods

    private void addLinkBetween(Link<T> pred, Link<T> succ, T value) {
        Link<T> newLink = new Link<>(value, pred, succ);
        pred.next = succ.last = newLink;
        size++;
    }

    private T removeLinkAt(Link<T> oldLink) {
        oldLink.last.next = oldLink.next;
        oldLink.next.last = oldLink.last;
        size--;
        return oldLink.value;
    }

    private T getRecursiveBody(Link<T> link, int i) {
        if (i == 0) {
            return link.value;
        }
        return getRecursiveBody(link.next, --i);
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
