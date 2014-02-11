/**********************\
  file: SetCreator.java
  package: interfaces.graphical
  author: Shinmera
  team: NexT
  license: -
\**********************/

package interfaces.graphical;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import kana.Set;
import kana.Symbol;

public class SetCreator extends JDialog implements ActionListener{
        JTextField name,category;
        JPanel fieldsPanel;
        HashMap<JTextField,JTextField> fields = new HashMap<JTextField,JTextField>();
        boolean ok = false;

        public SetCreator(JFrame f){
            super(f,"Create a Set",true);
            JPanel layout = new JPanel();
            layout.setLayout(new BoxLayout(layout,BoxLayout.PAGE_AXIS));
            layout.setBorder(new EmptyBorder(10, 10, 10, 10) );
            
            name = new JTextField("Name");
            category = new JTextField("Category");
            name.setMaximumSize(new Dimension(100000,20));
            category.setMaximumSize(new Dimension(100000,20));
            
            fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(0,2));
            ((GridLayout)fieldsPanel.getLayout()).setVgap(3);
            ((GridLayout)fieldsPanel.getLayout()).setHgap(3);
            addField();
            addField();
            addField();
            addField();
            addField();
            
            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancel");
            JButton add = new JButton("Add");
            ok.addActionListener(this);
            cancel.addActionListener(this);
            add.addActionListener(this);
            JPanel buttons = new JPanel();
            
            buttons.setLayout(new BoxLayout(buttons,BoxLayout.LINE_AXIS));
            buttons.add(add);
            buttons.add(Box.createRigidArea(new Dimension(10,10)));
            buttons.add(Box.createHorizontalGlue());
            buttons.add(cancel);
            buttons.add(Box.createRigidArea(new Dimension(5,5)));
            buttons.add(ok);
            
            layout.add(name);
            layout.add(category);
            layout.add(fieldsPanel);
            layout.add(Box.createVerticalGlue());
            layout.add(buttons);
            add(layout);
            
            setMinimumSize(new Dimension(250,300));
            setLocationRelativeTo(null);
            pack();
        }
        
        public void addField(){
            JTextField key = new JTextField();
            JTextField val = new JTextField();
            fields.put(key,val);
            ((GridLayout)fieldsPanel.getLayout()).setRows(((GridLayout)fieldsPanel.getLayout()).getRows()+1);
            fieldsPanel.add(key);
            fieldsPanel.add(val);
            validate();
        }
        
        public Set getSet(){
            if(ok){
                HashMap<Symbol,Symbol> map = new HashMap<Symbol,Symbol>();
                for(JTextField key : fields.keySet()){
                    if(key.getText().length()>0&&fields.get(key).getText().length()>0){
                        map.put(new Symbol(key.getText().trim().split(",")),
                                new Symbol(fields.get(key).getText().trim().split(",")));
                    }
                }
                if(map.isEmpty())return null;
                else             return new Set(name.getText(),category.getText(),map);
            }else return null;
        }
        
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand().trim();
            if(button.equals("Ok")){
                ok=true;setVisible(false);
            }else if(button.equals("Cancel")){
                setVisible(false);
            }else if(button.equals("Add")){
                addField();
            }
        }
        
    }
