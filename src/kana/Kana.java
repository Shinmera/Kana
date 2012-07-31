/**********************\
  file: Kana.java
  package: kana
  author: Shinmera
  team: NexT
  license: GPL
  version: 1.0.5
  
  Copyright (C) 2012 Nicolas Hafner / TymoonNexT
 
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
 
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
 */
//TODO: introduce logger
package kana;

import interfaces.Console;
import interfaces.Interface;
import interfaces.graphical.Graphical;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Kana{
    public static final Random random = new Random();
    private Interface UI;
    private TreeMap<String,Set> sets;
    private Timer timer;
    private boolean started = false;
    private boolean loop = false,repeatWrong = true;
    private int maxAttempts = 3,attempts=0;
    private Set total,wrong,right;
    private Iterator<Symbol> currentKeys;
    private Symbol current;
    
    public static void main(String[] args){
        List<String> argss = Arrays.asList(args);
        Kana main = new Kana(argss);
    }
    
    public Kana(List<String> args){
        sets = new TreeMap<String,Set>();
        timer = new Timer();
        if(args.contains("nogui")) UI = new Console(this);
        else                       UI = new Graphical(this);
        load("/sets/hiragana");
        load("/sets/katakana");
        timer.start();
    }
    
    public boolean load(File f){
        try{
            return load(new FileInputStream(f));
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean load(String resource){
        InputStream res = Kana.class.getResourceAsStream(resource);
        if(res!=null)return load(res);
        else         return false;
    }
    
    public boolean load(InputStream s){
        try {
            BufferedReader buf = new BufferedReader(
                                 new InputStreamReader(s,Charset.forName("UTF-8")));
            HashMap<Symbol,Symbol> map = null;
            String line,name="???";
            while((line = buf.readLine())!=null){
                line = line.trim();
                
                if(line.startsWith("BEGIN ")){
                    map = new HashMap<Symbol,Symbol>();
                    name = line.substring(5).trim();
                }
                
                if(line.contains(":")&&!line.startsWith("#")){
                    String[] varkey = line.split(":");
                    map.put(new Symbol(varkey[0].split(",")),
                            new Symbol(varkey[1].split(",")));
                }
                
                if(line.equals("END")){
                    Set set = new Set(name,map);
                    if((set.isKey("active"))&&(set.get("active").contains("true"))) set.setActive(true);
                    else                                                           set.setActive(false);
                    if(set.isKey("active"))set.remove("active");
                    addSet(set);
                }
            }
            buf.close();
            System.out.println("Currently "+sets.size()+" sets loaded.");
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void start(){
        System.out.println("Starting...");
        
        generateNewSet();
        
        started=true;
        timer.setRunning(true);
        attempts=0;
    }
    
    public void end(){
        started=false;
        timer.setRunning(false);
        current=null;
        currentKeys=null;
    }
    
    public Answer answer(String answer){
        if(!started)return Answer.ERROR;
        if(answer.trim().equalsIgnoreCase("stop")){
            end();
            return Answer.NO_MORE;
        }else if(!current.is(answer)){
            attempts++;
            if(attempts>=maxAttempts)
                return new Answer(Answer.ANSWER_WRONG,current);
            else
                return new Answer(Answer.ANSWER_WRONG);
        }else{ 
            int answerCode;
            if(attempts<maxAttempts){
                right.add(current,total.get(current));
                answerCode = Answer.ANSWER_RIGHT;
            }else{
                wrong.add(current,total.get(current));
                answerCode = Answer.ANSWER_NEXT;
            }
            if(currentKeys.hasNext()){
                if(answer.isEmpty()){
                    current = currentKeys.next();
                    return new Answer(Answer.ANSWER_NEXT,total.get(current));
                }else{
                    attempts=0;
                    current = currentKeys.next();
                    return new Answer(answerCode,total.get(current));
                }
            }else{
                if(loop){
                    generateNewSet();
                    return answer("");
                }else if(repeatWrong&&wrong.size()>0){
                    generateNewSet(wrong.getSymbols());
                    return answer("");
                }else{
                    return answer("stop");
                }
            }
        }
    }
    
    public void generateNewSet(HashMap<Symbol,Symbol> map){
        List<Symbol> keys = new ArrayList(map.keySet());
        Collections.shuffle(keys);
        current = new Symbol("");
        total = new Set("all",map);
        right = new Set("right");
        wrong = new Set("wrong");
        currentKeys = keys.iterator();
    }
    
    public void generateNewSet(){
        HashMap<Symbol,Symbol> map = new HashMap<Symbol,Symbol>();
        for(String name : sets.keySet()){
            if(sets.get(name).isActive()){
                map.putAll(sets.get(name).getSymbols());
            }
        }
        generateNewSet(map);
    }
    
    
    public void addSet(Set set){
        System.out.println("Added set '"+set.getName()+"'"+((set.isActive())?"(active)":"")+", contains "+set.getSymbols().size()+" symbols.");
        sets.put(set.getName(),set);
    }
    public void setLooping(boolean loop){this.loop=loop;}
    public void setRepeatWrong(boolean repeat){repeatWrong=repeat;}
    public void setMaxAttempts(int attempts){maxAttempts=attempts;}
    
    public Set[] getSets(){return sets.values().toArray(new Set[sets.size()]);}
    public int getTotalAmount(){return total.size();}
    public int getRightAmount(){return right.size();}
    public int getWrongAmount(){return wrong.size();}
    public int getTimePast(){return timer.getTimePast();}
    public int getMaxAttempts(){return maxAttempts;}
    public boolean isLooping(){return loop;}
    public boolean isStarted(){return started;}
    public boolean isRepeatingWrong(){return repeatWrong;}

    class Timer extends Thread{
        private int secs;
        private boolean running = false;

        public void run(){
            while(!isInterrupted()){
                secs = 0;
                while(running){
                    UI.update();
                    secs++;
                    try{Thread.sleep(1000);}catch(Exception e){/*Whatever*/}
                }
                try{Thread.sleep(200);}catch(Exception e){/*Whatever*/}
            }
        }

        public void setRunning(boolean running){this.running=running;}
        public int getTimePast(){return secs;}
    }
}