///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package cr.cdrb.web.edu.security;

import cr.cdrb.web.edu.dao.AuthorityDao;
import cr.cdrb.web.edu.dao.ResourceDao;
import cr.cdrb.web.edu.daointerface.IAuthDao;
import cr.cdrb.web.edu.security.domains.Role;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 *
 * @author jayan 权限元数据提供类
 */
public class EduInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {
////    @Resource(name = "AuthorityDao")
//
//    private AuthorityDao authDao;
////    @Resource(name = "ResourceDao")
//
//    public AuthorityDao getAuthDao() {
//        return authDao;
//    }
//
//    public void setAuthDao(AuthorityDao authDao) {
//        this.authDao = authDao;
//    }
//
//    public ResourceDao getResDao() {
//        return resDao;
//    }
//
//    public void setResDao(ResourceDao resDao) {
//        this.resDao = resDao;
//    }
//
//    private ResourceDao resDao;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    public EduInvocationSecurityMetadataSourceService(IAuthDao testdao) throws SQLException {
        super();
        //使用注解方式的话，只能在构造函数执行完成后才能获得实例
//        this.authDao = new AuthorityDao();
//        this.resDao = new ResourceDao();
//         setResDao(new ResourceDao());
//         setAuthDao(new AuthorityDao());
//        System.out.println("构造函数!");
        loadResourceDefine();
    }

    // 在Web服务器启动时，提取系统中的所有权限
    private void loadResourceDefine() {
        ResourceDao resDao = new ResourceDao();
        AuthorityDao authDao = new AuthorityDao();
        List<Role> query = authDao.getAllAuthorityName();//list<role>获取所有角色
        ///   List<ResRole> resroFleList = this.authDao.getResRoles();//list<role>获取所有角色与资源映射
        /*
         * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
         * sparta
         */
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        for (Role auth : query) {
            ConfigAttribute ca = new SecurityConfig(auth.getRolename());

            List<cr.cdrb.web.edu.security.domains.Resource> query1 = resDao.getResource(auth.getRolename());//list<resource>获取该角色所有资源
            for (cr.cdrb.web.edu.security.domains.Resource res : query1) {
                String url = res.getRes_url();
                /*
                 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
                 * sparta
                 */
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 根据URL，找到相关的权限配置。
    @Override
    @SuppressWarnings("empty-statement")
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        // object 是一个URL，被用户请求的url。
        String url = ((FilterInvocation) object).getRequestUrl();
        System.out.println("url" + url);
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (url.equals(resURL)) {
                return resourceMap.get(resURL);
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ///SecurityContextHolder.getContext().setAuthentication(auth);设置登录用户
        //throw new AccessDeniedException("无权限!");//对未加入权限的URL全部实施拦截
        return null;//未加入权限管理URL 暂时不拦截
    }

    @Override
    public boolean supports(Class<?> arg0) {

        return true;
    }
}
