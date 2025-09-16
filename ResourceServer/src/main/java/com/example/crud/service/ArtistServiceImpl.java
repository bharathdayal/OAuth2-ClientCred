package com.example.crud.service;

import com.example.crud.dto.ArtistDto;
import com.example.crud.entity.Artist;
import com.example.crud.exceptions.ArtistNotFoundException;
import com.example.crud.repository.ArtistRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {


    private ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository){
        this.artistRepository=artistRepository;
    }



    public Artist createArtist(ArtistDto artist) {
        Artist entity = new Artist();
        entity.setArtistName(artist.getArtistName());
        entity.setArtistBand(artist.getArtistBand());
        return artistRepository.save(entity);
    }

    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    public Optional<Artist> findByArtistId(Long id) {
        return Optional.ofNullable(artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id)));
    }


    public Artist updateArtist(Long id,ArtistDto artist) {
        Artist existingArtist =artistRepository.findById(id).orElseThrow(()->new RuntimeException("Artist not found"));
        if(artist.getArtistName() !=null) {
            existingArtist.setArtistName(artist.getArtistName());
        }
        if(artist.getArtistBand() !=null) {
            existingArtist.setArtistBand(artist.getArtistBand());
        }
        return artistRepository.save(existingArtist);

    }

    public Artist patchArtist(Long id,ArtistDto artist) {
        Artist existing = artistRepository.findById(id).orElseThrow(()->new RuntimeException("Artist not found"));
        if(artist.getArtistName() !=null) {
            existing.setArtistName(artist.getArtistName());
        }
        if(artist.getArtistBand() !=null) {
            existing.setArtistBand(artist.getArtistBand());
        }
        return artistRepository.save(existing);
    }

    public void deleteArtistById(Long id) {
        artistRepository.deleteById(id);
    }
}
