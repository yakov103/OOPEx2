package GUI;
import api.*;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.SystemColor.text;


public class popMSG extends JFrame implements ActionListener, Runnable{
    DirectedWeightedGraphAlgorithms graph;
    JTextField t_src;
    JTextField t_dest;
    JButton submit;
    JLabel msg_src;
    JLabel msg_dest;
    JLabel space;

    List<Integer> list_of_nodes;
    public List<Integer> getList (){
        return list_of_nodes;
    }

    public popMSG (DirectedWeightedGraphAlgorithms g){
        this.graph= g;
     setSize(400,200);
     msg_src = new JLabel("source : ");
     t_src = new JTextField(16);
     msg_dest = new JLabel("destanation  : ");
     t_dest= new JTextField(16);
     submit = new JButton("Submit");
     submit.addActionListener(this);
     space = new JLabel("      .............");
     JPanel p = new JPanel();
     p.add(msg_src);
     p.add(t_src);
     p.add(space);
     p.add(msg_dest);
     p.add(t_dest);
     p.add(submit);
    this.add(p);
    show();

 }

    @Override
    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit){
            int src = Integer.parseInt(t_src.getText());
            int dest = Integer.parseInt(t_dest.getText());
            String path = "";


            JOptionPane.showConfirmDialog(null, "length of the path is : "+this.graph.shortestPathDist(src,dest));
        notifyAll();
        }

    }
}
