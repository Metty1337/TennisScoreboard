package metty1337.tennisscoreboard.exceptions;

public class MatchDoesntExistException extends RuntimeException {
    public MatchDoesntExistException(String message) {
        super(message);
    }
    public MatchDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
