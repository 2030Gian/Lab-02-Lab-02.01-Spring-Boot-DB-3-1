package com.example.demo.song.application;

// Se importan la clase necesaria y su respectivo repositorio desde sus carpetas correspondientes
import com.example.demo.song.domain.SongRepository;
import com.example.demo.song.domain.Song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping
    public ResponseEntity<List<Song>> songs() {
        List<Song> songs = songRepository.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> song(@RequestBody Song song) {
        songRepository.save(song);
        return ResponseEntity.status(201).body("Created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(@PathVariable Long id, @RequestBody Song song) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            Song existingSong = optionalSong.get();
            existingSong.setTitle(song.getTitle());
            existingSong.setArtist(song.getArtist());
            existingSong.setAlbum(song.getAlbum());
            existingSong.setReleaseDate(song.getReleaseDate());
            existingSong.setGenre(song.getGenre());
            existingSong.setDuration(song.getDuration());
            existingSong.setCoverImage(song.getCoverImage());
            existingSong.setSpotifyUrl(song.getSpotifyUrl());
            songRepository.save(existingSong);
            return ResponseEntity.status(200).body("Updated");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            songRepository.deleteById(id);
            return ResponseEntity.status(200).body("Deleted");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchSong(@PathVariable Long id, @RequestBody Song editedSong) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            Song oldSong = song.get();
            if (editedSong.getTitle() != null) {
                oldSong.setTitle(editedSong.getTitle());
            }
            if (editedSong.getArtist() != null) {
                oldSong.setArtist(editedSong.getArtist());
            }
            if (editedSong.getAlbum() != null) {
                oldSong.setAlbum(editedSong.getAlbum());
            }
            if (editedSong.getReleaseDate() != null) {
                oldSong.setReleaseDate(editedSong.getReleaseDate());
            }
            if (editedSong.getGenre() != null) {
                oldSong.setGenre(editedSong.getGenre());
            }
            if (editedSong.getDuration() != null) {
                oldSong.setDuration(editedSong.getDuration());
            }
            if (editedSong.getCoverImage() != null) {
                oldSong.setCoverImage(editedSong.getCoverImage());
            }
            if (editedSong.getSpotifyUrl() != null) {
                oldSong.setSpotifyUrl(editedSong.getSpotifyUrl());
            }
            songRepository.save(oldSong);
            return ResponseEntity.status(200).body("Updated Partially");
        } else {
            return ResponseEntity.status(404).body("Not found");
        }
    }
}
