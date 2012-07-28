/**********************\
  file: SetChooser.java
  package: interfaces.graphical
  author: Shinmera
  team: NexT
  license: -
\**********************/

package interfaces.graphical;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import kana.Kana;
import kana.Set;

public class SetChooser extends JDialog implements ActionListener{
        ArrayList<String> selected = new ArrayList<String>();
        boolean ok = false;
        
        public SetChooser(JFrame f,Kana main){
            super(f,"Choose Sets",true);
            JPanel layout = new JPanel();
            layout.setLayout(new BoxLayout(layout,BoxLayout.PAGE_AXIS));
            layout.setBorder(new EmptyBorder(10, 10, 10, 10) );
            
            for(Set set : main.getSets()){
                JCheckBox box = new JCheckBox(set.getName());
                if(set.isActive())selected.add(set.getName());
                box.setSelected(set.isActive());
                box.addActionListener(this);
                layout.add(box);
            }
            
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
            
            layout.add(buttons);
            add(layout);
            
            setLocationRelativeTo(null);
            pack();
        }
        
        public ArrayList<String> getSelected(){
            if(ok)return selected;
            else  return null;
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Ok")){
                ok=true;
                setVisible(false);
            }else if(e.getActionCommand().equalsIgnoreCase("Cancel")){
                setVisible(false);
            }else{
                if(selected.contains(e.getActionCommand()))selected.remove(e.getActionCommand());
                else                                       selected.add(e.getActionCommand());
            }
        }

    }
