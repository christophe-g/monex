package org.exist.atmosphere;


public class Message {


    private String value;

    public Message() {

    }


    public String getMessage() {
        return "funktioniert";
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
