package metty1337.tennisscoreboard.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExceptionMessages {
    DATABASE_EXCEPTION("Database error"),
    PLAYER_DOESNT_EXIST_EXCEPTION("Player does not exist"),
    MATCH_DOESNT_EXIST_EXCEPTION("Match does not exist"),
    ILLEGAL_ARGUMENT_EXCEPTION("Has no next point");
    private final String message;
}

