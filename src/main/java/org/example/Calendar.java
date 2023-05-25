package org.example;

public class Calendar {
    private Integer monthID;
    private Integer dateOfDay;
    private String dayOfWeek;
    private String location;
    private String genre;
    private Integer priceOfTicket;
    private String typeOfEvent;

    public Integer getMonthID() {
        return monthID;
    }

    public void setMonthID(Integer monthID) {
        this.monthID = monthID;
    }

    public Integer getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(Integer dateOfDay) {
        this.dateOfDay = dateOfDay;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPriceOfTicket() {
        return priceOfTicket;
    }

    public void setPriceOfTicket(Integer priceOfTicket) {
        this.priceOfTicket = priceOfTicket;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "monthID=" + monthID +
                ", dateOfDay=" + dateOfDay +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", location='" + location + '\'' +
                ", genre='" + genre + '\'' +
                ", priceOfTicket=" + priceOfTicket +
                ", typeOfEvent='" + typeOfEvent + '\'' +
                '}';
    }
}
