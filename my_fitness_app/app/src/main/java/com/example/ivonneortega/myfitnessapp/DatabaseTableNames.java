package com.example.ivonneortega.myfitnessapp;

import java.util.ArrayList;

/**
 * Created by ivonneortega on 5/18/17.
 */

public interface DatabaseTableNames {

    public static final String USER = "users";
    public static final String ROUTINES = "routines";
    public static final String CHALLENGES = "challenges";
    public static final String SINGLE = "single";
    public static final String SUPER = "super";
    public static final String TRIPLE = "triple";

    public static final String CHALLENGE_USER = "user";
    public static final String CHALLENGE_FRIEND = "friend";

    public static final String[] LIST_OF_DAYS = new String[]{"Monday","Tuesday","Wednesday","Thrusday","Friday","Saturday","Sunday"};
    public static final String[] LIST_OF_DAYS_THREE_LETTERS = new String[]{"Mon","Tue","Wed","Thu","Fri","Sat","Sun","Mon"};


    public static final String PENDING = "pending";
    public static final String ACCEPTED = "accepted";
    public static final String REJECTED = "rejected";
    public static final String APPROVED = "approved";
    public static final String COMPLETED = "completed";


    public static final int INDIVIDUAL_WORKOUT = -1;
    public static final int CHALLENGE_WORKOUT = -2;

    public static final String NOTIFICATION_USER_ADD_AS_FRIEND = "user_add_as_friend";

}
