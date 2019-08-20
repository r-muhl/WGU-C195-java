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
public class City {
    private int cityID;
    private String city;
    private int countryID;

    public City(int cityID, String city, int countryID) {
        this.cityID = cityID;
        this.city = city;
        this.countryID = countryID;
    }

    public City(int cityID, String city) {
        this.cityID = cityID;
        this.city = city;
    }
    public City (String city) {
        this.city = city;
    }

    public City() {
        
    }
    
    /**
     * @return the cityID
     */
    public int getCityID() {
        return cityID;
    }

    /**
     * @param cityID the cityID to set
     */
    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID the countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    
    
}
