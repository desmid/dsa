## Deque

Two implementations demonstrate a deque using a doubly linked list or a
dynamically resizing array:

- `LinkedListDeque<T>`
- `AbstractArrayDeque<T>`

`LinkedListDeque<T>` makes use of a doubly linked list of link and a sentinel
link; the queue is terminated at each end by the sentinel, maintaining a
circular data structure.

`AbstractArrayDeque<T>` uses a ring buffer and a pair of indexes giving the
next available insert positions at the head and tail of the queue. The array
is dynamically reallocated on the next enqueue when full, and shrinks again on
dequeues when it reaches about 25% usage capacity. The initial default
capacity (8) can be overriden by a constructor parameter (>= 2).

### Class and interface hierarchies

An interface `Deque<T>` declares the core API.

An abstract base class `AbstractDeque<T>` implements `Deque<T>` as well as
common `iterator()`, `equals()` and `toString()` methods.

The types `LinkedListDeque<T>` and `AbstractArrayDeque<T>` (above) then extend
`AbstractDeque<T>`.

For convenience, both also provide a static factory method `of()`, which uses
varargs to create and populate a linked list with an arbitrary number of
elements.

### Generics and type specializations for dynamic arrays

Because `AbstractArrayDeque<T>` manipulates a dynamically created fixed length
array, concrete instances can't use generics. Type specializations are achieved
by explicitly subclassing for each type. Each subtype must implement
constructors delegating to `super`, the `of()` static factory, and an
`allocator()` that performs the memory allocation (i.e., calls the `new`
operator to return an array of the specific type).


### Deque API

| Method                                 | Description |
| ---                                    | --- |
| `public boolean empty()`               | Return true if the deque is empty, false otherwise. |
| `public int size()`                    | Return the size of the deque. |
| `public void addFirst(T)`              | Enqueue a new item at the front of the queue. |
| `public void addLast(T)`               | Enqueue a new item at the back of the queue. |
| `public T removeFirst()`               | Dequeue the item at the front of the queue and return it's value; returns null if deque is empty. |
| `public T removeLast()`                | Dequeue the item at the back of the queue and return it's value; returns null if deque is empty. |
| `public T get(int index)`              | Return the value stored in the i'th item; throws an exception if out of bounds. |
| `public void put(int index, newValue)` | Put a new value in the i'th item; throws an exception if out of bounds. |


### ArrayDeque API additions

| Method                                 | Description |
| ---                                    | --- |
| `public int capacity()`                | Return capacity. |
| `public float usageFactor()`           | Return size/capacity. |


### File listing

```
Java
├── pom.xml
├── src
│   ├── Deque.java                # interface
│   │
│   ├── AbstractDeque.java        # abstract base class for deque
│   │
│   ├── AbstractArrayDeque.java   # abstract generic array implementation
│   ├── ArrayDequeInteger.java    # Integer array specialization
│   ├── ArrayDequeString.java     # String array specialization
│   │
│   └── LinkedListDeque.java      # concrete generic linked list implementation
│
└── test
    ├── ArrayDequeIntegerTest.java
    ├── ArrayDequeStringTest.java
    └── LinkedListDequeTest.java
```
