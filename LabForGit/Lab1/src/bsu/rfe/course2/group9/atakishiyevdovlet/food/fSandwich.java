package bsu.rfe.course2.group9.atakishiyevdovlet.food;

public class fSandwich extends Food{

    public fSandwich(String filling){
        super("Sandwich", (filling == "chicken") ? 140 : (filling == "beef") ? 160 : 190);
        this.filling = filling;
        super.fullNameSize = name.length() + filling.length();
    }
    String filling;

    @Override
    public void consume() {
        System.out.println(name + " with " + filling + " SYEDENO");
    }

    @Override
    public String toString() {
        return name + " with " + filling + " (" + calories + " cl)";
    }
}
