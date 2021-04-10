package com.galvanize.playlist.controller;

import com.galvanize.playlist.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @PostMapping()
    public ResponseEntity<?> newPlaylist(@RequestParam("name") String name){
        CustomResponse customResponse=new CustomResponse("successful");
        return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
    }
}
