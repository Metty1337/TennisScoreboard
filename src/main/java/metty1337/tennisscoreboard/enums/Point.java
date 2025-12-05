package metty1337.tennisscoreboard.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Point {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40),
    GAME(100),
    DEUCE(0),
    ADVANTAGE(1);
    private final int value;

    public Point next() {
        if (this.equals(GAME)) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        }
        return values()[this.ordinal() + 1];
    }
}
