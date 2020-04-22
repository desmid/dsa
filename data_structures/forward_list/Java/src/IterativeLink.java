class IterativeLink<T> extends AbstractLink<T> {

    T value;
    IterativeLink<T> next;

    public IterativeLink(T value) {
        this.value = value;
        this.next = null;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public IterativeLink<T> getNext() {
        return next;
    }

    @Override
    public int size() {
        int size = 1;
        IterativeLink<T> link = next;
        while (link != null) {
            link = link.next;
            size++;
        }
        return size;
    }

    @Override
    public void append(T newValue) {
        IterativeLink<T> newLink = new IterativeLink<>(newValue);
        IterativeLink<T> link = this;
        while (link.next != null) {
            link = link.next;
        }
        link.next = newLink;
    }

    @Override
    public void append(AbstractLink<T> tail) {
        IterativeLink<T> link = this;
        while (link.next != null) {
            link = link.next;
        }
        link.next = (IterativeLink<T>) tail;
    }

    @Override
    public IterativeLink<T> delete(T oldValue) {
        IterativeLink<T> pred = null;
        IterativeLink<T> link = this;
        while (link != null) {
            if (link.value == oldValue) {
                if (pred == null) {
                    return link.next;   // excise first item
                }
                pred.next = link.next;  // excise inner or last item
                break;
            }
            pred = link;
            link = link.next;
        }
        return this;
    }

    /*
     * // more elegant: the original predecessor or list head is passed in,
     * // but not consistent with the other parts of the design
     * public void delete(IterativeLink<T> pred, T oldValue) {
     *     IterativeLink<T> link = this;
     *     while (link != null) {
     *         if (link.value == oldValue) {
     *             pred.next = link.next;
     *             break;
     *         }
     *         pred = link;
     *         link = link.next;
     *     }
     * }
     */

    @Override
    public T last() {
        IterativeLink<T> link = this;
        while (link.next != null) {
            link = link.next;
        }
        return link.value;
    }

    @Override
    public IterativeLink<T> reverse() {
        IterativeLink<T> prev, current, next;
        prev = null;
        current = this;
        while (current != null) {
            next = current.next;
            current.next = prev;  // reverse link
            prev = current;
            current = next;
        }
        return prev;
    }

    @Override
    public IterativeLink<T> reversed() {
        IterativeLink<T> prev, current, next;
        prev = null;
        current = this;
        while (current != null) {
            // reverse link in copy
            IterativeLink<T> newLink = new IterativeLink<>(current.value);
            newLink.next = prev;  // reverse link
            next = current.next;
            prev = newLink;
            current = next;
        }
        return prev;
    }

}
