package liubov.taskmanager.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationError extends Throwable{
    private Integer status;
    private String message;
    private Date timestamp;

    public ApplicationError(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
