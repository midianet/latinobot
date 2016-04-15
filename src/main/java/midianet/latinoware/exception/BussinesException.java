package midianet.latinoware.exception;

public abstract class BussinesException extends RuntimeException {

    public BussinesException(String message) {
        super(message);
    }

    public BussinesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinesException(Throwable cause) {
        super(cause);
    }

    public BussinesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}