package commands;

import parser.ParsedCommand;

import java.util.List;

public class ExitCommand  implements Command{

    @Override
    public void execute(ParsedCommand parsedCommand) {
     StatusReport statusReport= checkArgs(parsedCommand.args);
     if(statusReport.isSuccess){
         System.exit(statusReport.exitCode);
     }
    }

    @Override
    public void type() {
        System.out.println("exit is a shell builtin");
    }

    private StatusReport checkArgs(List<String> args){
        StatusReport statusReport=new StatusReport();
        statusReport.isSuccess=false;
        statusReport.exitCode=1;
        if(args.isEmpty()){
            statusReport.isSuccess=true;
            statusReport.exitCode=0;
            return statusReport;
        }
        else if(args.size()>1){
            System.out.println("-bash: exit too many arguments");
        return statusReport;
        }
        else {
            statusReport.isSuccess= true;
            statusReport.exitCode=0;
            String statusCode=args.get(0);
            try{
                statusReport.exitCode= Integer.parseInt(statusCode);
            }catch (NumberFormatException e){
                System.out.println("-bash: exit: "+statusCode+": numeric argument required");
            }
        }
   return statusReport; }
}
