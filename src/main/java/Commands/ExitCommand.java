package Commands;

import Exceptions.NumericArgumentRequiredException;
import Exceptions.TooManyArgumentsException;

public class ExitCommand  implements Command{

    @Override
    public void execute(String[] args) {
     StatusReport statusReport= checkArgs(args);
     if(statusReport.isSuccess){
         System.exit(statusReport.exitCode);
     }
    }
    private StatusReport checkArgs(String[] args){
        StatusReport statusReport=new StatusReport();
        statusReport.isSuccess=false;
        statusReport.exitCode=1;
        if(args.length==0){
            statusReport.isSuccess=true;
            statusReport.exitCode=0;
            return statusReport;
        }
        else if(args.length>1){
            System.out.println("-bash: exit too many arguments");
        return statusReport;
        }
        else {
            statusReport.isSuccess= true;
            statusReport.exitCode=0;
            String statusCode=args[0];
            try{
                statusReport.exitCode= Integer.parseInt(statusCode);
            }catch (NumberFormatException e){
                System.out.println("-bash: exit: "+statusCode+": numeric argument required");
            }
        }
   return statusReport; }
}
