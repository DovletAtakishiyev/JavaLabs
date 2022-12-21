package bsu.rfe.course2.group9.atakishiyevdovlet;
import bsu.rfe.course2.group9.atakishiyevdovlet.food.Food;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    private static Scanner scr = new Scanner(System.in);
    private static String []size    = {"small", "big"};
    private static String []filling = {"chicken", "beef", "pork"};
    private static Food foodTemplate = null;
    private static  int choice;

    public static void menu(Cart korzina, String []args){
        do {
            showMenu(korzina);
            do {
                System.out.print("choice: ");
                choice = scr.nextInt();

            }while ((choice > 2 && korzina.isEmpty()) || choice < 1 || choice > 7);

            switch (choice){
                case 1:
                    korzina.getList();
                    break;
                case 2:
                    add(korzina, args);
                    break;
                case 3:
                    removeFromList(korzina);
                    break;
                case 4:
                    korzina.sort();
                    break;
                case 5:
                    korzina.allCalories();
                    break;
                case 6:
                    sorting(korzina);
                    break;
                default:
                    break;
            }
        }while (choice != 7);
    }

    private static void compare(Cart korzina){
        int x, y;
        korzina.getList();
        System.out.println("Please choose what u wanna compare");

        do {
            System.out.print("Product #1: ");
            x = scr.nextInt();
        }while (x < 1 || x > korzina.size());

        do {
            System.out.print("Product #2: ");
            y = scr.nextInt();
        }while (y < 1 || y > korzina.size());

        System.out.println();
        if (korzina.isEqual(x,y)){
            System.out.println("Equals!");
        }else{
            System.out.println("Not Equals!");
        }

    }

    private static void listOfAll(){
        System.out.println("#  PRODUCT \t\tTYPE               \t\tCALORIES");
        System.out.println("1) Apple   \t\t(small/big)        \t\t      35/40 cl");
        System.out.println("2) Cheese  \t\t(regular)          \t\t         50 cl");
        System.out.println("3) Sandwich\t\t(chicken/beef/pork)\t\t140/160/190 cl");
        System.out.println();
    }

    private static void showMenu(Cart korzina){
        System.out.println();
            System.out.println("   MENU:");
            System.out.println("1) My products;");
            System.out.println("2) Add product;");
        if (!korzina.isEmpty()){
            System.out.println("3) Eat product;");
            System.out.println("4) Sort cart;");
            System.out.println("5) Calculate calories;");
            if (korzina.size() > 1 )
                System.out.println("6) Compare products;");
        }
            System.out.println("7) Quit.");
        System.out.println();
    }
    private static void removeFromList(Cart korzina){
        korzina.getList();
        System.out.println("Please chose what you want to eat?");

        do {
            System.out.print("choice: ");
            choice = scr.nextInt();
        }while (choice < 1 || choice > korzina.size());

        korzina.removeItem(choice -1);

    }

    private static void add(Cart korzina, String []args){
        listOfAll();
        do {
            System.out.print("Please choose: ");
            choice = scr.nextInt();
            if(choice == 1){
                do {
                    System.out.print("Please choose size (small/big): ");
                    choice = scr.nextInt();
                }while (choice < 1 || choice > 2);
                addApple(korzina, args[0], choice);
                continue;
            }
            if (choice == 2){
                addCheese(korzina, args[1]);
                continue;
            }
            if (choice == 3){
                do {
                    System.out.print("Please choose filling (chicken/beef/pork): ");
                    choice = scr.nextInt();
                }while (choice < 1 || choice > 3);
                addSandwich(korzina, args[2], choice);
                continue;
            }
        }while (choice < 1 || choice > 3);
    }

    private static void addApple(Cart korzina, String arg, int type){
        try {
            Class clss = Class.forName("bsu.rfe.course2.group9.atakishiyevdovlet.food." + arg);
            Class param = String.class;
            foodTemplate = (Food)clss.getConstructor(param).newInstance(size[type-1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        korzina.addToCart(foodTemplate);

//        System.out.println("Apple added");
    }
    private static void addCheese(Cart korzina, String arg){
        try {
            Class clss = Class.forName("bsu.rfe.course2.group9.atakishiyevdovlet.food." + arg);
            foodTemplate = (Food)clss.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        korzina.addToCart(foodTemplate);
//        System.out.println("Cheese added");
    }
    private static void addSandwich(Cart korzina, String arg, int type) {
        try {
            Class clss = Class.forName("bsu.rfe.course2.group9.atakishiyevdovlet.food." + arg);
            Class param = String.class;
            foodTemplate = (Food)clss.getConstructor(param).newInstance(filling[type-1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        korzina.addToCart(foodTemplate);
//        System.out.println("Sandwich added");
    }

    public static void sorting(Cart korzina){
        Food []aKorzina = new Food[korzina.size()];

        for (int i = 0; i < korzina.size(); i++) {
            aKorzina[i] = korzina.get(i);
        }
        Arrays.sort(aKorzina, new FoodComparator());

        for (int i = 0; i < aKorzina.length; i++) {
            korzina.set(i,aKorzina[i]);
        }
        System.out.println(".....sorted");
    }
}
