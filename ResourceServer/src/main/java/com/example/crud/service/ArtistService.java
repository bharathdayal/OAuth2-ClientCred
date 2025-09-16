package com.example.crud.service;

import com.example.crud.dto.ArtistDto;
import com.example.crud.entity.Artist;

import java.util.List;
import java.util.Optional;


public interface ArtistService {

    Artist createArtist(ArtistDto artist);

    List<Artist> getAllArtist();

    Optional<Artist> findByArtistId(Long id);

    Artist updateArtist(Long id,ArtistDto artist);

    Artist patchArtist(Long id, ArtistDto artist);

    void deleteArtistById(Long id);
}
