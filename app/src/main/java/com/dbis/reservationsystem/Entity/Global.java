package com.dbis.reservationsystem.Entity;

/**
 * Created by waho on 2016/3/25.
 * 用于在各个activity之间传递信息
 */
public class Global {
    public static String account;
    public static String password;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Global.account = account;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Global.password = password;
    }
}


