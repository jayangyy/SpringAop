/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.cdrb.web.edu.daointerface;

import cr.cdrb.web.edu.security.domains.Resource;
import java.util.List;

/**
 *
 * @author Jayang
 */
public interface IResourceDao {
    public List<Resource> getResource(String rolename);
}
