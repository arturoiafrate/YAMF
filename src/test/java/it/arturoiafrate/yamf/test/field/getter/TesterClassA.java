package it.arturoiafrate.yamf.test.field.getter;

public class TesterClassA {
    private Integer integer;
    private Boolean aBoolean;
    private String string;
    private int primitiveInteger;

    public TesterClassA(Integer integer, Boolean aBoolean, String string, int primitiveInteger) {
        this.integer = integer;
        this.aBoolean = aBoolean;
        this.string = string;
        this.primitiveInteger = primitiveInteger;
    }

    public TesterClassA() {}

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getPrimitiveInteger() {
        return primitiveInteger;
    }

    public void setPrimitiveInteger(int primitiveInteger) {
        this.primitiveInteger = primitiveInteger;
    }
}
