package com.demo.rs;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.demo.entity.SupplyItem;
import com.demo.model.LogInterceptor;
import com.demo.model.WebServiceBean;
import com.demo.util.LoggerFactory;

@Path("/v1/items")
public class ItemService {
  
  private static Logger LOG = LoggerFactory.make();
  
  private static final String VERSION = "v1";
  
  @Inject
  private WebServiceBean webServiceBean;
  
  public ItemService() {
    LOG.info(String.format("Restful %s (%s) created.", this.getClass().getSimpleName(), VERSION));
  }
  
  @GET
  @Path("/")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Interceptors({LogInterceptor.class})
  public List<SupplyItem> getAllItems() {
    return webServiceBean.findAllItems();
  }
  
}
