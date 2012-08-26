package NexT.util;

public class Argument {
    private char name;
    private String longName;
    private String value;
    private String defaultValue;
    private String description;
    
    public Argument(char name){                                                         this(name,null,        null,       null,    null);}
    public Argument(char name,String defaultValue){                                     this(name,defaultValue,null,       null,    null);}
    public Argument(char name,String defaultValue,String description){                  this(name,defaultValue,description,null,    null);}
    public Argument(char name,String defaultValue,String description,String longName){  this(name,defaultValue,description,longName,null);}
    public Argument(char name,String defaultValue,String description,String longName,String value){
        this.name=name;this.defaultValue=defaultValue;this.description=description;this.longName=longName;this.value=value;
    }
    
    public void setLongName(String longName){           this.longName=longName;}
    public void setDefaultValue(String defaultValue){   this.defaultValue=defaultValue;}
    public void setDescription(String description){     this.description=description;}
    public void set(String value){                      this.value=value;}
    public String get(){                                return value;}
    public String getDescription(){                     return description;}
    public String getDefaultValue(){                    return defaultValue;}
    public String getLongName(){                        return longName;}
    public char getName(){                              return name;}
}
