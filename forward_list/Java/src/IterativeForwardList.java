import java.util.NoSuchElementException;

public class IterativeForwardList<T> extends AbstractForwardList<T> {

    private IterativeLink<T> head = new IterativeLink<>(null); // sentinel

    @SafeVarargs
    public static <T> IterativeForwardList<T> of(T... args) {
        IterativeForwardList<T> list = new IterativeForwardList<>();
        for (T value : args) {
            list.append(value);
        }
        return list;
    }

    @Override
    protected IterativeLink<T> getHead() {
        return head.next;
    }

    @Override
    public boolean empty() {
        return head.next == null;
    }

    @Override
    public int size() {
        if (empty()) {
            return 0;
        }
        return head.next.size();
    }

    @Override
    public void append(T newValue) {
        if (newValue == null) {  // don't allow null
            return;
        }
        if (empty()) {
            head.next = new IterativeLink<T>(newValue);
            return;
        }
        head.next.append(newValue);
    }

    @Override
    public void append(AbstractForwardList<T> list2) {
        IterativeForwardList<T> list3 = (IterativeForwardList<T>) list2;
        if (empty()) {
            head.next = list3.head.next;
            return;
        }
        head.next.append(list3.head.next);
    }

    @Override
    public void delete(T oldValue) {
        if (empty()) {
            return;
        }
        head.next = head.next.delete(oldValue);
    }

    @Override
    public T first() {
        if (empty()) {
            throw new NoSuchElementException("list is empty");
        }
        return head.next.getValue();
    }

    @Override
    public T last() {
        if (empty()) {
            throw new NoSuchElementException("list is empty");
        }
        return head.next.last();
    }

    @Override
    public void reverse() {
        if (empty()) {
            return;
        }
        head.next = head.next.reverse();
    }

    @Override
    public IterativeForwardList<T> reversed() {
        IterativeForwardList<T> result = new IterativeForwardList<>();
        if (empty()) {
            return result;
        }
        result.head.next = head.next.reversed();
        return result;
    }

}
