package BasicTricky;

public class StaticClass {
    class Ram {
        int x;
        Ram(int x) {
            this.x = x;
        }
        public void display() {
            System.out.println("Running in method of static class");
        }
        public static void displayStatic() {
            System.out.println("Running in static method of static class");
        }
        
    }
    public static void main(String[] args) {
        
        // Ram r = new Ram(22);    // u can never access a NON STATIC class from a static method
        // what to do ?
        // make this class 'Ram' as static
        // or - make an instact of parent class whose 'main' method is part of, and then try to access 'Ram class'
            // eg -
            StaticClass ex = new StaticClass();
            Ram r = ex.new Ram(22);

        // THEORY -
        // static classes can be accessed without making an instance
        // eg - ABC.methodName();
        // where class ABC is a static class
        // ON OTHER HAND
        // to access normal class, u will need an instance
            // eg - if 'Ram' class is normal, u can access it
            // StaticClass ex = new StaticClass();
            // Ram r = ex.new Ram(22);
        r.display();
        Ram.displayStatic();    // else gives lint error, as static class ke static method must be accessed thru static class name
    }

}
