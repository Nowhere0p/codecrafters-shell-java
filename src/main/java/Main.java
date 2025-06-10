import Commands.Command;
import Commands.ExitCommand;
import Commands.ParsedCommand;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
        private static Map<String, Command> supportedCommands;
    public static void main(String[] args) throws Exception {
        supportedCommands=new HashMap<>();
        supportedCommands.put("exit",new ExitCommand());
         while (true){
            ParsedCommand command=readCommand();
            handleCommand(command);
            System.out.println(command.command+": command not found");
         }
    }

    private static ParsedCommand readCommand() {
        System.out.print("$");
        Scanner scanner=new Scanner(System.in);
        String line=scanner.nextLine();
        String[] parts=line.split(" ");
        ParsedCommand parsedCommand=new ParsedCommand();
        parsedCommand.command=parts[0];
        parsedCommand.args= Arrays.copyOfRange(parts,1,parts.length);
        return parsedCommand;
    }

    public  static  void handleCommand(ParsedCommand parsedCommand){
        if(supportedCommands.containsKey(parsedCommand.command)){
            supportedCommands.get(parsedCommand.command).execute(parsedCommand.args);
        }
//
    }
}
