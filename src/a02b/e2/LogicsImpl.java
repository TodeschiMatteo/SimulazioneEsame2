package a02b.e2;

import java.util.Map;
import java.util.Optional;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LogicsImpl implements Logics{

    final Map<Point, Boolean> cells = new HashMap<>();

    LogicsImpl(final int size){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                this.cells.put(new Point(j, i), false);
            }
        }
    }

    @Override
    public void onClick(Point point) {
        cells.put(point, !cells.get(point));
    }

    public Map<Point, Boolean> update(){
        return Collections.unmodifiableMap(this.cells);
    }

    @Override
    public Optional<List<Point>> checkDiagonal() {
        // Controlla le diagonali principali (dall'alto a sinistra al basso a destra)
        for (int k = 0; k < 7; k++) {
            List<Point> diagonal = new ArrayList<>();
            int filledCount = 0;
            for (int i = 0; i <= k; i++) {
                Point point = new Point(i, k - i);
                diagonal.add(point);
                if (cells.getOrDefault(point, false)) {
                    filledCount++;
                }
            }
            if (filledCount == 3) {
                return Optional.of(diagonal);
            }
        }
        for (int k = 1; k < 7; k++) {
            List<Point> diagonal = new ArrayList<>();
            int filledCount = 0;
            for (int i = 0; i <= 6 - k; i++) {
                Point point = new Point(k + i, 7 - i);
                diagonal.add(point);
                if (cells.getOrDefault(point, false)) {
                    filledCount++;
                }
            }
            if (filledCount == 3) {
                return Optional.of(diagonal);
            }
        }

        // Controlla le diagonali secondarie (dall'alto a destra al basso a sinistra)
        for (int k = 0; k < 7; k++) {
            List<Point> diagonal = new ArrayList<>();
            int filledCount = 0;
            for (int i = 0; i <= k; i++) {
                Point point = new Point(i, i - k + 7);
                diagonal.add(point);
                if (cells.getOrDefault(point, false)) {
                    filledCount++;
                }
            }
            if (filledCount == 3) {
                return Optional.of(diagonal);
            }
        }
        for (int k = 1; k < 7; k++) {
            List<Point> diagonal = new ArrayList<>();
            int filledCount = 0;
            for (int i = 0; i <= 6 - k; i++) {
                Point point = new Point(k + i, i);
                diagonal.add(point);
                if (cells.getOrDefault(point, false)) {
                    filledCount++;
                }
            }
            if (filledCount == 3) {
                return Optional.of(diagonal);
            }
        }
        return Optional.empty();
    }
}
