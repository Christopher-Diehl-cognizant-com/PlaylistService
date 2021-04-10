package com.galvanize.playlist.service;

import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepo;
import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.response.PlayListSongsResponse;
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

    public CustomResponse addSong2Playlist(String playlistName,String songName){
        CustomResponse customResponse = new CustomResponse();
        PlayListEntity playListEntity=this.playListRepo.findPlayListEntityByName(playlistName);
        playListEntity.addSong(songName);

        this.playListRepo.save(playListEntity);

        customResponse.setMessage("Successfully: Added song to "+playListEntity.getName()+" playlist.");
        customResponse.setStatus(HttpStatus.CREATED);
        return customResponse;
    }

    public CustomResponse removeSong2Playlist(String playlistName,String songName){
        CustomResponse customResponse = new CustomResponse();
        PlayListEntity playListEntity=this.playListRepo.findPlayListEntityByName(playlistName);
        playListEntity.removeSong(songName);

        this.playListRepo.save(playListEntity);

        customResponse.setMessage("Successfully: Removed song from "+playListEntity.getName()+" playlist.");
        customResponse.setStatus(HttpStatus.NO_CONTENT);
        return customResponse;
    }

    public PlayListSongsResponse getPlaylistSongs(String name) {
        PlayListEntity savedPlayListEntity = playListRepo.findPlayListEntityByName(name);
        PlayListSongsResponse playListSongsResponse = new PlayListSongsResponse();
        playListSongsResponse.setSongs(savedPlayListEntity.getSongList());
        playListSongsResponse.setStatus(HttpStatus.OK);
        playListSongsResponse.setMessage("Playlist test_list songs.");
        return playListSongsResponse;
    }
}
