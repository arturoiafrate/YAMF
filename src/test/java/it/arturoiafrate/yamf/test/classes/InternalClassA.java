package it.arturoiafrate.yamf.test.classes;

public class InternalClassA {
    private String string;
    private Double aDouble;
    private InternalClassB internal2;

    public InternalClassA(){
        internal2 = new InternalClassB();
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

    public String getInternal2String(){
        return internal2.getString();
    }
}
