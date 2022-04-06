package model;


/** This class controls the creation of Outsorced Parts
 RUNTIME ERROR
 An error I encountered was trying to reuse machineID for this class. Because machineID is a separate java datatype,
 I had to use an entirely different datatype and variable which is "String companyName"
 FUTURE ENHANCEMENT
 A future enhancement I could make is to create a hashmap of companyNames to parts so it would show companies that have
 multiple parts.
 */
public class OutsourcedPart extends Part {

    String companyName;
    /**
     @param id id
     @param name part name
     @param price part price
     @param stock part stock
     @param min part min
     @param max part max
     @param companyName part companyname
     This method instantiates an Outsourced part object.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }
    /**
     * @return companyName
     * This method gets the company name. */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyName
     * This method sets the company name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
