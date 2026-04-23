# Smart Campus Sensor & Room Management API

## Overview

This project is a RESTful API built using Jakarta REST (JAX-RS). It manages rooms, sensors, and sensor readings in a smart campus system. The API follows REST principles and uses in-memory storage (HashMap and ArrayList) instead of a database.

## Technology Stack

- Java
- Jakarta REST (JAX-RS)
- GlassFish
- Maven
- JSON

## API Base URL

http://localhost:8080/smart-campus-api/api/v1

## Project Structure

src/main/java/com/smartcampus
├── JakartaRestConfiguration.java
├── model
│   ├── ApiError.java
│   ├── Room.java
│   ├── Sensor.java
│   └── SensorReading.java
├── storage
│   └── DataStore.java
├── resources
│   ├── DiscoveryResource.java
│   ├── RoomResource.java
│   ├── SensorResource.java
│   └── SensorReadingResource.java
├── exceptions
│   ├── RoomNotFoundException.java
│   ├── RoomNotFoundExceptionMapper.java
│   ├── RoomNotEmptyException.java
│   ├── RoomNotEmptyExceptionMapper.java
│   ├── LinkedResourceNotFoundException.java
│   ├── LinkedResourceNotFoundExceptionMapper.java
│   ├── SensorNotFoundException.java
│   ├── SensorNotFoundExceptionMapper.java
│   ├── SensorUnavailableException.java
│   ├── SensorUnavailableExceptionMapper.java
│   └── GlobalExceptionMapper.java
└── filters
    └── ApiLoggingFilter.java

## How to Build and Run

1. Open the project in NetBeans
2. Make sure GlassFish is selected as the server
3. Press F6 to run the project
4. Open browser:

http://localhost:8080/smart-campus-api/api/v1

## Main Endpoints

GET /api/v1  
GET /api/v1/rooms  
POST /api/v1/rooms  
GET /api/v1/rooms/{id}  
DELETE /api/v1/rooms/{id}  

GET /api/v1/sensors  
GET /api/v1/sensors?type=Temperature  
POST /api/v1/sensors  

GET /api/v1/sensors/{id}/readings  
POST /api/v1/sensors/{id}/readings  

## Sample curl Commands

### Get all rooms
curl http://localhost:8080/smart-campus-api/api/v1/rooms

### Create a room
curl -X POST http://localhost:8080/smart-campus-api/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"SCI-201\",\"name\":\"Science Lab\",\"capacity\":60}"

### Get sensors
curl http://localhost:8080/smart-campus-api/api/v1/sensors

### Create sensor
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"CO2-001\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":415.0,\"roomId\":\"ENG-101\"}"

### Add reading
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\":24.7}"

## Part 1 – JAX-RS Lifecycle

JAX-RS creates a new resource instance per request. This avoids shared state issues inside resource classes. However, this project uses shared HashMaps, so care must be taken as multiple requests can still modify shared data.

## Part 1 – Hypermedia

Hypermedia allows APIs to include links in responses. This helps clients discover endpoints dynamically instead of relying on documentation, making the API more flexible.

## Part 2 – Room Design

Returning full room objects is better than just IDs because it reduces extra requests and improves usability.

DELETE is idempotent because repeating the same delete does not change the system further after the first successful removal.

## Part 3 – Sensors

@Consumes(JSON) ensures the API only accepts JSON input.

Query parameters (e.g. ?type=Temperature) are better for filtering because they clearly indicate filtering rather than new resources.

## Part 4 – Sub-Resource

The readings endpoint is implemented using a sub-resource locator. This keeps code modular and separates sensor logic from reading logic.

Posting a reading updates the sensor’s currentValue to maintain consistency.

## Part 5 – Error Handling

422 is used when a request is valid but contains invalid references (e.g. non-existing room).

Returning stack traces is dangerous because it exposes internal system details.

A global exception mapper returns safe 500 errors.

## Logging

A filter logs all incoming requests and outgoing responses. This avoids repeating logging code in every class.

## Conclusion

This project demonstrates a fully functional RESTful API using JAX-RS, implementing REST principles, proper HTTP methods, structured JSON responses, filtering, nested resources, error handling, and logging while using in-memory storage.