package com.wonderpets.payroll_gui_v2.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private int id;
    private String name;
    private LocalDate date;
    private String timeIn;
    private String timeOut;

    public Attendance(String id, String name, String date, String timeIn, String timeOut) {

        this.id = Integer.parseInt(id.strip());
        this.name = name.strip();
        this.date = stringToLocalDate(date.strip());
        this.timeIn = timeIn.strip();
        this.timeOut = timeOut.strip();
    }

    public Attendance(String timeIn, String timeOut) {

        this.id = (int) ((Math.random() * 9) + (Math.random() * 9) + 10000);
        this.name = "Anon";
        this.date = stringToLocalDate("1/1/23");
        this.timeIn = timeIn.strip();
        this.timeOut = timeOut.strip();
    }

    public static LocalDate stringToLocalDate(String dateString) {
        LocalDate date = null;
        // Define the patterns used in the date strings
        String[] patterns = {"M/d/yyyy", "M/d/yy", "M/dd/yyyy", "yyyy-M-dd"};
        // Loop through each pattern and parse it into a LocalDate object
        for (String pattern : patterns) {
            try {
                // Attempt to parse the date string with the current pattern
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                date = LocalDate.parse(dateString, formatter);
                break; // Break out of the loop if parsing is successful
            } catch (Exception e) {
                // Ignore exceptions and continue to the next pattern
            }
        }
        return date;
    }

    public int getHoursWorkedPerDay() {
        // Define a DateTimeFormatter for the time pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        // Parse the input string into LocalTime objects
        LocalTime localTime1 = LocalTime.parse(timeIn, formatter);
        LocalTime localTime2 = LocalTime.parse(timeOut, formatter);

        // Extract the hour part from the LocalTime objects
        int hour1 = localTime1.getHour();
        int hour2 = localTime2.getHour();

        return hour2 - hour1;
    }

    @Override
    public String toString() {
        return String.format("%8s %30s %20s %20s %20s",
                getId(), getName(), getDate(), getTimeIn(), getTimeOut());
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String date) {
        this.date = stringToLocalDate(date);
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