package server;

import commands.Command;
import parser.ParsedCommand;
import util.CommandUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommandHandler {
    public static void handle(ParsedCommand parsedCommand, Map<String, Command> commands) {

        if(commands.containsKey(parsedCommand.command)){
            commands.get(parsedCommand.command).execute(parsedCommand);
        }else {
            List<String> commandLocations = CommandUtils.checkCommandInPath(parsedCommand.command);
            if(commandLocations.isEmpty()){
                System.out.println(parsedCommand.command + ": command not found");
            }else{
            try{
                var statusCode=runExecutable(parsedCommand,commandLocations);
                if(statusCode!=0){
                    System.out.println(parsedCommand.command+": command failed");
                }
            }
            catch(IOException | InterruptedException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        }
    }

    private static int runExecutable(ParsedCommand parsedCommand, List<String> commandLocations) throws IOException,InterruptedException {
      if(commandLocations.size()>1){
        System.out.println("Multiple executables found");
      }
        var executable=commandLocations.getFirst();
        File executabFile=new File(executable);
        if(!executabFile.exists() ||!executabFile.canExecute()){
            System.out.println(parsedCommand.command+": is not executable");
           return 1;
        }
        ProcessBuilder builder=new ProcessBuilder(executabFile.getName());
        builder.command().addAll(parsedCommand.args);
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        var process=builder.start();
        return process.waitFor();

    }
}
