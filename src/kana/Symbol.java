/**********************\
  file: Symbol.java
  package: kana
  author: Shinmera
  team: NexT
  license: -
\**********************/

package kana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Symbol {
    ArrayList<String> sym;
    
    public Symbol(){sym = new ArrayList<String>();}
    public Symbol(String symbol){this();add(symbol);}
    public Symbol(String[] symbols){this(Arrays.asList(symbols));}
    public Symbol(List<String> symbols){
        this();for(String symbol : symbols){add(symbol);}
    }
    
    public void add(String symbol){
        symbol = symbol.trim().toLowerCase();
        if(!sym.contains(symbol))sym.add(symbol);}
    public void addAll(String[] symbols){addAll(Arrays.asList(symbols));}
    public void addAll(Symbol symbols){addAll(symbols.getSymbols());}
    public void addAll(List<String> symbols){
        for(String symbol : symbols){add(symbol);}}
    public void remove(String symbol){if(sym.contains(symbol))sym.remove(symbol);}
    public void clear(){sym.clear();}
    
    public boolean isEmpty(){return sym.isEmpty();}
    public boolean is(String symbol){return sym.contains(symbol.trim().toLowerCase());}
    public boolean contains(String symbol){return is(symbol);}
    
    public String get(int n){return sym.get(n);}
    public String get(){return sym.get(0);}
    public String getRandom(){return sym.get(Kana.random.nextInt(sym.size()));}
    public ArrayList<String> getSymbols(){return sym;}
    
    public String toString(){
        String ret = "";
        for(String s : sym){ret=ret+","+s;}
        return ret.substring(1);
    }
}
