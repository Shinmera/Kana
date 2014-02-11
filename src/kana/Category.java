/**********************\
  file: Category.java
  package: kana
  author: Shinmera
  team: NexT
  license: -
\**********************/

package kana;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Set> sets;
    
    public Category(String name){this(name,new ArrayList<Set>());}
    public Category(String name,ArrayList<Set> sets){
        this.name=name;this.sets=sets;
    }
    
    public void setSets(ArrayList<Set> sets){this.sets=sets;}
    public void addSet(Set set){if(!sets.contains(set))sets.add(set);}
    public void removeSet(Set set){if(sets.contains(set))sets.remove(set);}
    
    public String getName(){return name;}
    public Set[] getSets(){return sets.toArray(new Set[sets.size()]);}
    public String toString(){return getName();}
}
