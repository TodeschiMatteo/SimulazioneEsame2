package a02b.e2;

import java.util.Map;
import java.util.Optional;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LogicsImpl implements Logics{

    private final Map<Point, Boolean> cells = new HashMap<>();
    private final int size;
    private boolean toReload = false;

    LogicsImpl(final int size){
        this.size = size;
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
        int x=0;
        int y=0;
        int count=0;
        // upLeft-doRight diagonals (\)
        // Compressed 4 cycle in 2 using reflection propiety of Coordinates
        for(y=size-1; y>=0; y--){
            List<Point> list = new ArrayList<>();
            for(count = 0; count< size - y; count++){
                list.add(new Point(x+count, y+count));
            }
            var diagonalToDisable = threeOnDiagonal(list);
            if(diagonalToDisable.isPresent()){return diagonalToDisable;}
            list = new ArrayList<>();
            for(count = 0; count< size - y; count++){
                list.add(new Point(y+count, x+count));
            }
            diagonalToDisable = threeOnDiagonal(list);
            if(diagonalToDisable.isPresent()){return diagonalToDisable;}
        }
        // upRight-doLeft diagonals (/)
        // Compressed 4 cycle in 2 using reflection propiety of Coordinates
        y=0;
        for(x=0; x<size; x++){
            List<Point> list = new ArrayList<>();
            for(count = 0; count <= x/2; count++){
                    list.add(new Point(x-count, count));
                    list.add(new Point(count, x-count));
            }
            list = new ArrayList<>();
            var diagonalToDisable = threeOnDiagonal(list);
            if(diagonalToDisable.isPresent()){return diagonalToDisable;}
            for(count = 0; count <= (size-x)/2; count++){
                list.add(new Point(size-1-count, x+count));
                list.add(new Point(x+count, size-1-count));
            }
            diagonalToDisable = threeOnDiagonal(list);
            if(diagonalToDisable.isPresent()){return diagonalToDisable;}
        }
        return Optional.empty();
    }

    private Optional<List<Point>> threeOnDiagonal(List<Point> list){
        if(list.stream().distinct().map(e -> this.cells.get(e)).filter(e -> e.equals(true)).count() == 3){
            this.toReload = true;
            return Optional.of(list);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean checkRestart() {
        return this.toReload;
    }

}
