package com.galvanize.playlist.service;

import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepo;
import com.galvanize.playlist.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepo playListRepo;

    public CustomResponse createPlaylist(String name) {
        PlayListEntity savedPlayListEntity = new PlayListEntity();
        savedPlayListEntity.setName(name);
        savedPlayListEntity = playListRepo.save(savedPlayListEntity);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Successfully Created.");
        customResponse.setStatus(HttpStatus.CREATED);
        return customResponse;
    }

}
