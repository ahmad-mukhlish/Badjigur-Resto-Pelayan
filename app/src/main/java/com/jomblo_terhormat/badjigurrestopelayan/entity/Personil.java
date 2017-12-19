package com.jomblo_terhormat.badjigurrestopelayan.entity;

/**
 * Created by GOODWARE1 on 12/19/2017.
 */

public class Personil {

    private int avatar = 0;
    private String name;
    private String role;

    public Personil(int avatar, String name, String role) {
        this.avatar = avatar;
        this.name = name;
        this.role = role;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
