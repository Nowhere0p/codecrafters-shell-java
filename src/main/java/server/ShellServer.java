package server;



import commands.Command;
import parser.ParsedCommand;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;


public class ShellServer {
    public static Path currentPath=Paths.get("").toAbsolutePath();
     public static  PrintStream stdout=System.out;   
    public static PrintStream strerr=System.err;

    public static void start(Map<String, Command> commands){
        try{
        while (true){
            System.out.print("$ ");
            Scanner sc=new Scanner(System.in);
            String line=sc.nextLine();
            ParsedCommand command=ParsedCommand.fromLine(line);
           try{ CommandHandler.handle(command,commands);}
           catch(IOException ex){
            System.out.println("failed to handle command");
            ex.getLocalizedMessage();
            sc.close();
           }
            System.setOut(stdout);
            System.setErr(strerr);
        }
    }catch(RuntimeException rex){
        System.out.println("failed to start server");
        rex.printStackTrace();
    }
    }
}
