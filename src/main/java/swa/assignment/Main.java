package swa.assignment;



import swa.assignment.Exception.InvalidInputException;
import swa.assignment.Exception.NoParkingSlotException;
import swa.assignment.Exception.ParkingLotFullException;
import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;
import swa.assignment.service.AdminServiceImpl;
import swa.assignment.service.DisplayServiceImpl;
import swa.assignment.service.VehicleServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to ParkingLot");

        Map<String, ParkingTicket> displayMap = new HashMap<>();
        Map<Integer, SlotMap> parkingslots = new HashMap<>();

        while(true){

            System.out.println("Enter your choice : \n 1. Customer \n 2. Admin \n 3. DisplayBoard \n 4. Exit");
            int choice = scanner.nextInt();

            if (choice == 1){
                System.out.println("Welcome Customer");
                handleCarPark(displayMap,parkingslots);
            } else if (choice == 2 ) {
                System.out.println("Welcome Admin");
                handleAdmin(parkingslots);
            } else if (choice == 3) {
                System.out.println("Display Board");
                displayboard(displayMap,parkingslots);
            } else {
                System.out.println("Exiting the System");
                System.exit(0);
            }
        }
    }

    private static void displayboard(Map<String, ParkingTicket> displayMap,Map<Integer, SlotMap> parkingslots) {
        DisplayServiceImpl dsp = new DisplayServiceImpl();
        Map<Integer,Integer> getLevelAvailability = dsp.totalParkingAvailable(parkingslots);
        getLevelAvailability.forEach((key, value) -> System.out.println("Total Parking Available in Level " + key + ":" + value));
        System.out.println("_________________________________");

        long count = dsp.vehicleParked(displayMap);
        System.out.println("Total Vehicle Parked : " + count);
        System.out.println("_________________________________");

        dsp.vehicleParkedLevelWise(displayMap,parkingslots.size());
    }

    private static void handleCarPark(Map<String, ParkingTicket> displayMap,Map<Integer, SlotMap> parkingslots) {
        Scanner scanner = new Scanner(System.in);
        VehicleServiceImpl vehicleServiceImpl = new VehicleServiceImpl();

        System.out.println("Enter Vehicle Type : \n 1. CAR \n 2. BIKE \n 3. BUS \n 4. EV");
        int vehicleTypeNum = scanner.nextInt();
        VehicleType vehicleType = vehicleServiceImpl.getVehicle(vehicleTypeNum);
        System.out.println("Service Opted: \n 1. Entry \n 2. Exit");
        int opted = scanner.nextInt();

        System.out.println("Enter Parking Level");
        int level = scanner.nextInt();
        try{
            if (!parkingslots.isEmpty()){
                try{
                    if (opted ==1){
                        try{
                            if (displayMap.isEmpty() || vehicleServiceImpl.checkAvailabilty(displayMap,parkingslots,vehicleType,level)){
                                System.out.println("Enter Vehicle Number");
                                int vehicleNumber = scanner.nextInt();

                                System.out.println("Enter Owner Name");
                                String vehicleOwner = scanner.next();

                                Vehicle v = new Vehicle(vehicleNumber,vehicleOwner, vehicleType);
                                int counter = displayMap.isEmpty() ? 1 : displayMap.size()+1;

                                ParkingTicket pt = vehicleServiceImpl.createEntry(v,level,counter);
                                displayMap.put(pt.getVehicleRef(), pt);
                                System.out.println(pt);
                            } else {
                                String msg = "No Space Available in Level " + level;
                                throw new ParkingLotFullException(msg);
                            }
                        } catch (ParkingLotFullException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (opted ==2) {
                        System.out.println("Enter Vehicle Ref No :");
                        String refNum = scanner.next();

                        ParkingTicket pt = vehicleServiceImpl.exitEntry(displayMap,refNum);
                        System.out.println(pt);
                        System.out.println(displayMap);
                        displayMap.remove(pt.getVehicleRef().toUpperCase());
                        System.out.println(displayMap);
                    } else {
                        String msg = "Invalid Input";
                        throw new InvalidInputException(msg);
                    }
                } catch (InvalidInputException e) {
                    e.printStackTrace();
                }
            }else{
                String msg = "No Slot Assigned by Admin";
                throw new NoParkingSlotException(msg);
            }
        } catch (NoParkingSlotException e) {
            e.printStackTrace();
        }

    }

    private static void handleAdmin(Map<Integer, SlotMap> parkingslots) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose from below option : \n 1. Add New Level \n 2. Remove Existing Level");
        int options = scanner.nextInt();

        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        try{
            if(options == 1){
                Map<Integer,SlotMap> addParkingSlots = adminServiceImpl.addLevel(parkingslots);
                System.out.println(addParkingSlots);
            } else if (options == 2) {
                Map<Integer,SlotMap> addParkingSlots = adminServiceImpl.removeLevel(parkingslots);
                System.out.println(addParkingSlots);
            } else {
                String msg = "Invalid Input";
                throw new InvalidInputException(msg);
            }
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }

}