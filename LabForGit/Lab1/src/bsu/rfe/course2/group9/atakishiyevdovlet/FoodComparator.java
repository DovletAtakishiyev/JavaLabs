package bsu.rfe.course2.group9.atakishiyevdovlet;

import bsu.rfe.course2.group9.atakishiyevdovlet.food.Food;

import java.util.Comparator;

public class FoodComparator implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return o1.getFullNameSize().compareTo(o2.getFullNameSize());
    }
}
