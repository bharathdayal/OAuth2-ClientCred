package com.example.crud.exceptions;

public class ArtistNotFoundException extends RuntimeException{

    public ArtistNotFoundException(Long id) {
        super("Artist " +id+ " not found");
    }
}
