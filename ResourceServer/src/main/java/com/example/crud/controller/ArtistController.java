package com.example.crud.controller;

import com.example.crud.dto.ArtistDto;
import com.example.crud.entity.Artist;
import com.example.crud.service.ArtistService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/v1/artist")
@Validated
public class ArtistController {


    private ArtistService artistService;

    public ArtistController(ArtistService artistService){
        this.artistService=artistService;
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody ArtistDto artist) {
        Artist saveArtist = artistService.createArtist(artist);
        return new ResponseEntity<>(saveArtist, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtist() {
        List<Artist> getAll =artistService.getAllArtist();

        return new ResponseEntity<>(getAll,HttpStatus.OK);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<Artist> findByArtistId(@Valid @PathVariable Long artistId) {
        Optional<Artist> findById = artistService.findByArtistId(artistId);
        return artistService.findByArtistId(artistId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //return new ResponseEntity<>(findById,HttpStatus.OK);
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<Artist> updateArtist(@Valid @PathVariable Long artistId,
                                               @RequestBody  ArtistDto artist) {

        Artist updateArtist = artistService.updateArtist(artistId,artist);
        return new ResponseEntity<>(updateArtist,HttpStatus.OK);

    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtistById(@Valid @PathVariable Long artistId) {
        artistService.deleteArtistById(artistId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{artistId}")
    public ResponseEntity<Artist> patchArtist(@Valid @PathVariable Long artistId,
                                              @RequestBody ArtistDto artist) {

        Artist patchArtist = artistService.patchArtist(artistId,artist);
        return new ResponseEntity<>(patchArtist,HttpStatus.OK);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String test() {
        return "Test";
    }


}
