/**********************\
  file: OptionsDialog.java
  package: interfaces.graphical
  author: Shinmera
  team: NexT
  license: -
\**********************/

package interfaces.graphical;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import kana.Kana;

public class OptionsDialog extends JDialog implements ActionListener{
        JCheckBox loop,repeat;
        JTextField attempts;
        private Kana main;
        
        public OptionsDialog(JFrame f,Kana main){
            super(f,"Options",true);
            this.main=main;
            JPanel layout = new JPanel();
            layout.setLayout(new BoxLayout(layout,BoxLayout.PAGE_AXIS));
            layout.setBorder(new EmptyBorder(10, 10, 10, 10) );
            
            //TODO: Add Options
            loop = new JCheckBox("Repeat endlessly (type stop to end)");
            loop.setSelected(main.isLooping());
            repeat = new JCheckBox("Repeat wrong answers");
            repeat.setSelected(main.isRepeatingWrong());
            attempts = new JTextField(""+main.getMaxAttempts());
            attempts.setMaximumSize(new Dimension(50,20));
            JLabel l_attempts = new JLabel("Maximum Attempts");
            
            JPanel attemptsPanel = new JPanel();
            attemptsPanel.setLayout(new BoxLayout(attemptsPanel,BoxLayout.LINE_AXIS));
            attemptsPanel.add(attempts);
            attemptsPanel.add(Box.createRigidArea(new Dimension(5,5)));
            attemptsPanel.add(l_attempts);
            
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
            
            layout.add(loop);
            layout.add(repeat);
            layout.add(attemptsPanel);
            layout.add(Box.createVerticalGlue());
            layout.add(buttons);
            
            add(layout);
            
            setLocationRelativeTo(null);
            pack();
        }
        
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand().trim();
            if(button.equals("Ok")){
                //Save shit
                main.setLooping(loop.isSelected());
                main.setRepeatWrong(repeat.isSelected());
                main.setMaxAttempts(Integer.parseInt(attempts.getText()));
                setVisible(false);
            }else if(button.equals("Cancel")){
                setVisible(false);
            }
        }
        
    }