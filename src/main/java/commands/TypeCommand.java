package commands;

import parser.ParsedCommand;
import util.CommandUtils;

import java.sql.Struct;
import java.util.List;
import java.util.Map;

public class TypeCommand implements Command  {
    Map<String,Command> commandMap;
    public  TypeCommand(Map<String,Command> commands) {
        commandMap=commands;
    }
    @Override
    public void execute(ParsedCommand parsedCommand) {
        for(String command :parsedCommand.args){
            if(commandMap.containsKey(command)){
                commandMap.get(command).type();
            }else {
                System.out.println(command+": not found");
            }
        }
    }

    @Override
    public void type() {
        System.out.println("type is a shell builtin");
    }

    private  boolean checkInPath(String arg){
        List<String> paths= CommandUtils.checkCommandInPath(arg);
        if(!paths.isEmpty()){
            for(String path : paths){
                System.out.println(arg+" is "+path);
            }
            return  true;
        }
       return  false;
    }
}
