import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class StreamBasic {
    static class A<T> {
        void add(T t) {

        }
    }
    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(10,12,1,2121,938);
        A<Number> a = new A<>();
        a.add(new Integer(1));
        a.add(new Double(1.0));

        arr.stream().
        map(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 8;
            }
        }).
        filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer x) {
                return x % 2 == 0;
            }
        }).
        forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer x) {
                System.out.println(" :: " +  x);
            }
        });
    }    
}
