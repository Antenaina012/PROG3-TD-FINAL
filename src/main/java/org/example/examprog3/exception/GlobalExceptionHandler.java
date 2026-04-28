package org.example.examprog3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Pour les ressources non trouvées (Code 404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 2. Pour les erreurs de règles métier (Code 400 - Bad Request)
    // Tu peux mettre plusieurs exceptions ici !
    @ExceptionHandler({
            SponsorTenureException.class,
            InsufficientSponsorCount.class,
            PaymentException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> handleBusinessRules(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 3. Optionnel : Pour toutes les autres erreurs imprévues (Code 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOthers(Exception ex) {
        // On affiche l'erreur détaillée dans la console d'IntelliJ
        ex.printStackTrace();

        // On renvoie le VRAI message technique (ex: "Column 'id' is too long" ou "NullPointerException")
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Petite méthode utilitaire pour structurer la réponse JSON
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}