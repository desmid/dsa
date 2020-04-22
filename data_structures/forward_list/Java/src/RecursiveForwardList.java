import java.util.NoSuchElementException;

public class RecursiveForwardList<T> extends AbstractForwardList<T> {

    private RecursiveLink<T> head;

    @SafeVarargs
    public static <T> RecursiveForwardList<T> of(T... args) {
        RecursiveForwardList<T> list = new RecursiveForwardList<>();
        for (T value : args) {
            list.append(value);
        }
        return list;
    }

    @Override
    protected RecursiveLink<T> getHead() {
        return head;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public int size() {
        if (empty()) {
            return 0;
        }
        return head.size();
    }

    @Override
    public void append(T newValue) {
        if (newValue == null) {  // don't allow null
            return;
        }
        if (empty()) {
            head = new RecursiveLink<T>(newValue);
            return;
        }
        head.append(newValue);
    }

    @Override
    public void append(AbstractForwardList<T> list2) {
        RecursiveForwardList<T> list3 = (RecursiveForwardList<T>) list2;
        if (empty()) {
            head = list3.head;
            return;
        }
        head.append(list3.head);
    }

    @Override
    public void delete(T oldValue) {
        if (empty()) {
            return;
        }
        head = head.delete(oldValue);
    }

    @Override
    public T first() {
        if (empty()) {
            throw new NoSuchElementException("list is empty");
        }
        return head.getValue();
    }

    @Override
    public T last() {
        if (empty()) {
            throw new NoSuchElementException("list is empty");
        }
        return head.last();
    }

    @Override
    public void reverse() {
        if (empty()) {
            return;
        }
        head = head.reverse();
    }

    @Override
    public RecursiveForwardList<T> reversed() {
        RecursiveForwardList<T> result = new RecursiveForwardList<>();
        if (empty()) {
            return result;
        }
        result.head = head.reversed();
        return result;
    }

}
