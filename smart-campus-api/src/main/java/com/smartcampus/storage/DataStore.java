package com.smartcampus.storage;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
    public static Map<String, List<SensorReading>> readings = new HashMap<>();

    static {
        rooms.put("LIB-301", new Room("LIB-301", "Library Quiet Study", 120));
        rooms.put("ENG-101", new Room("ENG-101", "Engineering Lab", 40));

        sensors.put("TEMP-001", new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.5, "LIB-301"));
        rooms.get("LIB-301").getSensorIds().add("TEMP-001");

        readings.put("TEMP-001", new ArrayList<>());
    }
}