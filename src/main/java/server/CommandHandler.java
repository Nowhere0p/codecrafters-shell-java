package server;

import commands.Command;
import parser.ParsedCommand;
import util.CommandUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.*;

public class CommandHandler {
    public static void handle(ParsedCommand parsedCommand, Map<String, Command> commands) throws IOException {

        if(!commands.containsKey(parsedCommand.command)){
            List<String> commandLocations = CommandUtils.checkCommandInPath(parsedCommand.command);
            if(commandLocations.isEmpty()){
                System.out.println(parsedCommand.command + ": command not found");
            }else{
            try{
                var statusCode=runExecutable(parsedCommand,commandLocations);
                // if(statusCode!=0){
                //     System.out.println(parsedCommand.command+": command failed");
                // }
            }
            catch(IOException | InterruptedException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        }
        else{    
            parsedCommand = handleRedirection(parsedCommand);
            commands.get(parsedCommand.command).execute(parsedCommand);
        }
    }

    private static int runExecutable(ParsedCommand parsedCommand, List<String> commandLocations) throws IOException,InterruptedException {

    var executable=commandLocations.getFirst();
    File executabFile=new File(executable);
    if(!executabFile.exists() ||!executabFile.canExecute()){
        System.out.println(parsedCommand.command+": is not executable");
        return 1;
    }
    List<String> tokens = new ArrayList<>(parsedCommand.args);
    Set<String> redirectionOperators = Set.of(">", ">>", "1>", "1>>", "2>", "2>>");
    File outputFile = null;
    File errorFile = null;
    boolean appendOut = false;
    boolean appendErr = false;
    if (tokens.size() >= 2 && redirectionOperators.contains(tokens.get(tokens.size() - 2))) {
        String operator = tokens.get(tokens.size() - 2);
        File logFile = new File(tokens.get(tokens.size() - 1));
        if (operator.startsWith("2")) {
            errorFile = logFile;
            appendErr = operator.endsWith(">>");
        } else {
            outputFile = logFile;
            appendOut = operator.endsWith(">>");
        }
      
        tokens = tokens.subList(0, tokens.size() - 2);
    }

    ProcessBuilder builder = new ProcessBuilder();
    builder.command(executabFile.getName());
    builder.command().addAll(tokens);

    if (outputFile != null) {
        builder.redirectOutput(appendOut ? ProcessBuilder.Redirect.appendTo(outputFile) : ProcessBuilder.Redirect.to(outputFile));
    } else {
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    }
    if (errorFile != null) {
        builder.redirectError(appendErr ? ProcessBuilder.Redirect.appendTo(errorFile) : ProcessBuilder.Redirect.to(errorFile));
    } else {
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
    }
    builder.redirectInput(ProcessBuilder.Redirect.INHERIT);

    var process = builder.start();
    return process.waitFor();
}

    
    
        public static ParsedCommand handleRedirection(ParsedCommand input) throws IOException {
            List<String> tokens = input.args;
            Set<String> redirectionOperators = Set.of(">", ">>", "1>", "1>>", "2>", "2>>");
            if (tokens.size() < 2 || !redirectionOperators.contains(tokens.get(tokens.size() - 2))) {
                return input;
            }
            String operator = tokens.get(tokens.size() - 2);
            if (redirectionOperators.contains(operator)) {
                File logFile = new File(tokens.get(tokens.size() - 1));
                File path = logFile.getParentFile();
                if (path != null && !path.exists()) {
                    path.mkdirs();
                }
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                boolean append = operator.endsWith(">>");
                FileOutputStream fileOutputStream = new FileOutputStream(logFile, append);
                if (operator.startsWith("2")) {
                    System.setErr(new PrintStream(fileOutputStream, true));
                } else if (operator.startsWith("1")) {
                    System.setOut(new PrintStream(fileOutputStream, true));
                } else if (operator.equals(">") || operator.equals(">>")) {
                    System.setOut(new PrintStream(fileOutputStream, true));
                }
                List<String> newArgs = new ArrayList<>(tokens.subList(0, tokens.size() - 2));
                input.args = newArgs;
            }
            return input;
        }
    }
