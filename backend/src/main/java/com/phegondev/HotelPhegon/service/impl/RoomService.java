package com.phegondev.HotelPhegon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phegondev.HotelPhegon.dto.Response;
import com.phegondev.HotelPhegon.dto.RoomDTO;
import com.phegondev.HotelPhegon.entity.Room;
import com.phegondev.HotelPhegon.exception.OurException;
import com.phegondev.HotelPhegon.repo.BookingRepository;
import com.phegondev.HotelPhegon.repo.RoomRepository;
import com.phegondev.HotelPhegon.service.interfac.IRoomService;
import com.phegondev.HotelPhegon.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try {
            String imagePath = null;

            if (photo != null && !photo.isEmpty()) {
                imagePath = saveImageLocally(photo);
            }

            Room room = new Room();
            room.setRoomPhotoUrl(imagePath);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);

            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

            response.setRoom(roomDTO);
            response.setMessage("successful");
            response.setStatusCode(200);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice,
            MultipartFile photo) {
        Response response = new Response();

        try {
            String imagePath = null;

            if (photo != null && !photo.isEmpty()) {
                imagePath = saveImageLocally(photo);
            }

            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            if (roomType != null)
                room.setRoomType(roomType);
            if (roomPrice != null)
                room.setRoomPrice(roomPrice);
            if (description != null)
                room.setRoomDescription(description);
            if (imagePath != null)
                room.setRoomPhotoUrl(imagePath);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRoom(roomDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating a room " + e.getMessage());
        }
        return response;
    }

    private String saveImageLocally(MultipartFile file) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, file.getBytes());
    
        // Return public URL instead of file path
        return "http://localhost:7070/uploads/" + fileName;
    }
    

    // @Override
    // public List<String> getAllRoomTypes() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getAllRoomTypes'");
    // }
    @Override
public List<String> getAllRoomTypes() {
    return roomRepository.findDistinctRoomTypes();
}


    // @Override
    // public Response getAllRooms() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getAllRooms'");
    // }
    @Override
public Response getAllRooms() {
    Response response = new Response();
    try {
        List<Room> rooms = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<RoomDTO> roomDTOs = Utils.mapRoomListEntityToRoomListDTO(rooms);

        response.setStatusCode(200);
        response.setMessage("success");
        response.setRoomList(roomDTOs);
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage("Error fetching all rooms: " + e.getMessage());
    }
    return response;
}


    @Override
    public Response deleteRoom(Long roomId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoom'");
    }

   // @Override
    //public Response getRoomById(Long roomId) {
        // TODO Auto-generated method stub
   //     throw new UnsupportedOperationException("Unimplemented method 'getRoomById'");
   // }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndTypes(checkInDate, checkOutDate,
                    roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAvailableRooms'");
    }
    @Override
public Response getRoomById(Long roomId) {
    Response response = new Response();
    try {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new OurException("Room not found with ID: " + roomId));

        RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(room);

        response.setStatusCode(200);
        response.setMessage("Room fetched successfully");
        response.setRoom(roomDTO);
    } catch (OurException e) {
        response.setStatusCode(404);
        response.setMessage(e.getMessage());
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage("Something went wrong: " + e.getMessage());
    }

    return response;
}

}
