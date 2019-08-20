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
public class Type {
    private int typeID;
    private String type;

    public Type(int typeID, String type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public Type(String type) {
        this.type = type;
    }

    public Type() {
        
    }
    /**
     * @return the typeID
     */
    public int getTypeID() {
        return typeID;
    }

    /**
     * @param typeID the typeID to set
     */
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    /**
     * @return the type
     */
    public String getTheType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setTheType(String type) {
        this.type = type;
    }
}
