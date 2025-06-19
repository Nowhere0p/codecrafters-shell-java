package commands;

import java.nio.file.Paths;

import parser.ParsedCommand;
import server.ShellServer;

public class PwdCommand implements Command{

    @Override
    public void execute(ParsedCommand parsedCommand) {
        System.out.println(ShellServer.currentPath.toAbsolutePath());
    }

    @Override
    public void type() {
         System.out.println("pwd is a shell builtin");
    }
    
}
