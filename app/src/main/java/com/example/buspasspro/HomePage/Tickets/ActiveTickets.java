package com.example.buspasspro.HomePage.Tickets;
public class ActiveTickets {

    private String companyName;
    private String ticketPrice;
    private String destination;
    private String startingLocation;
    private String ticketID;
    private String departureTime;
    private String busId;
    private String busLicensePlate;
    private String qrCodeUrl;
    private String selectedSeat;
    private String selectedDate;
    private String ticketStatus;
    private String userId;


    public ActiveTickets() {
        // Default constructor required for Firestore
    }

    public ActiveTickets(String destination, String departureTime, String ticketPrice, String ticketID, String companyName, String startingLocation,String busId,String busLicensePlate,String qrCodeUrl,String  selectedSeat,String selectedDate,String ticketStatus,String userId) {
        this.destination = destination;
        this.companyName = companyName;
        this.departureTime = departureTime;
        this.ticketID = ticketID;
        this.ticketPrice = ticketPrice;
        this.startingLocation = startingLocation;
        this.busId = busId;
        this.busLicensePlate = busLicensePlate;
        this.qrCodeUrl = qrCodeUrl;
        this. selectedSeat =  selectedSeat;
        this.selectedDate = selectedDate;
        this.ticketStatus = ticketStatus;
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
    //--------------------------------------------------------------------------------

    public String getBusLicensePlate() {
        return busLicensePlate;
    }
    public  void setBusLicensePlate(String busLicensePlate){
        this.busLicensePlate = busLicensePlate;
    }
    //----------------------------------------------------------------------------------

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
    //-----------------------------------------------------------------------------

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(String selectedSeat) {
        this.selectedSeat = selectedSeat;
    }
    //----------------------------------------------------------------------------------

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}