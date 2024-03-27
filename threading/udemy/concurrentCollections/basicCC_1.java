
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class basicCC_1 {
    public static void main(String[] args) {
        // basic concurrent collections
        Vector<Integer> arr = new Vector<>();
        arr.add(1); 

        Hashtable<Integer, Integer> ht = new Hashtable<>();
        ht.put(1,2);

        StringBuffer sb = new StringBuffer();
        sb.append("okay");

        // synchronized collections
        List<Integer> list = new ArrayList<>();
        Collection<Integer> threadSafeList = java.util.Collections.synchronizedList(list);
        // other synchronized methods - 
        // java.util.Collections.synchronizedMap(___);
        // java.util.Collections.synchronizedSet(___);
        // java.util.Collections.synchronizedSortedMap(___);
    }   
}
