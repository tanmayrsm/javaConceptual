import java.util.*;

public class LambdaExp {
    public static void main(String[] args) {
        // NORMAL LIFE
        List<Integer> x = new List<Integer>() {

            @Override
            public int size() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'size'");
            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
            }

            @Override
            public boolean contains(Object o) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'contains'");
            }

            @Override
            public Iterator<Integer> iterator() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'iterator'");
            }

            @Override
            public Object[] toArray() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toArray'");
            }

            @Override
            public <T> T[] toArray(T[] a) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toArray'");
            }

            @Override
            public boolean add(Integer e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'add'");
            }

            @Override
            public boolean remove(Object o) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'remove'");
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
            }

            @Override
            public boolean addAll(Collection<? extends Integer> c) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'addAll'");
            }

            @Override
            public boolean addAll(int index, Collection<? extends Integer> c) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'addAll'");
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'clear'");
            }

            @Override
            public Integer get(int index) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'get'");
            }

            @Override
            public Integer set(int index, Integer element) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'set'");
            }

            @Override
            public void add(int index, Integer element) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'add'");
            }

            @Override
            public Integer remove(int index) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'remove'");
            }

            @Override
            public int indexOf(Object o) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
            }

            @Override
            public int lastIndexOf(Object o) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'lastIndexOf'");
            }

            @Override
            public ListIterator<Integer> listIterator() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
            }

            @Override
            public ListIterator<Integer> listIterator(int index) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
            }

            @Override
            public List<Integer> subList(int fromIndex, int toIndex) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'subList'");
            }
            
        };
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++) {
                    System.out.println("ok");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        r.run();

        Thread ll = new Thread(() -> {
            Integer x1 = new Integer(12);

        });

        ll.start();


        lambdaLife();
    }

    private static void lambdaLife() {
        // List<Integer> x = () -> {

        // };

        Runnable r = () -> {
            for(int i = 0; i < 5; i++) {
                System.out.println("ok in lambda");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        r.run();

        Thread x = new Thread(() -> {
            Integer x1 = new Integer(12);


        });
        x.start();
    }
}