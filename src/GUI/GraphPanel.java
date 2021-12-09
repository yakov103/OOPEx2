package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphPanel extends JPanel implements ActionListener {
        public GraphPanel(){
         super();
         this.setBackground(new Color(243, 234, 199));



        }


        void reset(){
            repaint();
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
