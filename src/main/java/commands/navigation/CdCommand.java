package commands.navigation;

import java.io.File;
import java.nio.file.Path;

import commands.Command;
import parser.ParsedCommand;
import server.ShellServer;

public class CdCommand implements Command{
    
  @Override
public void execute(ParsedCommand parsedCommand) {
    String target;
    if (parsedCommand.args.isEmpty() || parsedCommand.args.get(0).equals("~")) {
        target = System.getProperty("user.home");
    } else {
        target = parsedCommand.args.get(0);
    }

    Path newPath = ShellServer.currentPath.resolve(target).normalize();

    File dir = newPath.toFile();
    if (dir.exists() && dir.isDirectory()) {
        ShellServer.currentPath = newPath.toAbsolutePath();
    } else {
        System.out.println(parsedCommand.command + ": " + target + ": No such file or directory");
    }
}


    @Override
    public void type() {
      System.out.println("cd is a shell builtin");
    }
    
}
