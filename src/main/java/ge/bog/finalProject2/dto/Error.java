package ge.bog.finalProject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
    public enum ErrorType {
        IncorrectParams,
        EmptyResult,
    }

    private String message;
    private ErrorType errorType;

    public Error(String message) {
        this.message = message;
        errorType = ErrorType.IncorrectParams;
    }
}
