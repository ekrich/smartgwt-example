package com.demo.model;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.demo.util.LoggerFactory;

/**
 * <p>EclipseLink does not properly wrap exceptions thrown from JPA when an exception occurs.
 * The wrapping should be something like the following:</p>
 * <pre>
 * java.sql.SQLException
 *   javax.persistent.PersistenceException
 *     Extended by: EntityExistsException, EntityNotFoundException, NonUniqueResultException, 
 *       NoResultException, OptimisticLockException, RollbackException, TransactionRequiredException
 * </pre>
 * <p>Those would be reasonable for the caller but it is more like the following in EclipseLink:</p>
 * <pre>
 * </pre>
 * 
 * @author ekr
 *
 */
public class TransactionExceptionInterceptor {
	private static Logger LOG = LoggerFactory.make();
	@AroundInvoke
	Object exception(InvocationContext ctx) throws Exception {
		Object result = null;
		try {
			result = ctx.proceed();
		} catch (Exception e) {
			// FIXME: replace with something useful
			//javax.persistence.PersistenceException
            //extended by javax.persistence.EntityExistsException
			// actual
			// javax.ejb.TransactionRolledbackLocalException 'Error committing transaction:'
			// org.eclipse.persistence.exceptions.DatabaseException ' tons of junk '
			// java.sql.SQLIntegrityConstraintViolationException 'ORA-00001: unique constraint (FACETADM.SYS_C002742861) violated'
			LOG.warning("Interceptor: " + this.getClass().getName());
			LOG.warning(e.getClass().getName() + " " + e.getMessage());
			Throwable t = e.getCause();
			while( t != null) {
				LOG.warning(t.getClass().getName() + " " + t.getMessage());
				t = e.getCause();
			}
			// don't throw for now
			//throw e;
		}
		return result;
	}
}
