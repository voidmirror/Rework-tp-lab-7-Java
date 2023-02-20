package tplab7_flat;

public class Main {

    public static void main(String[] args) {

//        Unit predator = new Predator();
//        System.out.println(predator instanceof Unit);
//        System.out.println(predator instanceof Prey);
//        System.out.println(predator instanceof Predator);


        // Uppercast | Downcast

        Unit prey = new Prey();         // Uppercast

        Prey p2 = (Predator) prey;          // явное приведение типов Unit к Prey | Downcast
        p2.grow();

        Predator predator1 = new Predator();
        Predator predator2 = predator1;
        Predator predator3 = predator1;
        Predator predator4 = predator1;
        Predator predator5 = predator1;
        Predator predator6 = new Predator();
        Predator predator7 = predator1;
        Predator predator8 = predator1;

        System.out.println(predator1);
        System.out.println(predator4);
        System.out.println(predator6);

    }

}
