package Team4SecurityApp.security.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse<T> {

    public ApiResponse(T data, HttpStatus httpStatus, String message) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    T data;
    HttpStatus httpStatus;
    String message;
}
