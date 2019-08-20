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
public class ApptPerDayReport {
    private String date;
    private String consultant;
    private String num;

    public ApptPerDayReport(String date, String consultant, String num) {
        this.date = date;
        this.consultant = consultant;
        this.num = num;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the consultant
     */
    public String getConsultant() {
        return consultant;
    }

    /**
     * @param consultant the consultant to set
     */
    public void setConsultant(String consultant) {
        this.consultant = consultant;
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
