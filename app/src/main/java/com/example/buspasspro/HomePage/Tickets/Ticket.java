package com.example.buspasspro.HomePage.Tickets;
public class Ticket {

    private String companyName;
    private String ticketPrice;
    private String destination;
    private String startingLocation;
    private String ticketID;
    private String departureTime;
    private String busId;
    private Float rating;
    private String userId;

    public Ticket() {
        // Default constructor required for Firestore
    }

    public Ticket(String destination, String departureTime, String ticketPrice, String ticketID, String companyName, String startingLocation,String busId,Float rating,String userId) {
        this.destination = destination;
        this.companyName = companyName;
        this.departureTime = departureTime;
        this.ticketID = ticketID;
        this.ticketPrice = ticketPrice;
        this.startingLocation = startingLocation;
        this.busId = busId;
        this.rating = rating;
        this.userId = userId;
    }


    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    //--------------------------------------------------------------------
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    //---------------------------------------------------------------------------
    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    //--------------------------------------------------------------------------
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
//----------------------------------------------------------------------------

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDpartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    //-------------------------------------------------------------------------------
    public String getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }
    //-------------------------------------------------------------------------------
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }
    //---------------------------------------------------------------------------

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
