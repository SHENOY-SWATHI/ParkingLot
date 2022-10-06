package swa.assignment.service;

import swa.assignment.model.SlotMap;

import java.util.Map;

public interface AdminService {

    Map<Integer, SlotMap> addLevel(Map<Integer,SlotMap> parkingSlots);
    Map<Integer, SlotMap> removeLevel(Map<Integer,SlotMap> parkingSlots);
}
