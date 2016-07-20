/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.common;

import cr.cdrb.web.edu.daointerface.IAuthDao;
import cr.cdrb.web.edu.daointerface.IUserdao;
import cr.cdrb.web.edu.security.domains.ResRole;
import cr.cdrb.web.edu.security.domains.Resource;
import cr.cdrb.web.edu.security.domains.Role;
import java.util.List;
import org.springframework.stereotype.Repository;
import tools.SqlHelper;

/**
 *
 * @author Jayang
 */
@Repository
public class AuthorityDao implements IAuthDao {
    @javax.annotation.Resource
    private IUserdao userdao;
    @Override
    public List<Role> getAllAuthorityName() {
        String sqlStr = "select * from roles";
        return SqlHelper.executeList(Role.class, sqlStr);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Resource> getResource(String rolename) {
        String sqlStr = "select O.* from roles R\n"
                + "   join res_roles O on O.roleid=R.roleid\n"
                + "   where R.rolename='" + rolename + "'";
        return SqlHelper.executeList(Resource.class, sqlStr);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ResRole> getResRoles() {
        String sqlStr = "select * from res_roles";
        return SqlHelper.executeList(ResRole.class, sqlStr);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
