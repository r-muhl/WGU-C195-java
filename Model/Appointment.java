/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertmuhlesteinsoftware2.Model;


/**
 *
 * @author robmu
 */
public class Appointment {
    
    private int appointmentID;
    private Customer customer;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String user;
    private Type type;
    
    
    public Appointment(int appointmentID, Customer customer, String title, Type type, String description, String startTime, String endTime, String user) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    public Appointment(Customer customer, Type type, String description, String startTime, String endTime, String consultant) {
        this.customer = customer;
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = consultant;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Appointment(String startTime, String endTime, String user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    /**
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @param appointmentID the appointmentID to set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    
}
