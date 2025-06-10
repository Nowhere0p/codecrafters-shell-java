package Commands;

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
}
