package exercise3;

import java.util.*;

/**
 * Exercise 3. Implement a HashMap from scratch. In order to pass all the tests
 * you need to implement all the methods defined below. The key-value pair will
 * be encapsulated in the MyHashMap.MyEntry object defined below.
 * <p>
 * The buckets list in which each MyEntry object will be stored is stored in "buckets" object.
 */
public class MyHashMap {

    private ArrayList<LinkedList<MyEntry>> buckets;

    private int capacity;

    public MyHashMap(int capacity) {
        this.capacity = capacity;

        // Initialize buckets list
        buckets = new ArrayList<LinkedList<MyEntry>>();
        for (Integer i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<MyEntry>());
        }
    }

    public String get(String key) {

        LinkedList<MyEntry> list;
        if (key == null)
            list = buckets.get(0);
        else
            list = buckets.get(Math.abs(key.hashCode()) % capacity);
        for (MyEntry m : list)
            if (m.key.equals(key))
                return m.value;
        return null;
    }

    public void put(String key, String value) {
        // TODO
        MyEntry entry = new MyEntry(key, value);

        LinkedList<MyEntry> list;
        if (key == null)
            list = buckets.get(0);
        else
            list = buckets.get(Math.abs(key.hashCode()) % capacity);
        int ok = 0;
        for (MyEntry m : list)
            if (m.key.equals(key)) {
                m.value = value;
                ok = 1;
            }
        if (ok == 0)
            list.add(entry);
    }

    public Set<String> keySet() {
        Set<String> set = new HashSet<String>();
        for (LinkedList<MyEntry> i : buckets) {
            for (MyEntry j : i) {
                set.add(j.getKey());
            }
        }
        return set;
    }

    public Collection<String> values() {
        ArrayList<String> list = new ArrayList<String>();
        for (LinkedList<MyEntry> i : buckets) {
            for (MyEntry j : i) {
                list.add(j.getValue());
            }
        }
        return list;
    }

    public String remove(String key) {
        if (key == null) {
            return null;
        }
        if (capacity == 0)
            return null;
        int k = Math.abs(key.hashCode()) % capacity;
        LinkedList<MyEntry> list = buckets.get(k);
        for (MyEntry m : list)
            if (m.key.equals(key)) {
                list.remove(m);
                return m.getValue();
            }
        return null;
    }

    public boolean containsKey(String key) {
        if (capacity == 0)
            return false;
        int k = Math.abs(key.hashCode()) % capacity;
        LinkedList<MyEntry> list = buckets.get(k);
        for (MyEntry m : list)
            if (m.key.equals(key)) {
                return true;
            }
        return false;
    }

    public boolean containsValue(String value) {
        // TODO
        for (LinkedList<MyEntry> le : buckets) {
            for (MyEntry var : le) {

                if (var.value.equals(value))
                    return true;
                if (var.equals(value))
                    return true;

            }
        }

        return false;
    }

    public int size() {
        // TODO Return the number of the Entry objects stored in all the buckets
        int count = 0;
        for (LinkedList<MyEntry> le : buckets) {
            for (MyEntry var : le) {
                count++;
            }
        }


        return count;
    }

    public void clear() {
        // TODO Remove all the Entry objects from the bucket list
        buckets.clear();
    }

    public Set<MyEntry> entrySet() {
        Set<MyEntry> set = new HashSet<MyEntry>();
        for (LinkedList<MyEntry> me : buckets) {
            for (MyEntry m : me)
                set.add(m);
        }
        // TODO Return a Set containing all the Entry objects
        return set;
    }

    public boolean isEmpty() {
        // TODO
        for (LinkedList<MyEntry> me : buckets) {
            if (!me.isEmpty())
                return false;
        }
        return true;
    }

    public static class MyEntry {
        private String key;
        private String value;

        public MyEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
