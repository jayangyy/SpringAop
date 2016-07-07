/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.domains.security;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Jayang
 */
public class Users implements java.io.Serializable{

    //用户名
    private String username;
    //用户密码
    private String password;
    //用户角色集合
    private List<Role> roles;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
