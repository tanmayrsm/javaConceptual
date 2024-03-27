package com.first.demo.hashtable;

import java.util.Hashtable;

public class Ht {
    Hashtable<String, Integer> hashtable = new Hashtable<>();

    public Ht() {
        hashtable.put("A", 1);
        hashtable.put("B", 2);
        hashtable.put("C", 3);

        // Getting values from the hashtable
        int valueA = hashtable.get("B");
        System.out.println("Value of A: " + valueA);
    }

}
