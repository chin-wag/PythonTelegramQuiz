enum Answers {
  CORRECT {
    String getMessage() {
      return "Правильный ответ";
    }
  },
  INCORRECT {
    String getMessage() {
      return "Неправильный ответ";
    }
  };

  abstract String getMessage();
}
