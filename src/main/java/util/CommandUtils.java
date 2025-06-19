package util;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class CommandUtils {
public static List<String> checkCommandInPath(String command){
    List<String> paths=getPaths();
    List<String> locations=new ArrayList<>();
    for(String dir: paths) {
        File executable = new File(dir, command);
        if (executable.exists() && executable.canExecute()) {
            locations.add(executable.getAbsolutePath());
        }
    }
        return  locations;
}
public  static List<String> getPaths(){
    String paths=System.getenv("PATH");
    return  Arrays.stream(paths.split(":")).collect(Collectors.toList());
}

}
