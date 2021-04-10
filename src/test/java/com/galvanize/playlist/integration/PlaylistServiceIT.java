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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andDo(document("playlist", responseFields(
                        fieldWithPath("message").description("Response Message")
                )))
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

    @Test
    public void addSong2PlaylistTest() throws Exception {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName("test_list");
        playListRepo.save(playListEntity);

        RequestBuilder requestBuilder= put("/playlist/song")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list")
                .param("song_name","My song");
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Successfully: Added song to test_list playlist."))
        .andDo(print())
                .andDo(document("add-playlist-song", responseFields(
                        fieldWithPath("message").description("Response Message")
                )))
        ;
    }

    @Test
    public void removeSong2PlaylistTest() throws Exception {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName("test_list");
        playListEntity.addSong("first_song");
        playListEntity.addSong("remove_song");
        playListRepo.save(playListEntity);

        RequestBuilder requestBuilder= delete("/playlist/song")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("name","test_list")
                .param("song_name","remove_song");
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("message").value("Successfully: Removed song from test_list playlist."))
                .andDo(print())
                .andDo(document("delete-playlist-song", responseFields(
                        fieldWithPath("message").description("Response Message")
                )))
        ;
    }

    @Test
    public void getPlayListSongs() throws Exception {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName("test_list");
        playListEntity.addSong("song 1");
        playListEntity.addSong("song 2");
        playListRepo.save(playListEntity);
        RequestBuilder rq = get("/playlist/song")
                            .param("name", "test_list")
                ;

        this.mockMvc.perform(rq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", hasSize(2)))
                .andExpect(jsonPath("$.songs[0]").value("song 1"))
                .andExpect(jsonPath("$.songs[1]").value("song 2"))
                .andDo(print())
                .andDo(document("list-playlist-songs", responseFields(
                        fieldWithPath("message").description("Response Message"),
                        fieldWithPath("songs").description("List of Songs.")
                )))
        ;
    }
}
