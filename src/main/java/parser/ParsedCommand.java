package parser;

import java.util.Arrays;
import java.util.List;

public class ParsedCommand {
    public String command;
    public List<String> args;
    public static ParsedCommand fromLine(String line){
        String[] parts=line.split(" ");
        ParsedCommand parsedCommand=new ParsedCommand();
        parsedCommand.command=parts[0];
        parsedCommand.args= Arrays.asList(parts).subList(1, parts.length) ;
        return parsedCommand;
    }
}
