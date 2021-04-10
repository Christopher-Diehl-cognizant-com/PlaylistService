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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;


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


}
