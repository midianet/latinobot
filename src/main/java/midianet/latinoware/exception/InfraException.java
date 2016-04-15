package midianet.latinoware.exception;

public class InfraException extends RuntimeException {

    public InfraException() {
    }

    public InfraException(String message) {
        super(message);
    }

    public InfraException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfraException(Throwable cause) {
        super(cause);
    }

    public InfraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}