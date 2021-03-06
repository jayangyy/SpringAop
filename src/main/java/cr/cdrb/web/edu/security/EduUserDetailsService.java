/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.security;

import cr.cdrb.web.edu.daointerface.IUserdao;
import cr.cdrb.web.edu.security.domains.Role;
import cr.cdrb.web.edu.security.domains.Users;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xdemo.example.springaop.service.LogService;

/**
 *
 * @author Jayang Seurity数据库用户提供程序
 */
public class EduUserDetailsService implements UserDetailsService {
    
    protected static Log logger = LogService.getLog(EduUserDetailsService.class);
    @Autowired
    private IUserdao userDAO;
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        UserDetails user = null;
        Users dbUser = userDAO.GetUsers(username);
        if (dbUser == null) {
            throw new UsernameNotFoundException("未匹配到用户");
        }
//        user = new User(dbUser.getUsername(), dbUser.getPassword()
//                .toLowerCase(), true, true, true, true,
//                getAuthorities(dbUser.getRoles()));
        dbUser.setAccountNonExpired(true);
        dbUser.setAuthorities(getAuthorities(dbUser.getRoles()));
        dbUser.setCredentialsNonExpired(true);
        dbUser.setAccountNonLocked(true);
        return dbUser;
    }

    /**
     * 获得访问角色权限
     *
     * @param access
     * @return
     */
    public Collection<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        // 所有的用户默认拥有ROLE_USER权限
        logger.debug("组装用户权限!");
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
        if (roles != null) {
            for (Role role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
            }
        }
        return grantedAuthorities;
    }
}
