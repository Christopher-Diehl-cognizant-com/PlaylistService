package com.galvanize.playlist.unit.controller;

import com.galvanize.playlist.controller.PlaylistController;
import com.galvanize.playlist.response.CustomResponse;
import com.galvanize.playlist.service.PlayListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaylistController.class)
@ActiveProfiles("qa")
public class PlayListControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayListService playListService;

    /**
     * When a playlist is created with a name
     * Then a confirmation is returned that it was successful.
     * And the playlist is empty.
     * @throws Exception
     */
    @Test
    public void createNewPlaylistSuccessTest() throws Exception {
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list");
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMessage("Successfully Created.");
            customResponse.setStatus(HttpStatus.CREATED);
        when(playListService.createPlaylist(anyString())).thenReturn(customResponse);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Successfully Created."));
    }

    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     */

    @Test
    public void createNewPlaylistFailedDuplicationTest() throws Exception {
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list");
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Unsuccessful: Already Exist.");
        customResponse.setStatus(HttpStatus.BAD_REQUEST);
        when(playListService.createPlaylist(anyString())).thenReturn(customResponse);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Unsuccessful: Already Exist."));
    }

    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     * @throws Exception
     */

    @Test
    public void createNewPlaylistFailedEmptyNameTest() throws Exception {
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list");
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Unsuccessful: Please enter playlist name.");
        customResponse.setStatus(HttpStatus.BAD_REQUEST);
        when(playListService.createPlaylist(anyString())).thenReturn(customResponse);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Unsuccessful: Please enter playlist name."));
    }

}
