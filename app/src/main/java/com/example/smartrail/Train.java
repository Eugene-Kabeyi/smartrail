package com.example.smartrail;

public class Train {
    private String trainName;
    private String departureTime;
    private String arrivalTime;
    private String date;
    private String seats;
    private String price;
    private String imageUrl;

    public Train(String trainName, String departureTime, String arrivalTime, String date, String seats, String price, String imageUrl) {
        this.trainName = trainName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.date = date;
        this.seats = seats;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Train() {} // Empty constructor for Firebase

    // Getter methods
    public String getTrainName() {
        return trainName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDate() {
        return date;
    }

    public String getSeats() {
        return seats;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
