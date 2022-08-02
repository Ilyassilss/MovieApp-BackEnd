package org.sid.movieapp.exceptions;

public class NotFoundException extends Exception{

    private static final long serialVersionUID = 1L ;

    public NotFoundException( String entityName ){
        super(entityName+ " Not found !");
    }
}
