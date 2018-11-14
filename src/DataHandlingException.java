class DataBaseException extends Exception {
  DataBaseException(String errorMessage){
    super(errorMessage);
  }

  DataBaseException(Throwable e) {
    super(e);
  }
}