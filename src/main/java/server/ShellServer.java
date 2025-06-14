package server;



import commands.Command;
import parser.ParsedCommand;

import java.util.Map;
import java.util.Scanner;

public class ShellServer {
    public static void start(Map<String, Command> commands){
        while (true){
            System.out.print("$ ");
            Scanner sc=new Scanner(System.in);
            String line=sc.nextLine();
            ParsedCommand command=ParsedCommand.fromLine(line);
            CommandHandler.handle(command,commands);
        }
    }
}
