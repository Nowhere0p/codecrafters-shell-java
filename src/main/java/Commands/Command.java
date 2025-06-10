package Commands;

public interface Command {

    public void execute(ParsedCommand parsedCommand);
    public void type();
}
class StatusReport{
    public boolean isSuccess;
    public  int exitCode;
}