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
        CustomResponse customResponse = new CustomResponse();
        if(name == null || name.length()<=0){
            customResponse.setMessage("Unsuccessful: Please enter playlist name.");
            customResponse.setStatus(HttpStatus.BAD_REQUEST);
            return customResponse;
        }
        if(playListRepo.findPlayListEntityByName(name) != null){
            customResponse.setMessage("Unsuccessful: Already Exist.");
            customResponse.setStatus(HttpStatus.BAD_REQUEST);
            return customResponse;
        }
        PlayListEntity savedPlayListEntity = new PlayListEntity();
        savedPlayListEntity.setName(name);
        savedPlayListEntity = playListRepo.save(savedPlayListEntity);

        customResponse.setMessage("Successfully Created.");
        customResponse.setStatus(HttpStatus.CREATED);
        return customResponse;
    }

}
