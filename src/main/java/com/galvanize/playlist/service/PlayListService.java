package com.galvanize.playlist.service;

import com.galvanize.playlist.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {


    public CustomResponse createPlaylist(String name) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Successfully Created.");
        customResponse.setStatus(HttpStatus.CREATED);
        return customResponse;
    }
}
