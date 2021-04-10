package com.galvanize.playlist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ElementCollection
    private List<String> songList;

    public void addSong(String songName){
        if(this.songList==null){
            this.songList=new ArrayList<String>();
        }
        this.songList.add(songName);
    }
}
