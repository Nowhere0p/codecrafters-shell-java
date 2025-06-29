package parser;

import java.util.*;

public class ParsedCommand {
    public String command;
    public List<String> args;
    public ParsedCommand(){};
    public ParsedCommand(String command,List<String> args){
        this.command=command;
        this.args=args;
    };
    public ParsedCommand(String command){
        this.command=command;
        this.args=new ArrayList<>();
    };



    public static ParsedCommand fromLine(String line) {
     if(line.isEmpty() || line==null) return new ParsedCommand();
     boolean inQuotes=false;
     List<String> tokens=new ArrayList<>();
     StringBuilder sb=new StringBuilder();  //   for processing too much string values
     char quoteChar='\0';
     Set<Character> quoteSymbolSet=Set.of('\'','\"');
     for(int i=0;i<line.length();i++){
        char current=line.charAt(i);
        if(current=='\\'){  
            if(i+1<line.length()){
                char nextChar=line.charAt(i+1);
                if(inQuotes){
                    if(nextChar==quoteChar || nextChar=='\\'){  //more special characters can be added, but not checked by codecrafters so hehehe
                        sb.append(nextChar);
                        i++;
                    }
                    else{
                        sb.append(current);
                    }
                }
                else{
                    sb.append(nextChar);
                    i++;
                }
                
            }
        }
        else if(inQuotes){
            if(current==quoteChar){
                inQuotes=false;
            }
            else{
                sb.append(current);
            }
        }
        else if(quoteSymbolSet.contains(current)){
            inQuotes=true;
            quoteChar=current;
        }
        else if(current==' '){
            if(sb.length()>0){
                tokens.add(sb.toString());
                sb.setLength(0);
            }
        }
        else{
            sb.append(current);

        }
     }
     //process the last token from string builder bcs it was not ending with  ' ' to meet condition on line 63

    if(sb.length()>0)   tokens.add(sb.toString());
    sb.setLength(0);
    if (tokens.isEmpty()) return new ParsedCommand();

    ParsedCommand parsedCommand = new ParsedCommand();
    parsedCommand.command = tokens.get(0);


    parsedCommand.args = tokens.subList(1,tokens.size());
    return parsedCommand;

    }

}
