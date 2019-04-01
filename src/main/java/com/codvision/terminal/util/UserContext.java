package com.codvision.terminal.util;

import com.codvision.terminal.bean.user.User;

public class UserContext {

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void set(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}