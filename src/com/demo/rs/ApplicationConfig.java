package com.demo.rs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Get a set of root resource and provider classes. The default lifecycle
 * for resource class instances is per-request. The default lifecycle for
 * providers is singleton.
 * @author ekr
 *
 */
@ApplicationPath("/rs")
public class ApplicationConfig extends Application {

}
