package NexT.util;

import java.util.TreeMap;

public class ArgsParser {
    private TreeMap<String,Argument> args;
    
    public static void main(String[] args){
        ArgsParser a = new ArgsParser();
        a.addArgument(new Argument('h',"true","Print out help and stuff.","--help"));
        a.addArgument(new Argument(' ',"true","Run in console mode without GUI.","--nogui"));
        a.addArgument(new Argument('c',"true","Show configuration options."));
        a.addArgument(new Argument('v',"true","Run with VSYNC","--vsync"));
        System.out.println(a.getHelp(50));
        String test = "-vc --nogui";
        System.out.println("TESTING WITH: "+test);
        a.parse(test);
    }
    
    public ArgsParser(){this(new TreeMap<String,Argument>());}
    public ArgsParser(TreeMap<String,Argument> args){this.args=args;}
    
    public void parse(String str){
        int pointer = 0;
        boolean inString = false;
        while(pointer>=0){
            int nextString = str.indexOf('"',pointer+1);
            pointer = str.indexOf('-',pointer+1);
            if(nextString!=-1&&inString)                     {pointer=nextString;inString=false;}
            if(nextString!=-1&&nextString<pointer&&!inString){pointer=nextString;inString=true;}
            
        }
    }
    
    public String getHelp(int maxWidth){
        if(maxWidth<50)maxWidth=50;
        String ret = "Argument list: ";
        //Measure longest name:
        int longestNameLength = 0;
        for(String key : args.keySet()){
            if((args.get(key).getLongName()!=null)&&(args.get(key).getLongName().length()>longestNameLength))longestNameLength=args.get(key).getLongName().length();
        }
        //Process
        for(String key : args.keySet()){
            Argument arg = args.get(key);
                                                                        ret+="\n  ";
            if(arg.getName()==' ')                                        ret+="  ";
            else                                                          ret+="-"+arg.getName();
                                                                        ret+="  ";
            if((arg.getLongName()!=null)&&(!arg.getLongName().isEmpty())) ret+=arg.getLongName()+new String(new char[longestNameLength-arg.getLongName().length()]).replace("\0"," ");
            else                                                          ret+=new String(new char[longestNameLength]).replace("\0", " ");
                                                                        ret+="  ";
            if(arg.getDescription()!=null){
                String[] descriptionLines = arg.getDescription().split("(?<=\\G.{"+(maxWidth-longestNameLength-8)+"})");
                for(String line : descriptionLines){
                    ret+=line+"\n"+new String(new char[longestNameLength+8]).replace("\0", " ");
                }
            }
        }
        
        return ret;
    }
    
    public void addArgument(Argument argument){
        if(argument.getName()==' '){
            if(argument.getLongName()==null||argument.getLongName().isEmpty()){
                throw new RuntimeException(new Exception("No name or longName set! Cannot add argument."));
            }
            args.put(argument.getLongName(), argument);
        }else{
            args.put(argument.getName()+"",argument);
        }
    }
    public void removeArgument(char argument){args.remove(argument+"");}
    public void removeArgument(String argument){args.remove(argument);}
    public Argument getArgument(char argument){return args.get(argument+"");}
    public Argument getArgument(String argument){return args.get(argument);}
    public String get(char argument){return args.get(argument+"").get();}
    public String get(String argument){return args.get(argument).get();}
}
