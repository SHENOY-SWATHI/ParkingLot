package swa.assignment.service;



import swa.assignment.model.SlotMap;
import swa.assignment.model.VehicleType;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminServiceImpl implements AdminService {
    @Override
    public Map<Integer, SlotMap> addLevel(Map<Integer, SlotMap> parkingSlots) {
        SlotMap st = slotAddition();
        if (parkingSlots.isEmpty()){
            parkingSlots.put(1,st);
        }else{
            parkingSlots.put(parkingSlots.size()+1,st);
        }
        return parkingSlots;
    }

    private SlotMap slotAddition() {

        Scanner scanner = new Scanner(System.in);
        SlotMap slotMap = new SlotMap();
        Map<VehicleType,Integer> slotViewMap = new HashMap<>();
        System.out.println("Enter slot availability for Car");
        int carSlot = scanner.nextInt();
        slotViewMap.put(VehicleType.CAR,carSlot);
        System.out.println("Enter slot availability for Bike");
        int bikeSlot = scanner.nextInt();
        slotViewMap.put(VehicleType.BIKE,bikeSlot);
        System.out.println("Enter slot availability for Bus");
        int busSlot = scanner.nextInt();
        slotViewMap.put(VehicleType.BUS,busSlot);
        System.out.println("Enter slot availability for EV");
        int evSlot = scanner.nextInt();
        slotViewMap.put(VehicleType.EV,evSlot);

        slotMap.setSlot(slotViewMap);
        return slotMap;
    }

    @Override
    public Map<Integer, SlotMap> removeLevel(Map<Integer, SlotMap> parkingSlots) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Level to be removed");
        int levelremove = scanner.nextInt();
        System.out.println("Are you sure : Y/N");
        String rmvConfirmation = scanner.next();
        if(rmvConfirmation.equalsIgnoreCase("Y")){
            parkingSlots.remove(levelremove);
            return parkingSlots;
        } else if(rmvConfirmation.equalsIgnoreCase("N")){
            System.out.println("Exiting Admin Panel");
            return parkingSlots;
        }else{
            System.out.println("Invalid Entry");
            System.out.println("Exiting Admin Panel");
            return null;
        }
    }
}

