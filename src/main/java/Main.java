public class Main {
    public static void main(final String[] args) {
        MyCollection myCollection = new MyCollection(new Object[]{1, 2, 3});
        MyCollection myTestCollection = new MyCollection(new Object[]{4, 5, 6});

        myCollection.add(100);

        for (Object o : myCollection.getElementData()) {
            System.out.print(o + " ");
        }

        System.out.println("IsEmpty: " + myCollection.isEmpty());
        System.out.println("Size: " + myCollection.size());
        System.out.println("Contains: " + myCollection.contains(100));
        System.out.println("AddAll: " + myCollection.addAll(myTestCollection));
        System.out.println("ContainsAll: " + myCollection.containsAll(myTestCollection));

        for (Object o : myCollection.getElementData()) {
            System.out.print(o + " ");
        }

        System.out.println("!");

        System.out.println("Remove: " + myCollection.remove(3));

        for (Object o : myCollection.getElementData()) {
            System.out.print(o + " ");
        }
        /*System.out.println("!");
        System.out.println("RemoveAll: " + myCollection.removeAll(myTestCollection));

        for (Object o : myCollection.getElementData()) {
            System.out.print(o + " ");
        }*/
        System.out.println("!");
        System.out.println("IsEmpty: " + myCollection.isEmpty());
        System.out.println("RetainAll: " + myCollection.retainAll(myTestCollection));
        System.out.print("myCollection: ");
        for (Object o : myCollection.getElementData()) {
            System.out.print(o + " ");
        }
        System.out.println("!");
        System.out.print("myTestCollection: ");
        for (Object o : myTestCollection.getElementData()) {
            System.out.print(o + " ");
        }
        System.out.println("!");
        System.out.println("IsEmpty: " + myCollection.isEmpty());
        Object[] objects = myCollection.toArray();
        System.out.print("myTestCollection to Array: ");
        for (Object o : objects) {
            System.out.print(o + " ");
        }
        System.out.println("!");
        Object[] objectsTest = myCollection.toArray(objects);
        System.out.print("myTestCollection to Array(Array's type): ");
        for (Object o : objectsTest) {
            System.out.print(o + " ");
        }
        System.out.println("!");
        myCollection.clear();
        //System.out.println("IsEmpty: " + myCollection.isEmpty());
    }
}
