/**********************\
  file: Set.java
  package: kana
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

package kana;

import java.util.HashMap;

public class Set {
    private HashMap<Symbol,Symbol> symbols;
    private String name;
    private String category;
    private boolean active = false;
    
    public Set(String name,String category){this(name,category,new HashMap<Symbol,Symbol>());}
    public Set(String name,String category,HashMap<Symbol,Symbol> symbols){
        this.name=name;
        this.category=category;
        this.symbols=symbols;
    }
    
    public void setActive(boolean active){this.active=active;}
    public void setSymbols(HashMap<Symbol,Symbol> symbols){this.symbols=symbols;}
    
    public void add(String a,String b){
        Symbol sym = get(a);
        if(sym==null){
            symbols.put(new Symbol(a),new Symbol(b));
        }else{
            symbols.get(sym).add(b);
        }
    }
    public void add(Symbol a,String b){
        if(symbols.containsKey(a))symbols.get(a).add(b);
        else                      symbols.put(a,new Symbol(b));
    }
    public void add(Symbol a,Symbol b){
        symbols.put(a,b);
    }
    public void remove(Symbol a){symbols.remove(a);}
    public void remove(String a){symbols.remove(get(a));}
    
    public boolean isKey(Symbol key){return symbols.containsKey(key);}
    public boolean isKey(String key){return (get(key)==null)? false : true;}
    public boolean isValueOf(Symbol key,String val){return symbols.get(key).contains(val);}
    public boolean isValueOf(String key,String val){
        Symbol sym = get(key);if(sym==null)return false;
        return sym.contains(val);
    }
    
    public Symbol get(Symbol symbol){return symbols.get(symbol);}
    public Symbol get(String symbol){
        for(Symbol sym : symbols.keySet()){
            if(sym.contains(symbol))return sym;
        }
        return null;
    }
    public String getName(){return name;}
    public String getCategory(){return category;}
    public HashMap<Symbol,Symbol> getSymbols(){return symbols;}
    public boolean isActive(){return active;}
    public int size(){return symbols.size();}
    public String toString(){return getName();}
}
