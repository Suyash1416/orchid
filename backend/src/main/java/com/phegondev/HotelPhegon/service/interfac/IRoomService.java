package com.phegondev.HotelPhegon.service.interfac;

import org.springframework.web.multipart.MultipartFile;

import com.phegondev.HotelPhegon.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);
    List<String> getAllRoomTypes();
    Response getAllRooms();
    Response getRoomById(Long id);  // This should already exist

    Response deleteRoom(Long roomId);
    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);
   // Response getRoomById(Long roomId);
    Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
    Response getAllAvailableRooms();
}
