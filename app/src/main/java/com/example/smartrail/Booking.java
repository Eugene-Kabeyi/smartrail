package com.example.smartrail;

public class Booking {
    private String bookingId;
    private String trainName;
    private String departureTime;
    private String arrivalTime;
    private String date;
    private String price;
    private String passengerName;
    private int numberOfPassengers; // Change to int for easier calculations
    private double totalCost; // New field for total cost

    // Default constructor (required for Firebase)
    public Booking() {
    }

    // Constructor
    public Booking(String bookingId, String trainName, String departureTime, String arrivalTime, String date,
                   String price, String passengerName, int numberOfPassengers, double totalCost) {
        this.bookingId = bookingId;
        this.trainName = trainName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.date = date;
        this.price = price;
        this.passengerName = passengerName;
        this.numberOfPassengers = numberOfPassengers; // Store as integer
        this.totalCost = totalCost; // Initialize total cost
    }

    // Getters and setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers; // Return as integer
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers; // Store as integer
    }

    public double getTotalCost() { // New getter
        return totalCost;
    }

    public void setTotalCost(double totalCost) { // New setter
        this.totalCost = totalCost;
    }
}
