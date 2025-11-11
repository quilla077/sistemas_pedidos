package reservas.demo.Seguridad;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {//extends ResponseEntityExceptionHandler {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidateExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }
//    @ExceptionHandler(LocalNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ErrorMessage> mensajeError(LocalNotFoundException exception){
//        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
//    }
//
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders httpHeaders,HttpStatus httpStatus){
//        Map<String,Object> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(
//                error -> {errors.put(error.getField(),error.getDefaultMessage());}
//        );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }






}
