package com.example.messagingstompwebsocket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Type;
import java.util.List;

@Controller
public class RoomController {
    @Autowired
    ServiceRoom serviceRoom;
    @GetMapping(value="/rooms", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRooms(){
        List<Room> roomList = serviceRoom.getAllRooms();
        Gson gson = new Gson ();
        Type type = new TypeToken<List<Room>>() {}.getType ();
        String json = gson.toJson (roomList, type);
        System.out.println (json);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/create/room")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        serviceRoom.addRoom(room);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
