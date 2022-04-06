package model;

/** This class instantiates Inhouse Parts
 RUNTIME ERROR
 An error I encountered was trying to set the Inhouse Part attributes directly in this class instead of in the super class.
 I fixed this by using the super() call to pass the variables to the Parent class "Part"
 FUTURE ENHANCEMENT
 A future enhancement I could make is to add more details to inhouse part , such as department, POC, etc.
 */
public class InHouse extends Part {

    private int machineID;
    /**
     @param id part id
     @param name part name
     @param price part price
     @param stock part inventory
     @param min part minimum
     @param max part maximum
     @param machineID machineID
     This method instantiates an InHouse part object.
     */
    public InHouse(int id, String name, double price,int stock, int min, int max, int machineID) {

        super(id, name, price, stock, min, max);
        this.machineID = machineID;

    }

    /** This method gets the machine id.
     * @return machineID
     */
    public int getMachineID() {
        return machineID;
    }
    /** This method sets the machine id.
     * @param machineID machineID
     * */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
