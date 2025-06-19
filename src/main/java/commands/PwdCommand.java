package commands;

import java.nio.file.Paths;

import parser.ParsedCommand;

public class PwdCommand implements Command{

    @Override
    public void execute(ParsedCommand parsedCommand) {
        System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public void type() {
         System.out.println("pwd is a shell builtin");
    }
    
}
