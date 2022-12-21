package bsu.rfe.course2.group9.atakishiyevdovlet.food;

public class fCheese extends Food{

    public fCheese(){
        super("Cheese", 50);
        super.fullNameSize = name.length();
    }

    @Override
    public void consume() {
        System.out.println(name + " SYEDENO");
    }

    @Override
    public String toString() {
        return name + " (" + calories + " cl)";
    }
}
