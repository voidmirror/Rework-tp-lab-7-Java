package tplab7_flat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Ocean {

    private final static int HEIGHT = 15;
    private final static int WIDTH = 50;

    ArrayList<ArrayList<Cell>> ocean;
    ArrayList<ArrayList<Cell>> oceanNextStep;

    public void make() {
         ocean = new ArrayList<>();

        for (int i = 0; i < HEIGHT; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || j == 0 || i == HEIGHT - 1 || j == WIDTH -1) {
                    line.add(new Cell(true));                             // Пустая клетка
                    line.get(j).setUnit(new Stone());                           // Заполняем камнем
                }
                line.add(new Cell(false));                                // Клетка с рандомным наполнением
            }
            ocean.add(line);
        }

        makeOceanNextStep();

    }

    private void makeOceanNextStep() {
        oceanNextStep = new ArrayList<>();
        for (int i = 0; i < HEIGHT; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < WIDTH; j++) {
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
    public void live() {

        while (true) {
            printOcean(ocean);
            prepareNextStep();
            move();
            reproduction();
            eat();
            doStep();
            System.out.println();
            try {
                Thread.sleep(10000);
//                Runtime.getRuntime().exec("cls");

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();


//                final String ANSI_CLS = "\u001b[2J";
//                final String ANSI_HOME = "\u001b[H";
//                System.out.print(ANSI_CLS + ANSI_HOME);
//                System.out.flush();


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//
//            }
//        }
    }

    private void prepareNextStep() {
        // Copy stones (not necessary, but prevent Stone changes)
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Cell c = ocean.get(i).get(j);
                if (c.isStone()) {
                    oceanNextStep.get(i).set(j, c);
                }
            }
        }
    }

    private void move() {
        Random random = new Random();

        // PREYS
        for (int i = 1; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {
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
        for (int i = 1; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {
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

    private void reproduction() {

        for (int i = 1; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {

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

    private void findReproductionPlaceAround(Unit unit, int x, int y) {
        boolean done = false;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i != j) {
                    Cell checking = oceanNextStep.get(i).get(j);
                    if (checking.isEmpty()) {
                        checking.setUnit(unit);
                        done = true;
                    }
                }
            }
        }
        if (!done) {                                                    // Если все места заняты, новая вместо старой
            oceanNextStep.get(x).get(y).setUnit(unit);
        }
    }

    private void eat() {

        for (int i = 1; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {
                Cell cell = oceanNextStep.get(i).get(j);
                if (cell.isPredator()) {
                    Predator predator = (Predator) cell.getUnit();

                    for (int row = i - 1; row < i + 2 && predator.getSatiety() < Predator.getTTR(); row++) {
                        for (int col = j - 1; col < j + 2 && predator.getSatiety() < Predator.getTTR(); col++) {
                            if (row != col) {
                                Cell checking = oceanNextStep.get(row).get(col);
                                if (checking.isPray() && !checking.isPredator()) {
                                    checking.setUnit(null);
                                    predator.increaseSatiety();
                                }
                            }
                        }
                    }

                }
            }
        }

    }

    public void printOcean(ArrayList<ArrayList<Cell>> ocean) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(ocean.get(i).get(j));
            }
            System.out.println();
        }
    }

    public void doStep() {
        ocean = oceanNextStep;
        makeOceanNextStep();
    }


}
