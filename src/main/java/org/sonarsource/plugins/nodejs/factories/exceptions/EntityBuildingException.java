package org.sonarsource.plugins.nodejs.factories.exceptions;

public class EntityBuildingException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityBuildingException(String message, Exception exception) {
        super(message, exception);
    }
}