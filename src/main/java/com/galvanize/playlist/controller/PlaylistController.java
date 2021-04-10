package com.galvanize.playlist.controller;

import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
