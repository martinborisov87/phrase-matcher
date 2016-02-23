package md.your.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Martin on 2/22/2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input")
public class InvalidInputException extends RuntimeException {
}
