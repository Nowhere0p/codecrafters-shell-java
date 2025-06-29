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
