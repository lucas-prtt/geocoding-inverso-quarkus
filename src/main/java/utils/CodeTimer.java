package utils;

public class CodeTimer {
    Long start;
    Long end;
    public CodeTimer(){

    }
    public void start(){
        start = System.nanoTime();
    }
    public void stop(){
        end = System.nanoTime();
    }
    public long timeNano(){
        if(start == null){
            throw new RuntimeException("No se inicio el timer");
        }
        if(end == null){
            throw new RuntimeException("No se finalizo el timer");
        }
        return end - start;
    }
    public long timeMicro(){
        return timeNano() / 1000;
    }
    public long timeMili(){
        return timeNano() / 1000000;
    }
    public void printTime(String reference){
        System.out.println("Timer \"" + reference + "\" midió " + timeMili() + "ms");
    }
}
