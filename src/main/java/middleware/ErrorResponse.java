package middleware;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.NoArgsConstructor;

public class ErrorResponse {
    @JsonbProperty
    public String code;
    @JsonbProperty
    public String message;

    @Override
    public String toString() {
        return JsonbBuilder.create().toJson(this);
    }

    public ErrorResponse() {} // Constructor vacío obligatorio para JSON-B

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}