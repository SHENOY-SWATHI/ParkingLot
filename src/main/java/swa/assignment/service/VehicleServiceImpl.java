package swa.assignment.service;

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
        StringBuilder str = new StringBuilder();

        ParkingTicket pt = new ParkingTicket();
        str.append(level).append('L').append(counter);
        pt.setVehicleDetails(v);
        pt.setLevel(level);
        pt.setVehicleRef(str.toString());
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
    public boolean checkAvailabilty(Map<String, ParkingTicket> displayMap, Map<Integer, SlotMap> parkingslots, VehicleType vehicleType)  {
        for(Map.Entry<Integer, SlotMap> i:parkingslots.entrySet()){
            Map<VehicleType, Integer> specificType = new HashMap<>();
            List<ParkingTicket> f1 = displayMap.entrySet().stream()
                    .filter(p->p.getKey().substring(0,1).equalsIgnoreCase(Integer.toString(i.getKey())))
                    .map(Map.Entry::getValue).collect(Collectors.toList());
            for(ParkingTicket pttemp: f1) {
                VehicleType keyCheck = pttemp.getVehicleDetails().getVehicleType();
                if (specificType.containsKey(keyCheck)){
                    specificType.put(keyCheck,specificType.get(keyCheck) + 1);
                } else {
                    specificType.put(keyCheck,1);
                }
            }
            checkVehicleBasedAvailability(vehicleType);
            System.out.println(specificType.equals(parkingslots.get(i.getKey()).getSlot()));
        }

    return true;
    }

    private void checkVehicleBasedAvailability(VehicleType vehicleType) {

    }

    private double caluculatePayment(ParkingTicket pt) {
        //Assuming the entry and exit Date are same
        LocalTime entry = pt.getEntryTime();
        LocalTime exit = pt.getExitTime();

        long diff = entry.until(exit, ChronoUnit.HOURS);
        return diff<=1 ? 40.00 : (diff -1)*20+40.00;
    }
}
