package com.ssonsh.domain;

public enum Size {
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");

    private String text;
    Size(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
