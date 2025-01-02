package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<Point,JButton> cells = new HashMap<>();
    private Logics logics;
    
    public GUI(int size) {
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
        	    Point point = cells.entrySet().stream()
                    .filter(t -> t.getValue().equals(button))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get();
                logics.onClick(point);
                updateGUI();
            }
        };

        ActionListener checkListener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(logics.checkRestart()){
                    cells.forEach((p, b) -> cells.get(p).setEnabled(true));
                    logics = new LogicsImpl(size);
                    updateGUI();
                } else {
                    Optional<List<Point>> toDisable=logics.checkDiagonal();
        	        if(toDisable.isPresent()){
                        toDisable.get().forEach(p -> cells.get(p).setEnabled(false));
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

    void updateGUI(){
        Map<Point,Boolean> status = logics.update();
        status.forEach((p, s) -> this.cells.get(p).setText(status.get(p)?"*":" "));
    }
}
