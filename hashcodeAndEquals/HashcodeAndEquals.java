import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;


public class HashcodeAndEquals {
    public static void main(String[] args) {
        class Car<T> {
            void set(T t){

            }
        }
        
        class Student {
            private Integer id;
            private String name;
            String address;
            
            Student(int id, String name, String addr) {
                this.id = id;   this.name = name;   this.address = addr;
            }
            
            @Override
            public String toString() {
                return this.id + " :: " + this.name + " :: " + this.address;
            }

            @Override
            public int hashCode() {
                // return 13; enable forsimple map
                return Objects.hash(id, name);  // enable for treeset
            }

            // enable for treeset
            @Override
            public boolean equals(Object o) {
                if(this == o)   return true;
                if(!(o instanceof Student)) return false;
                Student s = (Student) o;
                return Objects.equals(id, s.id) && Objects.equals(name, s.name);
            }
        }

        Student s1 = new Student(1, "Ram", "abcdef");
        Student s2 = new Student(1, "Barcat Boy", "popeye");

        // simple map
        Map<Student, String> m = new HashMap<>();
        m.put(s1, "FPO");
        m.put(s2, "SPO");
        m.put(new Student(1, "Ram", null), "Override");
        
        System.out.println("Sige ::" + m.size());
        System.out.println(m.get(new Student(1, "Ram", "abll")));
        // System.out.println(s1.toString());
        // System.out.println(s2.toString());


        // treeset
        Set<Student> ts = new TreeSet<>();
        ts.add(s2);
        ts.add(null);
        ts.add(s1);

        for(Student ss : ts) {
            System.out.print(ss + "::");
        }

        //

        Car<String> cc = new Car<>();
        Car g = cc;

        //

        ExecutorService e = Executors.newCachedThreadPool();
        e.execute(new Thread());
        e.shutdown();
        
        // Lambda snippet
        boolean b = check(new ArrayList<>(), al -> al.isEmpty());
        // boolean b = check(new ArrayList<>(), ArrayList al -> al.isEmpty());
        boolean bb = check(new ArrayList<>(), al -> al.add("oye"));
        // boolean b = check(new ArrayList<>(), al -> return al.size() == 0);
        
        System.out.println(bb ? "Yes" : "No");
        
    }
    static class Thread implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("ok");
            } catch (Exception e) {
                System.out.println("Err");
            }
        }
    }

    // class Test {
        public static boolean check(List l, Predicate<List> p) {
            return p.test(l);
        }
    // }
}