package swa.assignment.test;

import org.junit.Assert;
import org.junit.Test;
import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.Vehicle;
import swa.assignment.model.VehicleType;
import swa.assignment.service.AdminServiceImpl;
import swa.assignment.service.DisplayServiceImpl;
import swa.assignment.service.VehicleServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class parkingLotTest {
    VehicleServiceImpl vehicleServicetest = new VehicleServiceImpl();
    ParkingTicket pt = new ParkingTicket();
    @Test
    public void entryTest(){

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


}
