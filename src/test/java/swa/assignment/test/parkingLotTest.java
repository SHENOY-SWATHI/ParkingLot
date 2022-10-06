package swa.assignment.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;
import swa.assignment.service.AdminService;
import swa.assignment.service.AdminServiceImpl;
import swa.assignment.service.DisplayServiceImpl;
import swa.assignment.service.VehicleServiceImpl;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class parkingLotTest {
    VehicleServiceImpl vehicleServicetest = new VehicleServiceImpl();
    ParkingTicket pt = new ParkingTicket();
    Map<String,ParkingTicket> testDisplay = new HashMap<>();
    @Test
    public void customerTest(){

        Vehicle v = new Vehicle(1,"Test",VehicleType.CAR);
        pt = vehicleServicetest.createEntry(v,1,1);
        Assert.assertNotNull(pt);
        Assert.assertNotNull(pt.getEntryTime());

        testDisplay.put("1l1",pt);
        pt = vehicleServicetest.exitEntry(testDisplay,"1L1");
        Assert.assertNotNull(pt);
        Assert.assertNotNull(pt.getExitTime());
    }

    @Test
    public void getVehicleTest(){
        Assert.assertEquals(VehicleType.CAR,vehicleServicetest.getVehicle(1));
        Assert.assertEquals(VehicleType.BIKE,vehicleServicetest.getVehicle(2));
        Assert.assertEquals(VehicleType.BUS,vehicleServicetest.getVehicle(3));
        Assert.assertEquals(VehicleType.EV,vehicleServicetest.getVehicle(4));
        Assert.assertEquals(null,vehicleServicetest.getVehicle(5));
    }

    @Test
    public void checkAvailibilityEqualTest(){

        Vehicle v = new Vehicle(1,"Test",VehicleType.CAR);
        pt = vehicleServicetest.createEntry(v,1,1);
        testDisplay.put("1l1",pt);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        st.setSlot(test);
        parkingSlots.put(1,st);

        boolean availabilityTest = vehicleServicetest.checkAvailabilty(testDisplay,parkingSlots,VehicleType.CAR,1);
        Assert.assertTrue(availabilityTest);
    }

    @Test
    public void checkAvailibilityLessTest(){

        Vehicle v = new Vehicle(1,"Test",VehicleType.CAR);
        pt = vehicleServicetest.createEntry(v,1,1);
        testDisplay.put("1l1",pt);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,2);
        st.setSlot(test);
        parkingSlots.put(1,st);

        boolean availabilityTest = vehicleServicetest.checkAvailabilty(testDisplay,parkingSlots,VehicleType.CAR,1);
        Assert.assertTrue(availabilityTest);
    }

    @Test
    public void adminTestWithLevel(){

        AdminServiceImpl adminService = new AdminServiceImpl();

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        st.setSlot(test);
        parkingSlots.put(1,st);

        String data = " 1 \n 2 \n 3 \n 4";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        adminService.addLevel(parkingSlots);
    }

    @Test
    public void adminTestWithoutLevel(){

        AdminServiceImpl adminService = new AdminServiceImpl();
        Scanner sc = new Scanner(System.in);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();

        String data = " 1 \n 2 \n 3 \n 4";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        adminService.addLevel(parkingSlots);
    }

    @Test
    public void adminTestRemoveLevel(){

        AdminServiceImpl adminService = new AdminServiceImpl();
        Scanner sc = new Scanner(System.in);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        st.setSlot(test);
        parkingSlots.put(1,st);

        String data = " 1 \n Y";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        parkingSlots = adminService.removeLevel(parkingSlots);
        Assert.assertEquals(0, parkingSlots.size());
    }

    @Test
    public void adminTestNotRemoveLevel(){

        AdminServiceImpl adminService = new AdminServiceImpl();
        Scanner sc = new Scanner(System.in);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        st.setSlot(test);
        parkingSlots.put(1,st);

        String data = " 1 \n N";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        parkingSlots = adminService.removeLevel(parkingSlots);
        Assert.assertEquals(1, parkingSlots.size());
    }

    @Test
    public void adminTestRemoveLevelInvalid(){

        AdminServiceImpl adminService = new AdminServiceImpl();
        Scanner sc = new Scanner(System.in);

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        st.setSlot(test);
        parkingSlots.put(1,st);

        String data = " 1 \n A";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        parkingSlots = adminService.removeLevel(parkingSlots);
        Assert.assertNull(parkingSlots);
    }

    @Test
    public void displayAvailability(){
        DisplayServiceImpl dsp = new DisplayServiceImpl();

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();
        SlotMap st = new SlotMap();
        Map<VehicleType,Integer> test = new HashMap<>();
        test.put(VehicleType.CAR,1);
        test.put(VehicleType.BIKE,2);
        st.setSlot(test);
        parkingSlots.put(1,st);
        parkingSlots.put(2,st);

        Map<Integer,Integer> getLevelAvailabilityTest = dsp.totalParkingAvailable(parkingSlots);
        Assert.assertEquals(2,getLevelAvailabilityTest.size());
    }

    @Test
    public void displayOccupied(){
        DisplayServiceImpl dsp = new DisplayServiceImpl();

        Vehicle v = new Vehicle(1,"Test",VehicleType.CAR);
        pt = vehicleServicetest.createEntry(v,1,1);
        testDisplay.put("1l1",pt);
        testDisplay.put("1l2",pt);

        long getLevelAvailabilityTest = dsp.vehicleParked(testDisplay);
        Assert.assertEquals(2,getLevelAvailabilityTest);
    }


}
