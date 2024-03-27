import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class OnlyOverrideHashCode {
    
    // EXPECTATION - student having same roll no, must overirde previous one in hashmap
    public static void main(String[] args) {
        class Student {
            private Integer id;
            private String name;
            String address;
            
            Student(int id, String name, String addr) {
                this.id = id;   this.name = name;   this.address = addr;
            }
    
            @Override
            public int hashCode() {
                return 13;
            }
        }

        Student s1 = new Student(1, "Laka", "Lop");
        Student s2 = new Student(2, "T", "Top");

        System.out.println("sign equal :: " + (s1 == s2));
        System.out.println("Normal equal :: " + (s1.equals(s2)));
        System.out.println("Both :: " + s1 + " :: " + s2);
        System.out.println("hascode of both :: " + s1.hashCode() + " :: " + s2.hashCode());
        

        // make a hasset to check sideeffect of overriding hascode
        Map<Student, Integer> map = new HashMap<>();
        map.put(s1, 1); // goes in same bucket
        map.put(s2, 2); // goes in same bucket, but doesnt overrides existing s1, as 'equals' is default here, hence s2 will be added to the end of LinkedList

        for(Map.Entry<Student, Integer> em : map.entrySet()) {  // LINEAR ITERATION IN SAME BUCKET with hashcode 13
            System.out.println("em :: " + em);
        }
        System.out.println("get s1 :: " + map.get(s1));
        
        
    }
}
