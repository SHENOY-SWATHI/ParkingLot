package swa.assignment.service;

import swa.assignment.model.SlotMap;

import java.util.Map;
import java.util.Scanner;

public interface AdminService {

    Map<Integer, SlotMap> addLevel(Scanner scanner, Map<Integer,SlotMap> parkingSlots);
    Map<Integer, SlotMap> removeLevel(Scanner scanner, Map<Integer,SlotMap> parkingSlots);
}
