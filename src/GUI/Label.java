package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Label extends JLabel implements ActionListener {
    JTextField tf;
    JLabel l;
    JButton b ;
        public Label (){
            tf= new JTextField();
            tf.setBounds(50,50,150,20);
            l = new JLabel();
            l.setBounds(50 , 100, 250, 20);
            l.setText("insert Url ");

            b= new JButton("text ");
            b.setBounds(50,150,95,30);
            b.addActionListener(this);
            this.add(b);
            this.add(tf);
            this.add(l);
            setSize(400,400);
            setLayout(null);
            setVisible(true);

        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
