package org.sid.movieapp.exceptions;

public class AlreadyExistsException extends Exception{

    private static final long serialVersionUID = 1L ;

    public AlreadyExistsException(String entityNameAttribut , String entityName ){
        super(entityNameAttribut+ " " +entityName+ " Already exists !");
    }
}
