package com.dbis.reservationsystem.Entity;

/**
 * Created by waho on 2016/3/25.
 * 用于在各个activity之间传递信息
 */
public class Teacher {
    public static String account;
    public static String password;
    public static String name;
    public static String id;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Teacher.account = account;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Teacher.password = password;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Teacher.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Teacher.id = id;
    }
}


