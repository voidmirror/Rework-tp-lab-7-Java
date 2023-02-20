package tplab7_flat;

import java.util.ArrayList;

public class Ocean {

    ArrayList<ArrayList<Cell>> ocean = new ArrayList<>();
    ArrayList<ArrayList<Cell>> oceanNextStep = new ArrayList<>();

    void make() {
        for (int i = 0; i < 15; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                line.add(new Cell());
            }
            ocean.add(line);
        }
    }

    /**
     * 0) откопировать все Stone
     * 1) prey: isDead? -> grow -> move                 (относительно ocean)
     * 2) predator: isDead -> grow -> move -> eat       (относительно oceanNextStep)
     */
    void live() {

    }


}
