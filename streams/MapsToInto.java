
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MapsToInto {
    public static void main(String[] args) {
        List<Integer> arr = List.of(1,2,3,4,5);
        // List<Integer> ba = arr.stream().distinct().collect(Collectors.toList());

        // mapToInt -> it does only returns IntStream of current item, and passed below
        int summ = arr.stream().mapToInt(i -> i).sum(); // WAY 1
        int summ2 = arr.stream().reduce(0, (accu, item) -> accu + item);    // WAY 2

        System.out.println("sum :: "+ summ + " :: " + summ2);

        // arr to list and vice versa
        // int[] r = new int[] {1,2,3};
        // List<Integer> x = Arrays.stream(r).boxed().collect(Collectors.toList());
        // int[] cham = x.stream().mapToInt(i -> i).toArray();
        // double ssum = x.stream().mapToDouble(i -> i).sum();

        //
        List<Account> mapper = List.of(new Account(1, "A", 100), new Account(3, "A", 110), new Account(2, "B", 200));
        Map<String, List<Account>> mappedRes = mapper.stream().collect(Collectors.groupingBy(Account :: getName));

        // Map<String, Integer> mappedRes = mapper.stream().collect(Collectors.groupingBy(Account :: getName, Collectors.summingInt(item -> Integer.parseInt(item.getBalance()))));
        // name : sumOfBalance

        
        for(Map.Entry<String, List<Account>> x : mappedRes.entrySet()) {
            System.out.println("\n" + x.getKey() + " => ");
            for(Account a : x.getValue())
                System.out.print(a.toString() + ", ");
            System.out.println();
        }
    }

    static class Account {
        private Integer id;
        private String name;
        private Integer balance;

        public Account(Integer id, String name, Integer balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }
    
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return this.id + " :: " + this.name + " :: " + this.balance;
        }
        public Integer getId() {
            return this.id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    
        public String getName() {
            return this.name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Integer getBalance() {
            return this.balance;
        }
    
        public void setBalance(Integer balance) {
            this.balance = balance;
        }
    
        
    }
}