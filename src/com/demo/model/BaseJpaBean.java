package com.demo.model;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Base JPA Bean to get the EntityManager and SessionContext.
 * 
 * @author ekr
 *
 */
public class BaseJpaBean {
	
	protected static Logger LOG = com.demo.util.LoggerFactory.make();
	
	@PersistenceContext(unitName="demo")
	protected EntityManager em;
	
	@Resource 
	protected SessionContext ctx;
	

	protected String getCallerName() {
		return BeanUtil.getCallerName(ctx);
	}
			
}
