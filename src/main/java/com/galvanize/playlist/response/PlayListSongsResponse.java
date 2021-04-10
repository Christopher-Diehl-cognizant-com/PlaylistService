package com.galvanize.playlist.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayListSongsResponse extends CustomResponse{
    List<String> songs;
}
