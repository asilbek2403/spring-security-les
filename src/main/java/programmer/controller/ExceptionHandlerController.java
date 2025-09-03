package programmer.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import programmer.exp.AppBadRequestException;
import programmer.exp.ItemNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    //Controllerda exception sodir bo'lsa Bu Exeptionalarni ushlaydi Securityga keyin bajarib statusini beradi

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handler(RuntimeException e) {
        e.printStackTrace();
    return    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());

    }
    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<String> handler(AuthorizationDeniedException e) {
// bu Secury methodda annotatsiyada role Boshqa bo'lsa 403
    return    ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(e.getMessage());

    }



@ExceptionHandler({ItemNotFoundException.class , AppBadRequestException.class})//ikkalasi ham status ketishi kerak
    public ResponseEntity<String> handlerRun(ItemNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
}



//Exception bo'lsa unga classda yaratib keyin Controllerda Handlerga olisa Controllerdagi excp ushlaydi va oddiy status boradi 


}
