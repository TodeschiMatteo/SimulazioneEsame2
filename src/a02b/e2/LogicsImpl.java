package a02b.e2;

import java.util.Map;

import java.awt.Point;
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
    public List<Point> checkDiagonal() {
        
        return List.of(new Point(0,0));
    }
    
}
