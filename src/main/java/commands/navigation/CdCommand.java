package commands.navigation;

import java.io.File;


import commands.Command;
import parser.ParsedCommand;
import server.ShellServer;

public class CdCommand implements Command{
    
    @Override
    public void execute(ParsedCommand parsedCommand) {
       File file= new File(parsedCommand.args.get(0));
       if(file.exists()){
        var newPath=ShellServer.currentPath.resolve(parsedCommand.args.get(0)).normalize();
        if(newPath.toFile().exists()){
            ShellServer.currentPath=newPath;
        }
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
