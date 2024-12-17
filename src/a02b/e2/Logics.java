package a02b.e2;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface Logics {
    public void onClick(final Point point);
    public Map<Point, Boolean> update();
    public List<Point> checkDiagonal();
}
