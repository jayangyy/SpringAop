/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.dao;

import cr.cdrb.web.edu.daointerface.IUserdao;
import cr.cdrb.web.edu.security.domains.Role;
import cr.cdrb.web.edu.security.domains.Users;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import tools.SqlHelper;

/**
 *
 * @author Jayang
 */
@Repository
public class UsersDao implements IUserdao {

//    @Resource(name = "dataSource")
//    private DataSource db;
    public Users GetUsers(String usernmae) {
        Users endUser = new Users();
        String sqlStr = "select U.username,U.password,S1.rolename,S1.roleid,S1.rolecmt from Users U "
                + " left join user_roles  R on R.username=U.username "
                + " left join roles S1 on S1.roleid=R.roleid where U.username='" + usernmae + "' "
                + "union  "
                + " select U1.username,U1.password,S.rolename,S.roleid,S.rolecmt from Users U1 "
                + " left join group_members G on G.username=U1.username "
                + " left join group_roles  R1 on R1.group_id=G.id "
                + " left join roles S on S.roleid=R1.roleid  where U1.username='" + usernmae + "'";
        List<Users> userList = SqlHelper.executeList(Users.class, sqlStr);
        if (userList.size() == 0) {
            throw new UsernameNotFoundException("未匹配到用户");
        }
        List<Role> roles = new ArrayList<Role>();
        boolean ismap = true;
        for (Users user : userList) {
            if (ismap) {
                endUser.setPassword(user.getPassword());
                endUser.setUsername(user.getUsername());
                ismap = false;
            }
            Role role = new Role();
            role.setRoleid(user.getRoleid());
            role.setRolename(user.getRolename());
            role.setRolecmt(user.getRolecmt());
            roles.add(role);
        }
        endUser.setRoles(roles);
        return endUser;
    }
}
