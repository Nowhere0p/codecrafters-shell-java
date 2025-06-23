package parser;

import java.util.*;

public class ParsedCommand {
    public String command;
    public List<String> args;
    public Map<Integer, String> outputRedirections = new HashMap<>();

    public static ParsedCommand fromLine(String line) {
        String[] parts = line.trim().split(" ");  // can use "\\s+" represents space only
        ParsedCommand parsedCommand = new ParsedCommand();
        List<String> argsList = new ArrayList<>();
        int i = 0;
        if (parts.length > 0) {
            parsedCommand.command = parts[i++];
        }

        //Arguments and redirections
        while (i < parts.length) {
            String token = parts[i];
            if (token.matches("\\d?>.*") || token.matches(">.*")) {  // "\\d?" -> any num  followed by ">" or just ">" followed by anything
                int fd = 1; //default 
                String target = token;
                if (token.contains(">")) {
                    String[] split = token.split(">", 2);
                    if (!split[0].isEmpty()) fd = Integer.parseInt(split[0]);
                    target = split[1];
                }
                if (target.isEmpty() && i + 1 < parts.length) {
                    target = parts[++i];
                }
                parsedCommand.outputRedirections.put(fd, target);
            } else {
                argsList.add(token);
            }
            i++;
        }
        parsedCommand.args = argsList;
        return parsedCommand;
    }

    @Override
    public String toString() {
        return "Command: " + command + "\nArgs: " + args +
                "\nOutputRedirections: " + outputRedirections;
    }
}
