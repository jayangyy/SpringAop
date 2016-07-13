/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.daointerface;

import cr.cdrb.web.edu.domains.security.ResRole;
import cr.cdrb.web.edu.domains.security.Role;
import java.util.List;

/**
 *
 * @author Jayang
 */
public interface IAuthDao {

    public List<Role> getAllAuthorityName() ;

    public List<cr.cdrb.web.edu.domains.security.Resource> getResource(String rolename);
    
    ///获取所有RES_ROLE映射
    public List<ResRole> getResRoles();
    
}
