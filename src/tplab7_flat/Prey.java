package tplab7_flat;

public class Prey implements Unit {

    private final static int TTL = 4;     // TTL - Time To Live
    private int age = 0;

    public int getAge() {
        return age;
    }

    public static int getTTL() {
        return TTL;
    }

    public void grow() {
        age++;
    }

    public boolean readyToReproduction() {
        return age % 2 == 1;
    }

    public Prey processReproduction() {
        return new Prey();
    }


}
