package Ecommerce_Completo.service.excepetions;

public class DatabaseException extends ResourceNotFoundException{
    public DatabaseException(String msg) {
        super(msg);
    }
}
