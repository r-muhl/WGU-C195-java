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
public class AppointmentTypeReport {
    
    private String month;
    private String type;
    private String num;

    public AppointmentTypeReport(String month, String type, String num) {
        this.month = month;
        this.type = type;
        this.num = num;
    }
    
    public AppointmentTypeReport (String type, String num) {
        this.type = type;
        this.num = num;
    }
    

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the num
     */
    public String getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(String num) {
        this.num = num;
    }
    
    
    
}
