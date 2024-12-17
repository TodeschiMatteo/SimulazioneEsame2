package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<Point,JButton> cells = new HashMap<>();
    int dimension;
    Logics logics;
    boolean toRestart=false;
    
    public GUI(int size) {
        this.dimension=size;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);

        logics = new LogicsImpl(size);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    Point point = cells.entrySet().stream().filter(t -> t.getValue().equals(button)).map(Map.Entry::getKey).findFirst().orElse(null);
                logics.onClick(point);
                updateGUI(logics.update());
            }
        };

        ActionListener checkListener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(toRestart){
                    updateGUI(IntStream.range(0, 7)
                    .boxed()
                    .flatMap(x -> IntStream.range(0, 7).mapToObj(y -> new Point(x, y)))
                    .collect(Collectors.toMap(point -> point, point -> false)));
                    cells.forEach((p, b) -> cells.get(p).setEnabled(true));
                    logics = new LogicsImpl(size);
                    toRestart=false;
                } else {
                    Optional<List<Point>> toDisable=Optional.of(logics.checkDiagonal());
        	        if(toDisable.isPresent()){
                        toDisable.get().forEach(p -> cells.get(p).setEnabled(false));
                        toRestart=true;
                    }
                }
            }
        };
        JButton check = new JButton("Check > Restart");
        check.addActionListener(checkListener);
        main.add(BorderLayout.SOUTH, check);
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Point(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    void updateGUI(Map<Point,Boolean> status){
        status.forEach((p, s) -> this.cells.get(p).setText(status.get(p)?"*":" "));
    }
}
