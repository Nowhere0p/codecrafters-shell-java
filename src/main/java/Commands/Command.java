package Commands;

public interface Command {

    public void execute(String[] args);
}
class StatusReport{
    public boolean isSuccess;
    public  int exitCode;
}