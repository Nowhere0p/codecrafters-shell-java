package parser;

public class ActionToPath{
    public int fd;
    public boolean toAppend;
    public String path;
    public ActionToPath(boolean append,String path){
        this.toAppend=append;
        this.path=path;
    }
}