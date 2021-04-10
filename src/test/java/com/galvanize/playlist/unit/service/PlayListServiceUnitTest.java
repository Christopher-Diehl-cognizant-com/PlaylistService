package com.galvanize.playlist.unit.service;

import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepo;
import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.service.PlayListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("qa")
public class PlayListServiceUnitTest {

    @InjectMocks
    private PlayListService playListService;

    @Mock
    private PlayListRepo playListRepo;

    /**
     * When a playlist is created with a name
     * Then a confirmation is returned that it was successful.
     * And the playlist is empty.
     * @throws Exception
     */
    @Test
    public void createNewPlaylistSuccessTest() throws Exception {
        CustomResponse actualResponse = playListService.createPlaylist("test_list");
        PlayListEntity playlistEntity = new PlayListEntity();
        playlistEntity.setName("test_list");
        verify(playListRepo).save(playlistEntity);

        assertNotNull(actualResponse);
        assertEquals(actualResponse.getStatus(), HttpStatus.CREATED);
        assertEquals(actualResponse.getMessage(), "Successfully Created.");
    }

    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     */

    @Test
    public void createNewPlaylistFailedDuplicationTest() throws Exception {

        when(playListRepo.findPlayListEntityByName(anyString())).thenReturn(new PlayListEntity());
        CustomResponse actualResponse = playListService.createPlaylist("test_list");
        assertNotNull(actualResponse);
        assertEquals(actualResponse.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(actualResponse.getMessage(), "Unsuccessful: Already Exist.");

    }

    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     * @throws Exception
     */

    @Test
    public void createNewPlaylistFailedEmptyNameTest() throws Exception {
        CustomResponse actualResponse = playListService.createPlaylist("");
        assertNotNull(actualResponse);
        assertEquals(actualResponse.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(actualResponse.getMessage(), "Unsuccessful: Please enter playlist name.");

    }

    @Test
    public void addSong2PlaylistTest() throws Exception {
        PlayListEntity playListEntity=new PlayListEntity();
        playListEntity.setName("test_list");
        playListEntity.setSongList(new ArrayList<String>());
        when(this.playListRepo.findPlayListEntityByName("test_list"))
                .thenReturn(playListEntity);

        CustomResponse actualResponse = playListService.addSong2Playlist("test_list","test_song");
        verify(this.playListRepo).save(playListEntity);
        assertNotNull(actualResponse);
        assertEquals(actualResponse.getStatus(), HttpStatus.CREATED);
        assertEquals(actualResponse.getMessage(), "Successfully: Added song to test_list playlist.");
    }

    @Test
    public void removeSong2PlaylistTest() throws Exception {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName("test_list");
        playListEntity.addSong("first_song");
        playListEntity.addSong("remove_song");

        when(this.playListRepo.findPlayListEntityByName("test_list"))
                .thenReturn(playListEntity);

        CustomResponse actualResponse = this.playListService.removeSong2Playlist("test_list","remove_song");

        verify(this.playListRepo).save(playListEntity);

        assertNotNull(actualResponse);
        assertEquals(actualResponse.getStatus(),HttpStatus.NO_CONTENT);
        assertEquals(actualResponse.getMessage(),"Successfully: Removed song from test_list playlist.");

    }
}
