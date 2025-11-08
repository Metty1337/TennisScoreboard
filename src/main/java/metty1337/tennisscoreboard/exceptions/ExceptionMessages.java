package metty1337.tennisscoreboard.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExceptionMessages {
    DATABASE_EXCEPTION("Database error"),
    PlayerDoesntExistException("Player does not exist");
    private final String message;
}

