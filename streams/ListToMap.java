import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListToMap {
    public static void main(String[] args) {
        class Student {
            private Integer id;
            private String name;
            
            Student(int id, String name) {
                this.id = id;   this.name = name;
            }

            public Integer getId() {
                return this.id;
            }
        }

        class Account {
            private String name;
            private Integer balance;

            Account(String name, Integer bal) {
                this.name = name;
                this.balance = bal;
            }

            public Integer getBalance()  {
                return this.balance;
            }
            public void setBalance(int bal) {
                this.balance = bal;
            }

            public String getName()  {
                return this.name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public String toString() {
                return this.getName() + " ::" + this.getBalance();
            }
        }
        
        // REQD - LIST TO MAP
        List<Student> ls = Arrays.asList(new Student(1, "Ram"), new Student(2, "Shyam"));


        Map<Integer, Student> mp = ls.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println("Map :: " + mp);

        // LIST TO {name : [{name:..., ...}, {name:..., ...}]}
        System.out.println("\nLIST TO {name : [{name:..., ...}, {name:..., ...}]}");
        List<Account> lstt = Arrays.asList(new Account("Alice", 100), new Account("Ram", 10), new Account("Alice", 4000));
        
        Map<String, List<Account>> opt = lstt.stream().collect(Collectors.groupingBy(p -> p.getName()));
        System.out.println(opt);

        // {name : [{name:..., ...}, {name:..., ...}]} TO [{name:..., ...}, {name:..., ...}]
        System.out.println("\n{name : [{name:..., ...}, {name:..., ...}]} TO [{name:..., ...}, {name:..., ...}]");

        List<Account> okt = opt.entrySet().stream().flatMap(i -> i.getValue().stream()).collect(Collectors.toList());
        System.out.println(okt);


        // LIST TO {name : sum_of_all_accountBal}
        System.out.println("\nLIST TO {name : sum_of_all_accountBal}");
        
        List<Account> lst = Arrays.asList(new Account("Alice", 100), new Account("Ram", 10), new Account("Alice", 4000));
        Map<String, Integer> mp2 = lst.stream().collect(Collectors.groupingBy(Account::getName, Collectors.summingInt(account -> account.getBalance())));

        System.out.println("Map ::" + mp2);

        // find max in list
        Integer maxi = ls.stream().map(i -> i.getId()).max((i1, i2) -> i1.compareTo(i2)).orElse(null);
        System.out.println("Max :: " + maxi);

        // MERGE TWO LIST IN ONE
        List<String> list1 = Arrays.asList("apple", "banana", "cherry");
        List<String> list2 = Arrays.asList("banana", "grape", "apple");

        // METH 1
        List<String> mergedList = Stream.concat(list1.stream(), list2.stream())
                                        .distinct()
                                        .collect(Collectors.toList());
        
        System.out.println(mergedList);

    }
}
