package ir.maktab.onlineexam.exceptions;

import java.io.IOException;

public class InputNotValidException extends IOException {
    public InputNotValidException(String message) {
        super(message);
    }
}
