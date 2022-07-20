package com.webrtc.videoChattingService.repository.room;


import com.webrtc.videoChattingService.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
