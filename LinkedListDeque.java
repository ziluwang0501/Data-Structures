package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private Node sentinel;
    private int size;

    private class Node {
        private T item;
        private Node next;
        private Node last;

        private Node(T item) {
            this.item = item;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.last = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item);
        Node firstNode = sentinel.next;
        newNode.next = firstNode;
        firstNode.last = newNode;
        sentinel.next = newNode;
        newNode.last = sentinel;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item);
        Node lastNode = sentinel.last;
        newNode.next = sentinel;
        lastNode.next = newNode;
        newNode.last = lastNode;
        sentinel.last = newNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int count = size();
        Node node = sentinel.next;
        while (count > 0) {
            System.out.print(node.item + " ");
            node = node.next;
            count--;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        firstNode.next.last = sentinel;
        size--;
        return firstNode.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node lastNode = sentinel.last;
        lastNode.last.next = sentinel;
        sentinel.last = lastNode.last;
        size--;
        return lastNode.item;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index < 0 || index >= size) {
            return null;
        }
        Node node = sentinel.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node p, int index) {
        if (index == 0) {
            return p.item;
        } else {
            return getRecursiveHelper(p.next, index - 1);
        }
    }

    public Iterator<T> iterator() {
        return new TheIterator();
    }

    private class TheIterator implements Iterator<T> {
        private int position;
        private TheIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return position < size();
        }

        public T next() {
            T nextValue = get(position);
            position++;
            return nextValue;
        }
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!other.get(i).equals(this.get(i))) {
                return false;
            }
        }
        return true;
    }
}

