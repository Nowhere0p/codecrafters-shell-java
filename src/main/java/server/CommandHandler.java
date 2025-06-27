package server;

import commands.Command;
import parser.ParsedCommand;
import util.CommandUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommandHandler {
    public static void handle(ParsedCommand parsedCommand, Map<String, Command> commands) throws IOException {
        parsedCommand = handleRedirection(parsedCommand);

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
    
        public static ParsedCommand handleRedirection(ParsedCommand input) throws IOException {
            if (!input.outputRedirections.isEmpty()) {
                int fd = input.outputRedirections.keySet().iterator().next();
                String outputPathStr=input.outputRedirections.get(fd).path;
                Path logPath = Paths.get(outputPathStr);
                Path parentDir = logPath.getParent();
                if (parentDir != null && !Files.exists(parentDir)) {
                    Files.createDirectories(parentDir);
                }
                if (Files.exists(logPath))  { 
                  if(!input.outputRedirections.get(fd).toAppend){
                    Files.delete(logPath);
                }
                }
                else{
                Files.createFile(logPath);
                }
                setStream(fd,logPath);
                
                return input;
            } 
            else {
                return input;
            }
        }
        private static void setStream(int fd,Path path) throws IOException{
            switch (fd) {
                case 1: 
                    System.setOut(new PrintStream(Files.newOutputStream(path)));
                    break;
                case 2:
                    System.setErr(new PrintStream(Files.newOutputStream(path)));
                    break;
                default:
                    break;
            }
        }
}
