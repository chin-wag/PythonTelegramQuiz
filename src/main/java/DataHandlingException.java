package main.java;

public class DataHandlingException extends Exception {
  public DataHandlingException(String errorMessage){
    super(errorMessage);
  }

  DataHandlingException(Throwable e) {
    super(e);
  }
}