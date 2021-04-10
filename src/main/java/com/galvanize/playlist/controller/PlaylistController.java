package com.galvanize.playlist.controller;

import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
