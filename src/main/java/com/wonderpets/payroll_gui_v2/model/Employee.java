package com.wonderpets.payroll_gui_v2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Employee extends User {

    private int id;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private String status;
    private String position;
    private String immediateSupervisor;
    private Account governmentAccounts;
    private Benefit benefit;

    public Employee(String username, String password, String lastName, String firstName, int id, String birthday, String address, String phoneNumber, String status, String position, String immediateSupervisor, Account governmentAccounts, Benefit benefit) {
        super(username, password, lastName, firstName);
        this.id = id;
        this.birthday = stringToLocalDate(birthday);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.governmentAccounts = governmentAccounts;
        this.benefit = benefit;
    }

    private static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(dateString, formatter);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public Account getGovernmentAccounts() {
        return governmentAccounts;
    }

    public void setGovernmentAccounts(Account governmentAccounts) {
        this.governmentAccounts = governmentAccounts;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }
}
