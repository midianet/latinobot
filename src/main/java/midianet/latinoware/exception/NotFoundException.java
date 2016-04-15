package midianet.latinoware.exception;

public class NotFoundException extends BussinesException {

    public NotFoundException() {
        super("Registro Não encontrado");
    }

}