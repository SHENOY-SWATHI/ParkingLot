package swa.assignment.service;

import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;

import java.util.Map;

public interface DispalyService {

    void totalParkingAvailable(Map<Integer, SlotMap> parkingslots);
    void vehicleParked(Map<String, ParkingTicket> displayMap);
    void vehicleParkedLevelWise(Map<String, ParkingTicket> displayMap, int levels);
}
