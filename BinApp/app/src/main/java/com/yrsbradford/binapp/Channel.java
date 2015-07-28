package com.yrsbradford.binapp;

/**
 * Created by 10askinsw on 28/07/2015.
 */
public enum Channel {

    GPS("GPS"), INIT("INIT"), AUTH("AUTH"), SAVE("SAVE"), RECIEVE("RECIEVE"), SEND("SEND"), NONE("NONE");

    private String name;

    private Channel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
