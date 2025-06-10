import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

         while (true){
            handleCommand();
         }
    }
    public  static  void handleCommand(){
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input+": command not found");
    }
}
