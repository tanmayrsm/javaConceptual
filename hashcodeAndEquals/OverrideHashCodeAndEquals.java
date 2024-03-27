import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class OverrideHashCodeAndEquals {
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
                return this.id;
            }
    
            @Override
            public boolean equals(Object o) {
                if(this == o)   return true;
                if(!(o instanceof Student)) return false;
                Student s = (Student) o;
                return Objects.equals(id, s.id);
            }
        }

        Student s1 = new Student(1, "Laka", "Lop");
        Student s2 = new Student(1, "T", "Top");

        System.out.println("sign equal :: " + (s1 == s2));  // reference still different in heap
        System.out.println("Normal equal :: " + (s1.equals(s2)));
        System.out.println("Both :: " + s1 + " :: " + s2);
        System.out.println("hascode of both :: " + s1.hashCode() + " :: " + s2.hashCode());
        

        // make a hashmap to check sideeffect of overriding both equals and hashcode
        Map<Student, Integer> map = new HashMap<>();
        map.put(s1, 1); // goes in same bucket
        map.put(s2, 2); // goes in same bucket
        // expectation -  as s1 and s2 have same roll nos, they must go in same bucket in hashMap, and s2 must override s1
        
        for(Map.Entry<Student, Integer> em : map.entrySet()) {  // ITERATION IN 2 BUCKETS IS CARRIED
            System.out.println("em :: " + em);
        }
        System.out.println("get s1 :: " + map.get(s1));
        System.out.println("get s2 :: " + map.get(s2));
        
        
        
    }
}

