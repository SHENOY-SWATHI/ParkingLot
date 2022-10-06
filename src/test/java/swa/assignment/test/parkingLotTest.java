package swa.assignment.test;

import org.junit.Assert;
import org.junit.Test;

import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;
import swa.assignment.service.AdminServiceImpl;
import swa.assignment.service.VehicleServiceImpl;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class parkingLotTest {
    VehicleServiceImpl vehicleServicetest = new VehicleServiceImpl();
    ParkingTicket pt = new ParkingTicket();
    @Test
    public void customerTest(){

        Vehicle v = new Vehicle(1,"Test",VehicleType.CAR);
        pt = vehicleServicetest.createEntry(v,1,1);
        Assert.assertNotNull(pt);
        Assert.assertNotNull(pt.getEntryTime());

        Map<String,ParkingTicket> testDisplay = new HashMap<>();
        testDisplay.put("1l1",pt);
        pt = vehicleServicetest.exitEntry(testDisplay,"1L1");
        Assert.assertNotNull(pt);
        Assert.assertNotNull(pt.getExitTime());
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

        Map<Integer, SlotMap> parkingSlots = new HashMap<>();

        String data = " 1 \n 2 \n 3 \n 4";
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);

        adminService.addLevel(parkingSlots);
    }

    @Test
    public void adminTestRemoveLevel(){

        AdminServiceImpl adminService = new AdminServiceImpl();

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
    

}
