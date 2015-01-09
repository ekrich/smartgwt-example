package com.demo.model;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.demo.entity.SupplyCategories;
import com.demo.entity.SupplyCategory;
import com.demo.entity.SupplyItem;
import com.demo.entity.SupplyItems;
import com.demo.servlet.DataLoader;
import com.demo.util.LoggerFactory;

/**
 * Service to deal with Supplies etc.
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
public class SupplyBean extends BaseJpaBean {
	
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.make();
	
	@Interceptors(TransactionExceptionInterceptor.class)
	public void loadSupplyCategories() {
		DataLoader dataLoader = new DataLoader(em);
		SupplyCategories supplyCategories = dataLoader.read("com/demo/data/supplyCategory.data.xml", SupplyCategories.class);
		List<SupplyCategory> supplyCategoryList = supplyCategories.getSupplyCategories();
		SupplyCategory previousCategory = null;
		for(SupplyCategory category :  supplyCategoryList) {
			String parentId = category.getParentId();
			if("root".equals(parentId)) {
				category.setParent(null);
			}
			
			if (previousCategory != null) {
				if (previousCategory.getCategoryName().equals(parentId)) {
					category.setParent(previousCategory);
				}
			}
			
			previousCategory = category;
		}
		dataLoader.persist(supplyCategoryList);
		dataLoader.print(supplyCategoryList);
		// looks like will have to find each parent via parentId and set root will be null or "root"
		SupplyItems supplyItems = dataLoader.read("com/demo/data/supplyItem.data.xml", SupplyItems.class);
		List<SupplyItem> supplyItemList = supplyItems.getSupplyItems();
		for (SupplyItem supplyItem : supplyItemList) {
			String categoryName = supplyItem.getCategory();
			supplyItem.setSupplyCategory(findSupplyCategory(categoryName));
		}
		//dataLoader.print(supplyItemList);
		dataLoader.persist(supplyItemList);
	}
	
	public List<SupplyCategory> findAllCategories() {
		return em.createNamedQuery("SupplyCategory.findAll", SupplyCategory.class).getResultList();
	}
	
	public SupplyCategory findSupplyCategory(String categoryName) {
		return em.createNamedQuery("SupplyCategory.find", SupplyCategory.class)
				.setParameter("categoryName", categoryName)
				.getSingleResult();
	}
	
	public List<SupplyItem> findAllItems() {
		return em.createNamedQuery("SupplyItem.findAll", SupplyItem.class).getResultList();
	}
	
	public List<SupplyItem> findItemsByCategory(String categoryName) {
		return em.createNamedQuery("SupplyItem.findByCategory", SupplyItem.class)
				.setParameter("categoryName", categoryName)
				.getResultList();
	}
	
}
