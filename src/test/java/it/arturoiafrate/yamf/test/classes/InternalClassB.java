package it.arturoiafrate.yamf.test.classes;

public class InternalClassB {
    private String string;
    private Double aDouble;

    public InternalClassB(){
        string = "This is the 2nd internal string level";
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }
}
