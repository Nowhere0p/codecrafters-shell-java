import commands.*;
import server.ShellServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
        private static Map<String, Command> commands;
    public static void main(String[] args) throws Exception {
        commands=new HashMap<>();
        commands.put("exit",new ExitCommand());
        commands.put("echo",new EchoCommand());
        commands.put("type",new TypeCommand(commands));
        ShellServer.start(commands);
    }
}
