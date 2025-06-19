package commands.navigation;

import java.io.File;


import commands.Command;
import parser.ParsedCommand;

public class CdCommand implements Command{
    
    @Override
    public void execute(ParsedCommand parsedCommand) {
       File file= new File(parsedCommand.args.get(0));
       if(file.exists()){
        System.setProperty("user.dir", parsedCommand.args.get(0));
       }
       else{
        System.out.println(parsedCommand.command+": "+parsedCommand.args.get(0)+": No such file or directory");
       }
    }

    @Override
    public void type() {
      System.out.println("cd is a shell builtin");
    }
    
}
