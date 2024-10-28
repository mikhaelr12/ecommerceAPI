package md.practice.ecommerceapi.exception;

import java.io.Serial;

public class CartException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public CartException(String message) {
        super(message);
    }

    public CartException() {
        super();
    }
}
