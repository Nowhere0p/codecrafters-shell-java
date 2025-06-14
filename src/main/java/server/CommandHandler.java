package server;

import commands.Command;
import parser.ParsedCommand;

import java.util.Map;

public class CommandHandler {
    public static void handle(ParsedCommand parsedCommand, Map<String, Command> commands) {

        if(commands.containsKey(parsedCommand.command)){
            commands.get(parsedCommand.command).execute(parsedCommand);
        }else {
            System.out.println(parsedCommand.command + ": command not found");
        }
    }
}
