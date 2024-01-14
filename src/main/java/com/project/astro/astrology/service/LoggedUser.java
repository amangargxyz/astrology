package com.project.astro.astrology.service;

import com.project.astro.astrology.model.ActiveUserStore;
import com.project.astro.astrology.model.ERole;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class LoggedUser implements HttpSessionBindingListener, Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private Long userId;
    private ERole role;
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username, Long userId, ERole role, ActiveUserStore activeUserStore) {
        this.username = username;
        this.userId = userId;
        this.role = role;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {}

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        Map<Long, ERole> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user.role);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        Map<Long, ERole> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users.containsKey(user.getUserId())) {
            users.remove(user.getUserId());
        }
    }

    // standard getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}
