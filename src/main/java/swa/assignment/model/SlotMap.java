package swa.assignment.model;

import java.util.Map;

public class SlotMap {

    private Map<VehicleType,Integer> slot;

    public Map<VehicleType, Integer> getSlot() {
        return slot;
    }

    public void setSlot(Map<VehicleType, Integer> slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Level{" +
                "slot=" + slot +
                '}';
    }
}
