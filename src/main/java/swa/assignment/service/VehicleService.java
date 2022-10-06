package swa.assignment.service;

import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;

import java.util.Map;

public interface VehicleService {

    ParkingTicket createEntry(Vehicle v , int level, int counter);
    ParkingTicket exitEntry(Map<String,ParkingTicket> displayMap, String refNum);

    boolean checkAvailabilty(Map<String, ParkingTicket> displayMap, Map<Integer, SlotMap> parkingslots, VehicleType vehicleType);
}
