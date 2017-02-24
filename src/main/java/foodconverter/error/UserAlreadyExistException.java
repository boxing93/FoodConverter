package foodconverter.error;

/**
 * Created by Iliya on 28.12.2016 г..
 */

public final class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = -234586131052L;

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
