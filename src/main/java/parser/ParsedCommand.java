package parser;

import java.util.*;

public class ParsedCommand {
    public String command;
    public List<String> args;
    public Map<Integer, ActionToPath> outputRedirections = new HashMap<>();
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
    List<String> argsList = new ArrayList<>();

    for (int i = 1; i < tokens.size(); i++) {
        String token = tokens.get(i);

        if (token.matches("\\d?>.*") || token.matches(">.*")) {
            int fd =1;
            String target = token;
            boolean appendAction=false;
            if (token.contains(">>")) {
                String[] split = token.split(">>", 2);
                if (!split[0].isEmpty()) fd = Integer.parseInt(split[0]);
                target = split[1];
                appendAction=true;
            }
            else{
                 String[] split = token.split(">", 2);
                if (!split[0].isEmpty()) fd = Integer.parseInt(split[0]);
                target = split[1];
            }
            if (target.isEmpty() && i + 1 < tokens.size()) {
                target = tokens.get(++i);
            }
            parsedCommand.outputRedirections.put(fd,new ActionToPath(appendAction, target) );
        } else {
            argsList.add(token);
        }
    }

    parsedCommand.args = argsList;
    return parsedCommand;

    }

    @Override
    public String toString() {
        return "Command: " + command + "\nArgs: " + args +
                "\nOutputRedirections: " + outputRedirections;
    }
}
