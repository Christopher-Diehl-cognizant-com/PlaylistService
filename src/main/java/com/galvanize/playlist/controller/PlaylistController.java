package com.galvanize.playlist.controller;

import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.response.PlayListSongsResponse;
import com.galvanize.playlist.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlayListService playListService;

    @PostMapping()
    public ResponseEntity<?> newPlaylist(@RequestParam("name") String name){
        CustomResponse customResponse = playListService.createPlaylist(name);
        return new ResponseEntity<>(customResponse, customResponse.getStatus());
    }

    @PutMapping("/song")
    public ResponseEntity<?> addSong2PlayList(@RequestParam("name") String name, @RequestParam("song_name") String songName){
        CustomResponse customResponse = this.playListService.addSong2Playlist(name,songName);
        return new ResponseEntity<>(customResponse, customResponse.getStatus());
    }

    @DeleteMapping("/song")
    public ResponseEntity<?> removeSong2PlayList(@RequestParam("name") String name, @RequestParam("song_name") String songName){
        CustomResponse customResponse = this.playListService.removeSong2Playlist(name,songName);
        return new ResponseEntity<>(customResponse, customResponse.getStatus());
    }

    @GetMapping("/song")
    public ResponseEntity<?> playListSongs(@RequestParam("name") String name){
        PlayListSongsResponse playListSongsResponse = new PlayListSongsResponse();
        playListSongsResponse.setStatus(HttpStatus.OK);
        playListSongsResponse.setMessage("Playlist test_list songs.");
        playListSongsResponse.setSongs(Arrays.asList("song 1", "song 2"));
        return new ResponseEntity<>(playListSongsResponse, playListSongsResponse.getStatus());
    }

}
