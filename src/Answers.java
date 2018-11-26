enum Answers {
  CORRECT("Правильный ответ") {},
  INCORRECT ("Неправильный ответ") {},
  ERROR ("На сервере произошла ошибка.\nПопробуйте зайти позже") {};

  private final String message;

  Answers(String message) {
    this.message = message;
  }

  String getMessage() {
    return message;
  }
}
