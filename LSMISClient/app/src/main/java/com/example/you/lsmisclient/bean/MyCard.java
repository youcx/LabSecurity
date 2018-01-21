package com.example.you.lsmisclient.bean;

/**
 * Created by You on 2018/1/16.
 */

public class MyCard {
    private int icon;
    private String card_name;

    public MyCard(String card_name, int icon) {
        this.card_name = card_name;
        this.icon = icon;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
