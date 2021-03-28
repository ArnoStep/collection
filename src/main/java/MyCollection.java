import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @version 1.0
 * @param <E>
 * @author Arno Stepanyan
 */

public class MyCollection<E> implements Collection<E> {

    private int size;

    private Object[] elementData = new Object[10];

    @Override
    public final boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public final boolean contains(final Object o) {
        if (o == null) {
            for (int i = 0; i < size(); i++) {
                if (elementData[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size(); i++) {
                if (elementData[i] != null && elementData[i].equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final Object[] toArray() {
        if (size() != 0) {
            return Arrays.copyOf(elementData, size());
        } else {
            return new Object[0];
        }
    }

    @Override
    public final <T> T[] toArray(final T[] a) {
        Iterator<?> it = iterator();
        if (a.length <= this.size()) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        } else {
            for (int i = 0; i < a.length; i++) {
                if (it.hasNext()) {
                    a[i] = (T) it.next();
                }
            }
        }
        return a;
    }

    @Override
    public final boolean remove(final Object o) {
        Iterator<E> it = iterator();
        if (o == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        boolean hasElement;
        for (Object o : c) {
            hasElement = false;
            if (o == null) {
                for (int i = 0; i < size(); i++) {
                    if (elementData[i] == null) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < size(); i++) {
                    if (elementData[i] != null && elementData[i].equals(o)) {
                        hasElement = true;
                        break;
                    }
                }
            }
            if (!hasElement) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        for (E e : c) {
            if (!add(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        boolean isRemoved = false;
        Iterator<?> it = iterator();
        for (Object o : c) {
            while (it.hasNext()) {
                if (o == null) {
                    if (it.next() == null) {
                        it.remove();
                        isRemoved = true;
                    }
                } else {
                    if (o.equals(it.next())) {
                        it.remove();
                        isRemoved = true;
                    }
                }
            }
        }
        return isRemoved;
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        Iterator<?> it = this.iterator();
        boolean isChanged = false;
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public final void clear() {
        for (int i = 0; i < size(); i++) {
            elementData[i] = null;
            size = 0;
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private int last = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            last = cursor++;
            return (T) elementData[last];
        }

        @Override
        public void remove() {
            if (last == -1) {
                throw new IllegalStateException();
            }
            if (last < size - 1) {
                System.arraycopy(elementData, last + 1, elementData, last, size - 1 - last);
            }
            size--;
            last = -1;
            cursor--;
        }
    }
}