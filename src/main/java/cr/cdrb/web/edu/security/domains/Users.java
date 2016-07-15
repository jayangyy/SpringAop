/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.security.domains;

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
    private String rolename;
    private Integer roleid;
    private String rolecmt;

    public String getRolecmt() {
        return rolecmt;
    }

    public void setRolecmt(String rolecmt) {
        this.rolecmt = rolecmt;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRole() {
        return rolename;
    }

    public void setRole(String rolename) {
        this.rolename = rolename;
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
