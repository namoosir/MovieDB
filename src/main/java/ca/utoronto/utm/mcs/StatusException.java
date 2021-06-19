package ca.utoronto.utm.mcs;

public class StatusException extends Exception {
    private int status;
    public StatusException(int status){
        this.status = status; 
    }
    public int getStatus(){
        return status;
    }
}
