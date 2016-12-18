package com.smie.project;

import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by zackzhao on 2016/12/8.
 */

public class MenuPersonItem {
    private String menu_programId;
    private int menu_personItemId;
    private int menu_location_icon;
    private String menu_personName;
    private String menu_personAddress;
    private String menu_evaluate;
    private float menu_evaluta_bar;
    private String menu_personDescription;
    private String menu_go_to_connect;

    public MenuPersonItem(String menu_programId,
                          int menu_personItemId,
                          int menu_location_icon,
                          String menu_personName,
                          String menu_personAddress,
                          String menu_evaluate,
                          float menu_evaluta_bar,
                          String menu_personDescription,
                          String menu_go_to_connect){
        this.menu_programId = menu_programId;
        this.menu_personItemId = menu_personItemId;
        this.menu_location_icon = menu_location_icon;
        this.menu_personName = menu_personName;
        this.menu_personAddress = menu_personAddress;
        this.menu_evaluate = menu_evaluate;
        this.menu_evaluta_bar = menu_evaluta_bar;
        this.menu_personDescription = menu_personDescription;
        this.menu_go_to_connect = menu_go_to_connect;

    }

    public int getMenu_personItemId(){
        return menu_personItemId;
    }
    public int getMenu_location_icon(){
        return menu_location_icon;
    }
    public String getMenu_personName(){
        return menu_personName;
    }
    public String getMenu_personAddress(){
        return menu_personAddress;
    }
    public String getMenu_evaluate(){
        return menu_evaluate;
    }
    public float getMenu_evaluta_bar(){
        return menu_evaluta_bar;
    }
    public String getMenu_personDescription(){
        return menu_personDescription;
    }
    public String getMenu_go_to_connect(){
        return menu_go_to_connect;
    }
    public String getMenu_programId(){return  menu_programId;}
}
