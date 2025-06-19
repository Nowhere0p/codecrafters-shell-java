import commands.*;
import commands.navigation.CdCommand;
import server.ShellServer;
import java.util.HashMap;
import java.util.Map;

public class Main {
        private static Map<String, Command> commands;
    public static void main(String[] args) throws Exception {
        commands=new HashMap<>();
        commands.put("exit",new ExitCommand());
        commands.put("echo",new EchoCommand());
        commands.put("type",new TypeCommand(commands));
        commands.put("pwd", new PwdCommand());
        commands.put("pwd", new CdCommand());
        ShellServer.start(commands);
    }
}
