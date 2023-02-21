package tplab7_flat;

import java.util.ArrayList;
import java.util.Random;

public class Ocean {

    private static int SIZE = 15;

    ArrayList<ArrayList<Cell>> ocean = new ArrayList<>();
    ArrayList<ArrayList<Cell>> oceanNextStep = new ArrayList<>();

    void make() {
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                if (i == 0 || j == 0 || i == SIZE - 1 || j == SIZE -1) {
                    line.add(new Cell(true));                             // Пустая клетка
                    line.get(j).setUnit(new Stone());                           // Заполняем камнем
                }
                line.add(new Cell(false));                                // Клетка с рандомным наполнением
            }
            ocean.add(line);
        }

        for (int i = 0; i < SIZE; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                line.add(new Cell(true));                              // Пустая клетка
            }
            oceanNextStep.add(line);
        }
    }

    /**
     * 0) откопировать все Stone
     * 1) prey: isDead? -> grow -> move                 (относительно ocean)
     * 2) predator: isDead -> grow -> move -> eat       (относительно oceanNextStep)
     */
    void live() {
        prepareNextStep();
        move();
        reproduction();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

            }
        }
    }

    void prepareNextStep() {
        // Copy stones (not necessary, but prevent Stone changes)
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell c = ocean.get(i).get(j);
                if (c.isStone()) {
                    oceanNextStep.get(i).set(j, c);
                }
            }
        }
    }

    void move() {
        Random random = new Random();

        // PREYS
        for (int i = 1; i < SIZE - 1; i++) {
            for (int j = 1; j < SIZE - 1; j++) {
                int x = random.nextInt(3) - 1;
                int y = random.nextInt(3) - 1;
                Cell current = ocean.get(i).get(j);
                Cell target = ocean.get(i + x).get(j + y);
                Cell targetNext = oceanNextStep.get(i + x).get(j + y);

                if (!current.isStone() && !target.isStone()) {
                    if (current.isPray() && !current.isPredator() && target.isEmpty()) {
                        targetNext.setUnit(current.getUnit());
                    }
                }
            }
        }

        // PREDATORS
        for (int i = 1; i < SIZE - 1; i++) {
            for (int j = 1; j < SIZE - 1; j++) {
                int x = random.nextInt(3) - 1;
                int y = random.nextInt(3) - 1;
                Cell current = ocean.get(i).get(j);
                Cell target = ocean.get(i + x).get(j + y);
                Cell targetNext = oceanNextStep.get(i + x).get(j + y);

                if (!current.isStone() && !target.isStone()) {
                    if (current.isPredator() && !target.isPredator()) {
                        if (targetNext.isPray() && !targetNext.isPredator()) {
                            targetNext.setUnit(current.getUnit());
                            Predator predator = (Predator) targetNext.getUnit();
                            predator.increaseSatiety();
                        } else if (targetNext.isEmpty()) {
                            targetNext.setUnit(current.getUnit());
                        }
                    }
                }
            }
        }
    }

    void reproduction() {

        for (int i = 1; i < SIZE - 1; i++) {
            for (int j = 1; j < SIZE - 1; j++) {

                Cell current = ocean.get(i).get(j);
                Unit unit = null;                           // May produce NullPointerException ----
                if (current.isPray()) {                     //                                     |
                    unit = new Prey();                      //                                     V
                }
                if (current.isPredator()) {
                    unit = new Predator();
                }
                findReproductionPlaceAround(unit, i, j);    // NullPointerException, если не учтены ВСЕ виды размножающихся

            }
        }

    }

    void findReproductionPlaceAround(Unit unit, int x, int y) {
        boolean done = false;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                Cell checking = oceanNextStep.get(i).get(j);
                if (checking.isEmpty()) {
                    checking.setUnit(unit);
                    done = true;
                }
            }
        }
        if (!done) {                                                    // Если все места заняты, новая вместо старой
            oceanNextStep.get(x).get(y).setUnit(unit);
        }
    }

    void eat() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell cell = oceanNextStep.get(i).get(j);
                if (cell.isPredator()) {

                    

                }
            }
        }

    }


}
