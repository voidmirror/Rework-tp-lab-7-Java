package tplab7_flat;

import java.util.Random;

public class Cell {

    private Unit unit;

    Cell() {
        Random random = new Random();
        int r = random.nextInt(100);
        if (r < 10) {
            unit = new Stone();
        } else if (r < 70) {
            unit = new Prey();
        } else if (r < 90) {
            unit = new Predator();
        } else {
            unit = null;                // empty cell
        }
    }

    void live() {

        if (unit != null) {

            if (unit instanceof Predator) {
                Predator predator = (Predator) unit;
                if (predator.getAge() >= Predator.getTTL()) {
                    unit = null;
                    return;
                }



            } else if (unit instanceof Prey) {
                Prey prey = (Prey) unit;                // Downcast
                if (prey.getAge() >= Prey.getTTL()) {
                    unit = null;
                    return;
                }
                prey.grow();

            } else if (unit instanceof Stone) {

            }

        }

    }

}
