package com.example.demo.playlist.application;

// Se importan la clase necesaria y su respectivo repositorio desde sus carpetas correspondientes
import com.example.demo.playlist.domain.PlaylistRepository;
import com.example.demo.playlist.domain.Playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping
    public ResponseEntity<List<Playlist>> playlists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> playlist(@RequestBody Playlist playlist) {
        playlistRepository.save(playlist);
        return ResponseEntity.status(201).body("Created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            Playlist existingPlaylist = optionalPlaylist.get();
            existingPlaylist.setTitle(playlist.getTitle());
            existingPlaylist.setSongs(playlist.getSongs());
            existingPlaylist.setCoverImage(playlist.getCoverImage());
            playlistRepository.save(existingPlaylist);
            return ResponseEntity.status(200).body("Updated");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            playlistRepository.deleteById(id);
            return ResponseEntity.status(200).body("Deleted");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchPlaylist(@PathVariable Long id, @RequestBody Playlist editedplaylist) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            Playlist existingPlaylist = optionalPlaylist.get();
            if (editedplaylist.getTitle() != null) {
                existingPlaylist.setTitle(editedplaylist.getTitle());
            }
            if (editedplaylist.getSongs() != null) {
                existingPlaylist.setSongs(editedplaylist.getSongs());
            }
            if (editedplaylist.getCoverImage() != null) {
                existingPlaylist.setCoverImage(editedplaylist.getCoverImage());
            }
            playlistRepository.save(existingPlaylist);
            return ResponseEntity.status(200).body("Updated Partially");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

}
