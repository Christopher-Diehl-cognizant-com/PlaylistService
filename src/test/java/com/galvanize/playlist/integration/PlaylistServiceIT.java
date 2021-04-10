package com.galvanize.playlist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("qa")
public class PlaylistServiceIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PlayListRepo playListRepo;

    @BeforeEach
    void init(){
        playListRepo.deleteAll();
    }

    @Test
    public void createNewPlaylistSuccessTest() throws Exception {
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Successfully Created."))
                .andDo(print())
        ;
    }


    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     */
    @Test
    public void createNewPlaylistFailedAlreadyExistTest() throws Exception {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName("test_list");
        playListRepo.save(playListEntity);
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","test_list");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Unsuccessful: Already Exist."));
    }

    @Test
    public void createNewPlaylistFailedNoPlayListNameExistTest() throws Exception {
        RequestBuilder requestBuilder= post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name","");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Unsuccessful: Please enter playlist name."));
    }
}
