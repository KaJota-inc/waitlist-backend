package com.kajota.kajota_webpage.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;

    public Response(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

}