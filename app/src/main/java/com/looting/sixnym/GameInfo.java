package com.looting.sixnym;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    public String msgProtocol;
    private String msg;
    public GameInfo(String msgProtocol, String msg) {
        this.msgProtocol = msgProtocol;
        this.msg = msg;
    }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(){
        return this.msg;
    }
}
