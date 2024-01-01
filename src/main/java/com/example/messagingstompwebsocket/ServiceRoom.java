package com.example.messagingstompwebsocket;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceRoom {
    List<Room> roomList = new ArrayList<>();

    public List<Room> getAllRooms(){
        return roomList;
    }
    public void addRoom(Room room){
        roomList.add(room);
    }
}


class Room{
    private int id;
    private String name;

    public Room(int id, String name){
        this. id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}