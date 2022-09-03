package ir.maktab.onlineexam.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorModel implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -1;
    private String message;
    private HttpStatus errorCode;
}
