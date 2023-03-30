package br.com.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.demo.exception.responses.GenericErrorResponse;

import java.util.function.Supplier;

public abstract class ApiError extends ResponseStatusException {
    @Getter
    private HttpStatus status;

    @Getter
    @Setter
    private boolean logAsError = true;

    @Getter
    private GenericErrorResponse response;

    public ApiError(HttpStatus status, GenericErrorResponse response) {
        super(status);
        this.status = status;
        this.response = response;
    }

    public static <E extends Class<? extends ApiError>, T> T doesNotLogAsError(E expectedErrorClass,
            Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ApiError error) {
            if (expectedErrorClass.isAssignableFrom(error.getClass())) {
                error.setLogAsError(false);
            }
            throw error;
        }
    }

}
