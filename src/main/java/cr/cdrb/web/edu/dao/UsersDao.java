/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.dao;

import cr.cdrb.web.edu.daointerface.IUserdao;
import cr.cdrb.web.edu.domains.security.Users;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Jayang
 */
public class UsersDao implements IUserdao {

    @Resource(name = "dataSource1")
    private DataSource db;

    public Users GetUsers(String usernmae) {
        String sqlStr = "select U.username,U.password,S1.rolename from Users U "
                + "join user_roles  R on R.username=U.username "
                + "join roles S1 on S1.roleid=R.roleid where U1.username='user' "
                + "union  "
                + "select U1.username,U1.password,S.rolename from Users U1 "
                + " join group_members G on G.username=U1.username "
                + " join group_roles  R1 on R1.group_id=G.id "
                + " join roles S on S.roleid=R1.roleid  where U1.username='user'";
        
        return new Users();
    }
}
