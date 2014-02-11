/**********************\
  file: Graphical.java
  package: interfaces
  author: Shinmera
  team: NexT
  license: GPL
  
  This file is part of Kana.

    Kana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Kana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Kana.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
 */
 
package interfaces.graphical;

import interfaces.Interface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import kana.Answer;
import kana.Category;
import kana.Kana;
import kana.Set;
import kana.Symbol;
import say.swing.JFontChooser;

public class Graphical extends JFrame implements Interface,ActionListener{
    private Kana main;
    private JTextField input;
    private JLabel show;
    private Font font;
    private JLabel status;
    private JFileChooser fileChooser = new JFileChooser();
    private Flasher flasher;
    
    public Graphical(Kana main){
        System.setProperty("awt.useSystemAAFontSettings","lcd");
        System.setProperty("swing.aatext", "true");
        this.main=main;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Kana.class.getResourceAsStream("/fonts/IPAGothic.ttf"));
            font = font.deriveFont(Font.BOLD,100);
            System.out.println("Loaded font from package.");
        } catch (FontFormatException ex) {
            ex.printStackTrace();
            System.out.println("Failed to load font from resource!");
            font = new Font("IPAGothic",Font.BOLD,100);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to load font from resource!");
            font = new Font("IPAGothic",Font.BOLD,100);
        }
        input = new JTextField("Press enter to start");
        input.addActionListener(this);
        input.setFont(new Font("Arial",Font.PLAIN,18));
        show = new AntialiasedLabel("Welcome");
        show.setFont(font);
        show.setHorizontalAlignment(JLabel.CENTER);
        show.setBackground(Color.WHITE);
        show.setOpaque(true);
        status = new AntialiasedLabel(" ");
        status.setBackground(Color.LIGHT_GRAY);
        status.setOpaque(true);
        status.setMinimumSize(new Dimension(400,30));
        
        add(show,BorderLayout.CENTER);
        add(input,BorderLayout.SOUTH);
        add(status,BorderLayout.NORTH);
        
        JMenuBar bar = new JMenuBar();
        
        JMenu m_file = new JMenu("File");
        JMenuItem i_load = new JMenuItem("Load...");
        JMenuItem i_save = new JMenuItem("Save...");
        JMenuItem i_exit = new JMenuItem("Exit");
        
        JMenu m_edit = new JMenu("Edit");
        JMenuItem i_sets = new JMenuItem("Choose sets...");
        JMenuItem i_add  = new JMenuItem("Add set...");
        JMenuItem i_font = new JMenuItem("Set Font...");
        JMenuItem i_options = new JMenuItem("Options");
        
        JMenu m_help = new JMenu("Help");
        JMenuItem i_help = new JMenuItem("Help");
        JMenuItem i_about = new JMenuItem("About");
        
        i_load.addActionListener(this);
        i_save.addActionListener(this);
        i_exit.addActionListener(this);
        i_sets.addActionListener(this);
        i_add.addActionListener(this);
        i_font.addActionListener(this);
        i_options.addActionListener(this);
        i_help.addActionListener(this);
        i_about.addActionListener(this);
        
        m_file.add(i_load);
        m_file.add(i_save);
        m_file.addSeparator();
        m_file.add(i_exit);
        
        m_edit.add(i_sets);
        m_edit.add(i_add);
        m_edit.addSeparator();
        m_edit.add(i_font);
        m_edit.add(i_options);
        
        m_help.add(i_help);
        m_help.add(i_about);
        
        bar.add(m_file);
        bar.add(m_edit);
        bar.add(m_help);
        
        pack();
        setMinimumSize(new Dimension(400,200));
        setPreferredSize(new Dimension(500,300));
        setJMenuBar(bar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Kana");
        
        input.requestFocus();
        input.requestFocusInWindow();
        flasher = new Flasher();
        flasher.start();
    }
    

    public void initDone() {setVisible(true);}

    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand().trim();
        if(button.equals("Load...")){
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                if(main.load(fileChooser.getSelectedFile()))JOptionPane.showMessageDialog(this, "Loading successful!","Success",JOptionPane.INFORMATION_MESSAGE);
                else                                        JOptionPane.showMessageDialog(this, "Failed to load file!","Failure",JOptionPane.ERROR_MESSAGE);
            }
        }else if(button.equals("Save...")){
            SetChooser chooser = new SetChooser(this,main);
            chooser.setVisible(true);
            ArrayList<Set> sets = chooser.getSelected();
            if(sets!=null){
                if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                    try {
                        File file = fileChooser.getSelectedFile();
                        if(!file.exists())file.createNewFile();
                        PrintWriter writer = new PrintWriter(
                                             new OutputStreamWriter(
                                             new FileOutputStream(file),"UTF-8"));
                    
                        for(Set set : sets){
                            writer.println("BEGIN "+set.getName());
                            writer.println("    active: false");
                            for(Symbol key : set.getSymbols().keySet()){
                                writer.println("    "+key.toString()+": "+set.get(key).toString());
                            }
                            writer.println("END");
                            writer.println();
                        }
                        writer.flush();
                        writer.close();
                        JOptionPane.showMessageDialog(this, "Saving successful!","Success",JOptionPane.INFORMATION_MESSAGE);
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,"Failed to save!","Failed to save!<br />UTF-8 not supported!",JOptionPane.ERROR_MESSAGE);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,"Failed to save!","Failed to save!<br />File not found?",JOptionPane.ERROR_MESSAGE);
                    } catch(IOException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,"Failed to save!","Failed to save!<br />Failed to create file.",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(button.equals("Exit")){
            System.exit(0);
        }else if(button.equals("Choose sets...")){
            SetChooser chooser = new SetChooser(this,main);
            chooser.setVisible(true);
        }else if(button.equals("Add set...")){
            SetCreator creator = new SetCreator(this);
            creator.setVisible(true);
            Set set = creator.getSet();
            if(set!=null){
                main.addSet(set);
            }
        }else if(button.equals("Set Font...")){
            JFontChooser fontChooser = new JFontChooser();
            fontChooser.setSelectedFont(font);
            int result = fontChooser.showDialog(this);
            if (result == JFontChooser.OK_OPTION){
                font = fontChooser.getSelectedFont(); 
                show.setFont(font);
            }
        }else if(button.equals("Options")){
            OptionsDialog options = new OptionsDialog(this,main);
            options.setVisible(true);
        }else if(button.equals("Help")){
            //TODO: Help Page
            JOptionPane.showMessageDialog(this, "I'm too lazy to write a help page right now, so fuck off.","Help",JOptionPane.INFORMATION_MESSAGE);
        }else if(button.equals("About")){
            JOptionPane.showMessageDialog(this, "<html>Simple Kana Trainer<br />"
                                              + "Written by Nicolas Hafner<br />"
                                              + "©2012 TymoonNET/NexT</html>","About Kana",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getSource().equals(input)){
            Answer a;
            if(!main.isStarted()){
                main.start();
                input.setText("");
            }
            a = main.answer(input.getText());
            input.setText("");
            switch(a.getAnswer()){
                case Answer.ANSWER_ERROR:   show.setText("Error!");break;
                case Answer.ANSWER_NO_MORE: end();break;
                case Answer.ANSWER_NEXT:    show.setText(a.getCorrect().getRandom());break;
                case Answer.ANSWER_RIGHT:   show.setText(a.getCorrect().getRandom());flasher.flash(Color.GREEN,100);break;
                case Answer.ANSWER_WRONG: 
                    flasher.flash(Color.RED,100);
                    if(!a.getCorrect().isEmpty())input.setText(a.getCorrect().getRandom());break;
            }
        }
    }
    
    public void end(){
        show.setText("Done!");
        input.setText("Press enter to start");
    }
    
    public void update(){
        status.setText("<html> Status:"
                        + " Total: "+main.getTotalAmount()
                        + " Correct: "+main.getRightAmount()
                        + " Wrong: "+main.getWrongAmount()
                        + " Time: "+main.getTimePast()
                       +"</html>");
    }
    
    class Flasher extends Thread{
        private int timer = 0;
        private Color color = Color.RED;
        
        public void flash(Color color,int duration){
            timer=duration;this.color=color;
        }
        
        public void run(){
            while(!isInterrupted()){
                while(timer>0){
                    timer--;
                    show.setBackground(new Color(255-(255-color.getRed())*timer/100,
                                                 255-(255-color.getGreen())*timer/100,
                                                 255-(255-color.getBlue())*timer/100));
                    try{Thread.sleep(10);}catch(Exception e){/*Nobody cares*/}
                }
                try{Thread.sleep(100);}catch(Exception e){/*Nobody cares*/}
            }
        }
    }
}
class AntialiasedLabel extends JLabel{
    public AntialiasedLabel(){super();}
    public AntialiasedLabel(String s){super(s);}
    
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
    }
}