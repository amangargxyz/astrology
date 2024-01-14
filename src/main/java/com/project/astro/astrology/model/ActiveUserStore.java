package com.project.astro.astrology.model;

import java.util.HashMap;
import java.util.Map;

public class ActiveUserStore {

    public Map<Long, ERole> users;

    public ActiveUserStore() {
        users = new HashMap<>();
    }

    // standard getter and setter
    public Map<Long, ERole> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, ERole> users) {
        this.users = users;
    }
}
