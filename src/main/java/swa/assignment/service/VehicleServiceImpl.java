package swa.assignment.service;

import swa.assignment.Exception.NoParkingSlotException;
import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class VehicleServiceImpl implements VehicleService {

    @Override
    public ParkingTicket createEntry(Vehicle v, int level, int counter) {

        ParkingTicket pt = new ParkingTicket();
        pt.setVehicleDetails(v);
        pt.setLevel(level);
        pt.setVehicleRef(String.valueOf(level) + 'L' + counter);
        pt.setEntryDate(LocalDate.now());
        pt.setEntryTime(LocalTime.now());

        return pt;
    }

    @Override
    public ParkingTicket exitEntry(Map<String, ParkingTicket> displayMap, String refNum) {

        ParkingTicket pt = new ParkingTicket();
        Optional<ParkingTicket> getPt = displayMap.entrySet().stream()
                .filter(p -> p.getKey().equalsIgnoreCase(refNum))
                .map(Map.Entry::getValue)
                .findFirst();
        pt.setVehicleRef(refNum);
        pt.setVehicleDetails(getPt.get().getVehicleDetails());
        pt.setEntryDate(getPt.get().getEntryDate());
        pt.setEntryTime(getPt.get().getEntryTime());
        pt.setExitDate(LocalDate.now());
        pt.setExitTime(LocalTime.now());
        pt.setPayment(caluculatePayment(pt));

        return pt;

    }

    @Override
    public boolean checkAvailabilty(Map<String, ParkingTicket> displayMap, Map<Integer, SlotMap> parkingslots, VehicleType vehicleType, int level)  {
        try{
            SlotMap parkingMap = parkingslots.get(level);
            if(parkingMap!= null){
                int sum = parkingMap.getSlot().values().stream().mapToInt(Integer::intValue).sum();
                Map<VehicleType, Integer> specificType = new HashMap<>();
                List<ParkingTicket> levelParking = displayMap.entrySet().stream()
                        .filter(p->p.getKey().substring(0,1).equalsIgnoreCase(Integer.toString(level)))
                        .map(Map.Entry::getValue).collect(Collectors.toList());

                if (levelParking.size() != sum && !levelParking.isEmpty()){
                    for(ParkingTicket p : levelParking){
                        VehicleType specificKey = p.getVehicleDetails().getVehicleType();
                        if (specificType.containsKey(specificKey)){
                            specificType.put(specificKey,specificType.get(specificKey)+1);
                        } else {
                            specificType.put(specificKey,1);
                        }
                    }
                    return checkVehicleBasedAvailability(vehicleType,specificType,parkingMap.getSlot());
                }
            } else{
                String msg = "No Slot Assigned by Admin";
                throw new NoParkingSlotException(msg);
            }
        } catch (NoParkingSlotException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean checkVehicleBasedAvailability(VehicleType vehicleType, Map<VehicleType, Integer> specificType, Map<VehicleType, Integer> slot) {
     int availableLot = slot.get(vehicleType);
     int occupiedLot = specificType.containsKey(vehicleType) ? specificType.get(vehicleType) : 0;

     return availableLot == occupiedLot ? false : true;
    }

    private double caluculatePayment(ParkingTicket pt) {
        //Assuming the entry and exit Date are same
        LocalTime entry = pt.getEntryTime();
        LocalTime exit = pt.getExitTime();

        long diff = entry.until(exit, ChronoUnit.HOURS);
        return diff<=1 ? 40.00 : (diff -1)*20+40.00;
    }

    public VehicleType getVehicle(int vehicleTypeNum) {
        switch (vehicleTypeNum){
            case 1:
                return VehicleType.CAR;
            case 2:
                return VehicleType.BIKE;
            case 3:
                return VehicleType.BUS;
            case 4:
                return VehicleType.EV;
            default:
                return null;
        }
    }
}
