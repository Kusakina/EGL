package egl.client.service.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityServiceException extends Exception {

    public EntityServiceException(Throwable cause) {
        super(cause);
    }
}
