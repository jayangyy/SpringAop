/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.dao;

import cr.cdrb.web.edu.daointerface.IResourceDao;
import cr.cdrb.web.edu.domains.security.Resource;
import java.util.List;
import org.springframework.stereotype.Repository;
import tools.SqlHelper;

/**
 *
 * @author Jayang
 */
@Repository
public class ResourceDao implements IResourceDao {

    @Override
    public List<Resource> getResource(String rolename) {
        String sqlStr = "select O.* from roles R\n"
                + "   join res_roles O on O.roleid=R.roleid\n"
                + "   where R.rolename='" + rolename + "'";
        return SqlHelper.executeList(Resource.class, sqlStr);
    }

}
