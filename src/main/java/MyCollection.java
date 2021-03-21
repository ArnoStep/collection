import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyCollection<E> implements Collection<E> {

    private Object[] elementData;

    public MyCollection(final Object[] elementData) {
        this.elementData = elementData;
    }

    @Override
    public boolean add(final E e) {
        elementData = Arrays.copyOf(elementData, elementData.length + 1);
        elementData[elementData.length - 1] = e;
        return true;
    }

    @Override
    public int size() {
        return 0; //не использую
    }

    @Override
    public boolean isEmpty() {
        return this.elementData.length == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(final Object o) {
        for (Object e : this.elementData) {
            if (Objects.equals(o, e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Iterator<E> it = iterator();
        for (int i = 0; i < elementData.length; i++) {
            if (!it.hasNext()) {
                return Arrays.copyOf(elementData, i);
            }
            elementData[i] = it.next();
        }
        return it.hasNext() ? finishToArray(elementData, it) : elementData;
    }

    private static <T> T[] finishToArray(T[] r, final Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                int newCap = cap + (cap >> 1) + 1;
                if (newCap - Integer.MAX_VALUE - 8 > 0) {
                    newCap = hugeCapacity(cap + 1);
                }
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T) it.next();
        }
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }

    private static int hugeCapacity(final int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError(
                    "Требуемый размер массива слишком велик");
        }
        return (minCapacity > Integer.MAX_VALUE - 8)
                ? Integer.MAX_VALUE
                : Integer.MAX_VALUE - 8;
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        T[] r = a.length >= elementData.length ? a
                : (T[]) java.lang.reflect.Array
                .newInstance(a.getClass().getComponentType(), elementData.length);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) {
            if (!it.hasNext()) {
                if (a == r) {
                    r[i] = null;
                } else if (a.length < i) {
                    return Arrays.copyOf(r, i);
                } else {
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T) it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    @Override
    public boolean remove(final Object o) {
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
    public boolean containsAll(final Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (!add(e)) {
                continue;
            }
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean flag = false;
        for (Object o : c) {
            if (remove(o)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        boolean flag = false;
        int size = elementData.length;
        for (Object o : c) {
            if (retain(o)) {
                flag = true;
            }
        }
        elementData = Arrays.copyOfRange(elementData, size, elementData.length);
        return flag;
    }

    public boolean retain(final Object o) {
        boolean flag = false;
        if (isEmpty()) {
            return false;
        }
        for (Object elementDatum : elementData) {
            if (elementDatum.equals(o)) {
                if (add((E) elementDatum)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void clear() {
        for (Object o : elementData) {
            remove(o);
        }
    }

    public Object[] getElementData() {
        return this.elementData;
    }

    private class MyIterator<T> implements Iterator<T> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < elementData.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= elementData.length) {
                throw new NoSuchElementException();
            }
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (isEmpty()) {
                throw new NullPointerException("Коллекция пуста!");
            }

            if (cursor < 0 || cursor > elementData.length) {
                throw new IndexOutOfBoundsException("Вне границ массива!");
            }

            Object[] anotherElementData = new Object[elementData.length - 1];


            for (int i = 0, k = 0; i < elementData.length; i++) {

                if (i == cursor - 1) {
                    continue;
                }

                anotherElementData[k++] = elementData[i];

            }

            elementData = Arrays.copyOf(anotherElementData, anotherElementData.length);

        }
    }
}
