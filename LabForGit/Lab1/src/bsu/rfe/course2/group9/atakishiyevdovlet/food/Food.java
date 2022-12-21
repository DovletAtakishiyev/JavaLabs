package bsu.rfe.course2.group9.atakishiyevdovlet.food;

public abstract class Food implements Consumable, Nutritious {
    Food(String name, int calories){
        this.name = name;
        this.calories = calories;
    }
    protected String name  = null;
    protected int calories  = 0;
    protected int fullNameSize;

//    @Override
//    public void consume() {}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Food)) return false;
        return name.equals(((Food) obj).name) && calories == (((Food) obj).calories);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void calculateCalories() {
        System.out.println(name + " has " + calories + " calories." );
    }

    public int getCalories() {
        return calories;
    }

    public Integer getFullNameSize(){
        return  fullNameSize;
    }
}
