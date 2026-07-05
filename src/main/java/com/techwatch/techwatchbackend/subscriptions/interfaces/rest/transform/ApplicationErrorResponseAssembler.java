package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Assembler to convert application errors into HTTP responses.
 */
public class ApplicationErrorResponseAssembler {

    public static ResponseEntity<ApplicationError> toResponseFromError(ApplicationError error) {
        if (error.code().endsWith("_NOT_FOUND")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        if (error.code().equals("BUSINESS_RULE_VIOLATION")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        if (error.code().equals("VALIDATION_ERROR")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}