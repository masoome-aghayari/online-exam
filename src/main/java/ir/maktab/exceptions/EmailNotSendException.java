package ir.maktab.exceptions;

import javax.mail.MessagingException;

public class EmailNotSendException extends MessagingException {
    public EmailNotSendException(String message) {
        super(message);
    }
}
