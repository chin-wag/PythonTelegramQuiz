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
  },
  ERROR {
    String getMessage() { return "На сервере произошла ошибка.\nПопробуйте зайти позже"; }
  };

  abstract String getMessage();
}
