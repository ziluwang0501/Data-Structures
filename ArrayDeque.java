package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    @Override
    public void addFirst(T item) {
        if (items.length == size) {
            resizeHelper(items.length * 2);
        }
        items[nextFirst] = item;
        size++;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
    }

    @Override
    public void addLast(T item) {
        if (items.length == size) {
            resizeHelper(items.length * 2);
        }
        items[nextLast] = item;
        size++;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }
    }

    private void resizeHelper(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            a[i] = get(i);
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
            System.out.println();
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size <= items.length / 4) {
            resizeHelper(items.length / 2);
        }
        if (nextFirst == items.length - 1) {
            size--;
            nextFirst = 0;
            return items[nextFirst];
        } else {
            size--;
            nextFirst++;
            return items[nextFirst];
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size <= items.length / 4) {
            resizeHelper(items.length / 2);
        }
        if (nextLast == 0) {
            size--;
            nextLast = items.length - 1;
            return items[items.length - 1];
        } else {
            size--;
            nextLast--;
            return items[nextLast];
        }
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index < 0 || index >= size) {
            return null;
        } else {
            int realIndex = (nextFirst + 1 + index) % items.length;
            return items[realIndex];
        }
    }

    public Iterator<T> iterator() {
        return new AnotherIterator();
    }

    private class AnotherIterator implements Iterator<T> {
        private int iteratorPos;
        private AnotherIterator() {
            iteratorPos = 0;
        }

        public boolean hasNext() {
            return iteratorPos < size();
        }

        public T next() {
            int actualIndex = (nextFirst + 1 + iteratorPos) % items.length;
            T theValue = items[actualIndex];
            iteratorPos++;
            return theValue;
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
