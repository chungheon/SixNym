package com.looting.sixnym;

import java.util.ArrayList;

public class SocketHandler {
    private static MultiplayerConnect.SendReceive sr;
    private static ArrayList<MultiplayerConnect.SendReceive> srs = new ArrayList<>();

    public static synchronized MultiplayerConnect.SendReceive getSocket(){
        return sr;
    }

    public static synchronized int getSize(){
        return srs.size();
    }

    public static synchronized MultiplayerConnect.SendReceive getSocket(int index){
        return srs.get(index);
    }

    public static synchronized void setSocket(MultiplayerConnect.SendReceive socket){
        SocketHandler.sr = socket;
    }

    public static synchronized void addSocket(MultiplayerConnect.SendReceive socket){
        SocketHandler.srs.add(socket);
    }
}
