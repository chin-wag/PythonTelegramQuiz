package main.java;

class DataHandlingException extends Exception {
  DataHandlingException(String errorMessage){
    super(errorMessage);
  }

  DataHandlingException(Throwable e) {
    super(e);
  }
}