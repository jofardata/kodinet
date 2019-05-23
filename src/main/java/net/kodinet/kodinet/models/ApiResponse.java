package net.kodinet.kodinet.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String responseCode;
    private String responseMessage;
    private Object data;
}
