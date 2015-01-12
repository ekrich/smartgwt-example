package com.demo.rs;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
  public List<SupplyItemInfo> getAllItems() {
    return webServiceBean.findAllItems();
  }
  
  @GET
  @Path("/smartgwt")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Interceptors({LogInterceptor.class})
  public DsResponse<SupplyItemInfo> getResponseItemsByCategory(@QueryParam(value = "category") String categoryName) {
	  List<SupplyItemInfo> data = webServiceBean.findAllItems();
	  //List<SupplyItemInfo> data = webServiceBean.findItemsByCategory(categoryName);
	  int endAndTotalRows = data.size();
	  return new DsResponse<>(0, 0, endAndTotalRows, endAndTotalRows, data);
  }
  
}
