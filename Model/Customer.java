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
public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private City city;
    private String country;
    private String ZIPCode;
    private String customerPhoneNumber;
    public static int customerIDCounter = 1;
   

    public Customer(int customerID, String customerName, String customerAddress, City city, String country, String ZIPCode, String customerPhoneNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.city = city;
        this.country = country;
        this.ZIPCode = ZIPCode;
        this.customerPhoneNumber = customerPhoneNumber;
   }
    
    public Customer (String customerName, String customerAddress, String customerPhoneNumber) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Customer(int customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
    }

    public Customer() {
        
    }

       
    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the ZIPCode
     */
    public String getZIPCode() {
        return ZIPCode;
    }

    /**
     * @param ZIPCode the ZIPCode to set
     */
    public void setZIPCode(String ZIPCode) {
        this.ZIPCode = ZIPCode;
    }

    /**
     * @return the customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * @param customerPhoneNumber the customerPhoneNumber to set
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }
    
    public int getCityID (City object) {
        return object.getCityID();
    }
    
}
