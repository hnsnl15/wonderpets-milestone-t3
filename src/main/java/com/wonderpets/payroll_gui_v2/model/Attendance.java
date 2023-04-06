package com.wonderpets.payroll_gui_v2.model;

public class Attendance {
    private String id;
    private String name;
    private String date;
    private String timeIn;
    private String timeOut;

    public Attendance(String id, String name, String date, String timeIn, String timeOut) {

        this.id = id.strip();
        this.name = name.strip();
        this.date = date.strip();
        this.timeIn = timeIn.strip();
        this.timeOut = timeOut.strip();
    }

    @Override
    public String toString() {
        return String.format("%8s %30s %20s %20s %20s",
                getId(), getName(), getDate(), getTimeIn(), getTimeOut());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

}