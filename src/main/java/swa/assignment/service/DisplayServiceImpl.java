package swa.assignment.service;



import swa.assignment.model.ParkingTicket;
import swa.assignment.model.SlotMap;
import swa.assignment.model.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisplayServiceImpl implements  DispalyService{

    @Override
    public Map<Integer,Integer> totalParkingAvailable(Map<Integer, SlotMap> parkingslots) {
        System.out.println("_________________________________");
        Map<Integer,Integer> getTotalavability = new HashMap<>();
        parkingslots.forEach((key, st) -> {
            int sum = st.getSlot().values().stream().mapToInt(Integer::intValue).sum();
            getTotalavability.put(key, sum);
        });
        return getTotalavability;
    }

    @Override
    public long vehicleParked(Map<String, ParkingTicket> displayMap) {
        System.out.println("_________________________________");
        return displayMap.entrySet().stream()
                .filter(list -> list.getValue().getExitTime() == null)
                .count();
    }

    @Override
    public void vehicleParkedLevelWise(Map<String, ParkingTicket> displayMap, int levels) {
        while(levels != 0){
            Map<VehicleType, Integer> specificType = new HashMap<>();
            int finalLevels = levels;
            List<ParkingTicket> f1 = displayMap.entrySet().stream()
                    .filter(p->p.getKey().substring(0,1).equalsIgnoreCase(Integer.toString(finalLevels)))
                    .map(Map.Entry::getValue).collect(Collectors.toList());
            System.out.println("_________________________________");
            System.out.println("Total Vehicle Parked in Level " + finalLevels + ":" + f1.size());
            for(ParkingTicket i: f1) {
                VehicleType keyCheck = i.getVehicleDetails().getVehicleType();
                if (specificType.containsKey(keyCheck)){
                    specificType.put(keyCheck,specificType.get(keyCheck) + 1);
                } else {
                    specificType.put(keyCheck,1);
                }
            }
            specificType.forEach((vehicleType, integer) ->
                    System.out.println("Occupied Space by " + vehicleType + ":" + integer ));
            System.out.println("_________________________________");
            levels --;
        }

    }
}
