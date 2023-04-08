package com.wonderpets.payroll_gui_v2.model;

import java.math.BigDecimal;
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

    public Employee(String lastName, String firstName, int id, String birthday, String address, String phoneNumber, String status, String position, String immediateSupervisor, Account governmentAccounts, Benefit benefit) {
        super(lastName, firstName);
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

    //Method on getting the SSS contribution using condition
    public static double calculateSSSContribution(BigDecimal wage) {
        // "con" short for condition
        double salary = wage.doubleValue();
        boolean con1 = salary <= 3250;
        boolean con2 = salary > 3250 && salary <= 3750;
        boolean con3 = salary > 3750 && salary <= 4250;
        boolean con4 = salary > 4250 && salary <= 4750;
        boolean con5 = salary > 4750 && salary <= 5250;
        boolean con6 = salary > 5250 && salary <= 5750;
        boolean con7 = salary > 5750 && salary <= 6250;
        boolean con8 = salary > 6250 && salary <= 6750;
        boolean con9 = salary > 6750 && salary <= 7250;
        boolean con10 = salary > 7250 && salary <= 7750;
        boolean con11 = salary > 7750 && salary <= 8250;
        boolean con12 = salary > 8250 && salary <= 8750;
        boolean con13 = salary > 8750 && salary <= 9250;
        boolean con14 = salary > 9250 && salary <= 9750;
        boolean con15 = salary > 9750 && salary <= 10250;
        boolean con16 = salary > 10250 && salary <= 10750;
        boolean con17 = salary > 10750 && salary <= 11250;
        boolean con18 = salary > 11250 && salary <= 11750;
        boolean con19 = salary > 11750 && salary <= 12250;
        boolean con20 = salary > 12250 && salary <= 12750;
        boolean con21 = salary > 12750 && salary <= 13250;
        boolean con22 = salary > 13250 && salary <= 13750;
        boolean con23 = salary > 13750 && salary <= 14250;
        boolean con24 = salary > 14250 && salary <= 14750;
        boolean con25 = salary > 14750 && salary <= 15250;
        boolean con26 = salary > 15250 && salary <= 15750;
        boolean con27 = salary > 15750 && salary <= 16250;
        boolean con28 = salary > 16250 && salary <= 16750;
        boolean con29 = salary > 16750 && salary <= 17250;
        boolean con30 = salary > 17250 && salary <= 17750;
        boolean con31 = salary > 17750 && salary <= 18250;
        boolean con32 = salary > 18250 && salary <= 18750;
        boolean con33 = salary > 18750 && salary <= 19250;
        boolean con34 = salary > 19250 && salary <= 19750;
        boolean con35 = salary > 19750 && salary <= 20250;
        boolean con36 = salary > 20250 && salary <= 20750;
        boolean con37 = salary > 20750 && salary <= 21250;
        boolean con38 = salary > 21250 && salary <= 21750;
        boolean con39 = salary > 21750 && salary <= 22250;
        boolean con40 = salary > 22250 && salary <= 22750;
        boolean con41 = salary > 22750 && salary <= 23250;
        boolean con42 = salary > 23250 && salary <= 23750;
        boolean con43 = salary > 23750 && salary <= 24250;
        boolean con44 = salary > 24250 && salary <= 24750;

        if (con1) return 135.00;
        else if (con2) return 157.50;
        else if (con3) return 180.00;
        else if (con4) return 202.50;
        else if (con5) return 225.00;
        else if (con6) return 247.50;
        else if (con7) return 315.00;
        else if (con8) return 337.50;
        else if (con9) return 360.00;
        else if (con10) return 382.50;
        else if (con11) return 405.00;
        else if (con12) return 427.50;
        else if (con13) return 450.00;
        else if (con14) return 472.50;
        else if (con15) return 495.00;
        else if (con16) return 517.50;
        else if (con17) return 540.00;
        else if (con18) return 562.50;
        else if (con19) return 585.00;
        else if (con20) return 607.50;
        else if (con21) return 630.00;
        else if (con22) return 652.50;
        else if (con23) return 697.50;
        else if (con24) return 720.00;
        else if (con25) return 742.50;
        else if (con26) return 765.00;
        else if (con27) return 787.50;
        else if (con28) return 810.00;
        else if (con29) return 832.50;
        else if (con30) return 855.00;
        else if (con31) return 877.50;
        else if (con32) return 900.00;
        else if (con33) return 922.50;
        else if (con34) return 945.50;
        else if (con35) return 990.00;
        else if (con36) return 1012.50;
        else if (con37) return 1035.00;
        else if (con38) return 1057.50;
        else if (con39) return 1080.00;
        else if (con40) return 1012.50;
        else if (con41) return 1035.00;
        else if (con42) return 1057.50;
        else if (con43) return 1080.00;
        else if (con44) return 1102.50;

        return 1125.00;
    }

    //method on getting Philhealth Contribution with arithmetic functions
    public static double calculatePhilhealthContribution(BigDecimal monthlySalary) {
        return (monthlySalary.doubleValue() * 0.03) / 2;
    }

    //method on getting Pagibig Contribution  with arithmetic functions
    public static double calculatePagibigContribution(BigDecimal monthlySalary) {
        return monthlySalary.doubleValue() < 1500 ? monthlySalary.doubleValue() * 0.01 : 100;
    }

    //Method on Getting the With holding tax using else if
    public static double calculateTax(BigDecimal monthlySalary, double SSS, double pagibig, double philhealth) {
        double salary = monthlySalary.doubleValue() - (SSS + pagibig + philhealth),
                taxRate = 0;
        if (salary > 20832 && salary <= 33333) {
            taxRate = (salary - 20833) * 0.20;
        } else if (salary > 33333 && salary <= 66667) {
            taxRate = 2500 + ((salary - 33333) * 0.25);
        } else if (salary > 66667 && salary <= 166667) {
            taxRate = 10833 + ((salary - 66667) * 0.30);
        } else if (salary > 166667 && salary <= 666667) {
            taxRate = 166667 + ((salary - 40833.33) * 0.32);
        } else if (salary > 666667) {
            taxRate = 200833.33 + ((salary - 666667) * 0.35);
        }
        return taxRate;
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
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
