package Commands;

public interface Command {

    public void execute(ParsedCommand parsedCommand);
}
class StatusReport{
    public boolean isSuccess;
    public  int exitCode;
}