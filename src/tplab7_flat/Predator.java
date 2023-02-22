package tplab7_flat;

public class Predator extends Prey {

    private final static int TTL = 12;     // TTL - Time To Live
    private final static int TTR = 16;     // TTL - Time To Reproduction
    private int age = 0;
    private int satiety = 0;

    public static int getTTL() {
        return TTL;
    }

    public static int getTTR() {
        return TTR;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public void increaseSatiety() {
        satiety++;
    }

    @Override
    public boolean readyToReproduction() {
        return satiety == TTR;
    }

    @Override
    public Predator processReproduction() {
        return new Predator();
    }

    @Override
    public String toString() {
        return "P";
    }
}
