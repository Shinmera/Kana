/**********************\
  file: SetChooser.java
  package: interfaces.graphical
  author: Shinmera
  team: NexT
  license: -
\**********************/

package interfaces.graphical;

import NexT.gui.imported.CheckTreeManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import kana.Category;
import kana.Kana;
import kana.Set;

public class SetChooser extends JDialog implements ActionListener,TreeSelectionListener{
        ArrayList<Set> selected;
        CheckTreeManager treeManager;
        boolean ok = false;
        
        public SetChooser(JFrame f,Kana main){
            super(f,"Choose Sets",true);
            
            selected = new ArrayList<Set>();
            JPanel layout = new JPanel();
            layout.setBorder(new EmptyBorder(10, 10, 10, 10) );
            layout.setLayout(new BorderLayout());
            
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Sets");
            JTree tree = new JTree(top);tree.expandRow(0);
            JScrollPane scroll = new JScrollPane(tree);
            treeManager = new CheckTreeManager(tree);
            tree.addTreeSelectionListener(this);
            
            for(Category cat : main.getCategories()){
                DefaultMutableTreeNode category = new DefaultMutableTreeNode(cat);
                for(Set set : cat.getSets()){
                    //if(set.isActive())selected.add(set.getName());
                    DefaultMutableTreeNode item = new DefaultMutableTreeNode(set);
                    category.add(item);
                }
                top.add(category);
            }
            for(int i=0;i<tree.getRowCount();i++){tree.expandRow(i);}
            
            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancel");
            ok.addActionListener(this);
            cancel.addActionListener(this);
            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons,BoxLayout.LINE_AXIS));
            buttons.add(Box.createHorizontalGlue());
            buttons.add(cancel);
            buttons.add(Box.createRigidArea(new Dimension(5,5)));
            buttons.add(ok);
            
            layout.add(scroll, BorderLayout.CENTER);
            layout.add(buttons, BorderLayout.SOUTH);
            add(layout);
            
            setLocationRelativeTo(null);
            setPreferredSize(new Dimension(300,300));
            pack();
        }
        
        public ArrayList<Set> getSelected(){
            if(ok)return selected;
            else  return null;
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Ok")){
                ok=true;
                setVisible(false);
            }else if(e.getActionCommand().equalsIgnoreCase("Cancel")){
                setVisible(false);
            }
        }

    public void valueChanged(TreeSelectionEvent tse) {
        Set node = (Set)((DefaultMutableTreeNode)tse.getNewLeadSelectionPath().getLastPathComponent()).getUserObject();
        if(selected.contains(node)){
            selected.remove(node);
            node.setActive(false);
        }else{
            selected.add(node);
            node.setActive(true);
        }
    }

    }
