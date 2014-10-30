package com.demo.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.demo.entity.SupplyItem;
import com.demo.entity.User;
import com.demo.rs.CategoryInfo;
import com.demo.rs.InfoUtils;
import com.demo.rs.UserInfo;

/**
 * Session Bean for all web service endpoints, for now anyway.
 */
@Stateless
@LocalBean
public class WebServiceBean extends BaseJpaBean {
	
	@EJB
	private UserBean userBean;
	
	@EJB
	private SupplyBean supplyBean;

	/**
	 * Default constructor.
	 */
	public WebServiceBean() {
	}
	
	public List<UserInfo> findActiveUsers() {
		List<User> users = userBean.findActiveUsers();
		return InfoUtils.convert(users);
	}
	
	public UserInfo findActiveUser(String employeeId) {
		User user = userBean.findActiveUser(employeeId);
		return InfoUtils.convert(user);
	}
	
	public List<CategoryInfo> findAllCategories() {
		return InfoUtils.convert(supplyBean.findAllCategories());
	}
	
	public List<SupplyItem> findAllItems() {
		return supplyBean.findAllItems();
	}
	
}
