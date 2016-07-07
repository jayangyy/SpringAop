/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.security;

import cr.cdrb.web.edu.daointerface.IUserdao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.xdemo.example.springaop.service.LogService;

/**
 *
 * @author Jayang
 */
public class EduUserDetailsService implements UserDetailsService {

    protected static Log logger = LogService.getLog(EduUserDetailsService.class);
    @Resource(name = "dataSource1")
    private IUserdao userDAO;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        UserDetails user = null;

        try {

            // 搜索数据库以匹配用户登录名.
            // 我们可以通过dao使用JDBC来访问数据库
            //DbUser dbUser = userDAO.getDatabase(username);
            // Populate the Spring User object with details from the dbUser
            // Here we just pass the username, password, and access level
            // getAuthorities() will translate the access level to the correct
            // role type
            //user = new User(dbUser.getUsername(), dbUser.getPassword()
            ///.toLowerCase(), true, true, true, true,
            ///getAuthorities(dbUser.getAccess()));
        } catch (Exception e) {
            logger.error("Error in retrieving user");
            throw new UsernameNotFoundException("Error in retrieving user");
        }

        return user;
    }

    /**
     * 获得访问角色权限
     *
     * @param access
     * @return
     */
    public Collection<GrantedAuthority> getAuthorities(Integer access) {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

        // 所有的用户默认拥有ROLE_USER权限
        logger.debug("Grant ROLE_USER to this user");
        ///authList.add(new SimpleAuthority("ROLE_USER"));

        // 如果参数access为1.则拥有ROLE_ADMIN权限
        if (access.compareTo(1) == 0) {
            logger.debug("Grant ROLE_ADMIN to this user");
            //authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }

        return authList;
    }
}
