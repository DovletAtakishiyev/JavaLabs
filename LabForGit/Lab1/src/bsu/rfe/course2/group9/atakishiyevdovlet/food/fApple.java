package bsu.rfe.course2.group9.atakishiyevdovlet.food;

public class fApple extends Food{

    public fApple(String size){
        super("Apple", (size == "big") ? 40 : 35);
        this.size = size;
        super.fullNameSize = name.length() + size.length();
    }
    String size;

    @Override
    public void consume() {
        System.out.println(name + " size " + size + " SYEDENO");
    }

    @Override
    public String toString() {
        return name + " size " + size + " (" + calories + " cl)";
    }
}
