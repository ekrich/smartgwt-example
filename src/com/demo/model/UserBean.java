package com.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.demo.entity.CreateAudit;
import com.demo.entity.Role;
import com.demo.entity.RoleName;
import com.demo.entity.UpdateAudit;
import com.demo.entity.User;
import com.demo.util.LoggerFactory;
import com.demo.vo.Tuple2;

/**
 * Service to deal with Users and Roles etc.
 * @PermitAll is on the class level - remove if adding
 * a security domain or configuring in the container to
 * allow.
 * <br>Ref: <a href="https://docs.jboss.org/author/display/WFLY8/Securing+EJBs">
 * https://docs.jboss.org/author/display/WFLY8/Securing+EJBs</a>
 * 
 * @author ekr
 *
 */
@PermitAll
@Stateless
@LocalBean
public class UserBean extends BaseJpaBean {
	
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.make();
	
	/**
	 * Find if person is User in the system. Used for update (active or inactive).
	 * @param employeeId - directory services id
	 * @return the User object or null if not found.
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public User findUser(String employeeId) {
		Query q = em.createNamedQuery("User.findByEmployeeId");
		q.setParameter("employeeId", employeeId);	
		User user = null;
		try {
			user = (User)q.getSingleResult();
		} catch (NoResultException e) {
		  // should not happen because j_security_check looks for Principle
		  throw new IllegalArgumentException("User: " + employeeId + " not found.");
		}
		return user;
	}
	
	 /**
   * Find if person is User in the system.
   * @param id - the system user id
   * @return the User object or exception thrown if not found.
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public User findUser(Long id) {
    User user = null;
    try {
      user = em.find(User.class, id);
    } catch (NoResultException e) {
      // should not happen
      throw new IllegalArgumentException("User by Id: " + id + " not found.");
    }
    return user;
  }
	
	/**
	 * Find if person is active User in the system. Used for login.
	 * @param employeeId - directory services id (aka peoplesoft id)
	 * @return the User object or null if not found.
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public User findActiveUser(String employeeId) {
		Query q = em.createNamedQuery("User.findByEmployeeIdActive");
		q.setParameter("employeeId", employeeId);	
		User user = null;
		try {
			user = (User)q.getSingleResult();
		} catch (NoResultException e) {
		  // should not happen because j_security_check looks for Principle
			throw new IllegalArgumentException("User: " + employeeId + " not found.");
		}
		return user;
	}
	
	public List<User> findAllUsers() {
		TypedQuery<User> q = em.createNamedQuery("User.findAll", User.class);
		return q.getResultList();
	}

	public List<User> findActiveUsers() {
		return em.createNamedQuery("User.findActive", User.class).getResultList();
	}
	
	@Interceptors(TransactionExceptionInterceptor.class)
	@RolesAllowed({RoleName.ADMIN_STR, RoleName.IT_ADMIN_STR})
	public User add(User user, List<String> roleCodes) {
	  Tuple2<CreateAudit, UpdateAudit> createUpdateAudit = getCreateUpdateAudit();
	  // user could take this tuple2 to set both
		user.setCreateAudit(createUpdateAudit._1);
		user.setUpdateAudit(createUpdateAudit._2);
		updateRoles(user, roleCodes);
		em.persist(user);
		return user; 
	}
	
	@RolesAllowed({RoleName.ADMIN_STR, RoleName.IT_ADMIN_STR})
	public User update(User user, List<String> roleCodes) {	
	  CreateAudit createAudit = findCreateAudit(user);
	  user.setCreateAudit(createAudit);
		user.setUpdateAudit(getUpdateAudit());
		updateRoles(user, roleCodes);
		return em.merge(user);
	}
	
	private void updateRoles(User user, List<String> roleCodes) {
		if (roleCodes != null) {
			List<Role> roles = new ArrayList<Role>();
			for (String roleCode : roleCodes) {
				Role role = findRole(RoleName.findByLabel(roleCode));
				roles.add(role);
			}
			if(!roles.isEmpty()) {
				user.setRoles(roles);
			}
		}
	}
	
	private Role findRole(RoleName name) {
		Query q = em.createNamedQuery("Role.findRole");
		q.setParameter("name", name);	
		Role role = null;
		try {
			role = (Role)q.getSingleResult();
		} catch (NoResultException e) {
			//TODO: should we swallow exceptions? if so document (we always do or in this case etc)
		}
		return role;
	}
	
	/**
	 * Get CreateAudit from the DB using the user id.
	 * This may need to be public if create audits are added 
	 * are added to other entities.
	 * 
	 * @param user
	 * @return
	 */
  private CreateAudit findCreateAudit(User user) {
    User foundUser = findUser(user.getId());
    return foundUser.getCreateAudit();
  }
  
  public CreateAudit getCreateAudit() {
    return getCreateAudit(new Date());
  }
  
  private CreateAudit getCreateAudit(Date date) {
    String callerName = BeanUtil.getCallerName(ctx);
    User callerUser = findUser(callerName);
    return new CreateAudit(callerUser, date);
  }
  
  public UpdateAudit getUpdateAudit() {
    return getUpdateAudit(new Date());
  }
  
  private UpdateAudit getUpdateAudit(Date date) {
    String callerName = BeanUtil.getCallerName(ctx);
    User callerUser = findUser(callerName);
    return new UpdateAudit(callerUser, date);
  }
  
  public Tuple2<CreateAudit, UpdateAudit> getCreateUpdateAudit() {
    Date date = new Date();
    return new Tuple2<CreateAudit, UpdateAudit>(getCreateAudit(date), getUpdateAudit(date));
  }
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Role> findAllRoles() {
		Query q = em.createNamedQuery("Role.findAll");
		@SuppressWarnings("unchecked")
		List<Role> roles = q.getResultList();
		Collections.sort(roles, Role.ROLE_NAME_COMPARATOR);
		return roles;
	}
	

}
