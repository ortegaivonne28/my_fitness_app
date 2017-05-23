package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/23/17.
 */

public class Friend {

    private String name;
    private String userId;
    private String friendId;
    private String email;


    public Friend(String name, String userId, String friendId, String email) {
        this.name = name;
        this.userId = userId;
        this.friendId = friendId;
        this.email = email;
    }

    public Friend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
