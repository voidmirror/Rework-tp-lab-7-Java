package tplab7_flat;

import java.util.Random;

public class Cell {

    private Unit unit;

//    public Cell() {
//
//    }

    public Cell(boolean empty) {
        if (empty) {
            this.unit = null;
        } else {
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
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    void live() {

        if (unit != null) {

            if (isPredator()) {
                Predator predator = (Predator) unit;
                if (predator.getAge() >= Predator.getTTL()) {
                    unit = null;
                    return;
                }
                predator.grow();

            } else if (isPray()) {
                Prey prey = (Prey) unit;                // Downcast
                if (prey.getAge() >= Prey.getTTL()) {
                    unit = null;
                    return;
                }
                prey.grow();

            } else if (isStone()) {

            }

        }

    }

    public boolean isStone() {
        return this.unit instanceof Stone;
    }

    public boolean isPray() {
        return this.unit instanceof Prey;
    }

    public boolean isPredator() {
        return this.unit instanceof Predator;
    }

    public boolean isEmpty() {
        return this.unit == null;
    }

    @Override
    public String toString() {
        return unit == null ? " " : unit.toString();
    }
}
