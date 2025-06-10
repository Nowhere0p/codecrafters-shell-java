package Commands;

import java.util.Arrays;

public class EchoCommand implements  Command {
    @Override
    public void execute(ParsedCommand parsedCommand) {
        if(parsedCommand.args.length==0){
            System.out.println();
        }
        else{
            System.out.println(String.join(" ",parsedCommand.args));

        }
    }

    @Override
    public void type() {
        System.out.println("echo is a shell builtin");
    }
}
