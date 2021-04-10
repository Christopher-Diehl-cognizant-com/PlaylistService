package com.galvanize.playlist.repository;

import com.galvanize.playlist.entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayListRepo extends JpaRepository<PlayListEntity, UUID> {

}
