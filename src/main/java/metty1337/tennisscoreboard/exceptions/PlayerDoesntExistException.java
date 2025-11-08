package metty1337.tennisscoreboard.exceptions;

public class PlayerDoesntExistException extends RuntimeException {
    public PlayerDoesntExistException(String message) {
        super(message);
    }
    public PlayerDoesntExistException(String message, Throwable cause) {
      super(message, cause);
    }
}
