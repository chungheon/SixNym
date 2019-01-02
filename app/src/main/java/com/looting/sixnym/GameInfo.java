package com.looting.sixnym;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    public String msgProtocol;
    private ArrayList<CardRow> cardRows;
    private Card cardPlayed;
    private ArrayList<Integer> points;
}
