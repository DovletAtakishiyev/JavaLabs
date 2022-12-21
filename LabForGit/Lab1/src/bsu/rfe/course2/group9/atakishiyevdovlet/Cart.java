package bsu.rfe.course2.group9.atakishiyevdovlet;
import bsu.rfe.course2.group9.atakishiyevdovlet.food.Food;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Food> korzina = new ArrayList<>();
    private int sumCalories = 0;

    void addToCart(Food product){
        korzina.add(product);
        sumCalories += product.getCalories();
        System.out.println(".....added");
    }
    void removeItem(int productID){
        sumCalories -= korzina.get(productID).getCalories();
        korzina.get(productID).consume();
        korzina.remove(productID);
    }

    void getList(){
        System.out.println();
        System.out.println("Breakfast List:");
        for (int i = 0; i < korzina.size(); i++) {
            System.out.println(i+1 + ". " + korzina.get(i));
        }
        System.out.println();
    }

    void allCalories(){
        System.out.println("All calories equal to " + sumCalories);
    }

    void sort(){
        Food temp;
        for (int i = 0; i < korzina.size()-1; i++)
            for (int j = 0; j < korzina.size() - i - 1; j++)
                if (korzina.get(j).getFullNameSize() < korzina.get(j+1).getFullNameSize()){
                    temp = korzina.get(j);
                    korzina.set(j, korzina.get(j + 1));
                    korzina.set(j+1, temp);
                }
        System.out.println(".....sorted");

//        Arrays.sort(korzina, new Comparator() {
//            public int compare(Object f1, Object f2) {
//                if (f1 == null) {
//                    return 1;
//                }
//                if (f2 == null) {
//                    return -1;
//                } else {
//                    return ((Food) f2).getFullNameSize().compareTo(((Food) f1).getFullNameSize());
//                }
//            }
//        });
    }

    public Food get(int id){
        return korzina.get(id);
    }
    public void set(int id, Food arg){
        korzina.set(id, arg);
    }

    public boolean isEmpty(){
        return korzina.isEmpty();
    }

    public int size(){
        return korzina.size();
    }
    public boolean isEqual(int prod1, int prod2){
        return korzina.get(prod1 - 1).equals(korzina.get(prod2-1));
    }

}
