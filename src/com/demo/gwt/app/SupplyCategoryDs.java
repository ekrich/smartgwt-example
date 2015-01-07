package com.demo.gwt.app;  
  
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
  
public class SupplyCategoryDs extends RestDataSource {  
  
    private static SupplyCategoryDs instance = null;  
  
    public static SupplyCategoryDs getInstance() {  
        if (instance == null) {  
            instance = new SupplyCategoryDs("supplyCategoryDs");  
        }  
        return instance;  
    }  
  
    public SupplyCategoryDs(String id) {  
  
        setID(id);  
        //setRecordXPath("/List/supplyCategory");  
   
        DataSourceTextField itemNameField = new DataSourceTextField("categoryName", "Item", 128, true);  
        itemNameField.setPrimaryKey(true);  
  
        DataSourceTextField parentField = new DataSourceTextField("parentID", null);  
        parentField.setHidden(true);  
        parentField.setRequired(true);  
        //parentField.setRootValue("root");  // default is null, set to null in objects
        parentField.setForeignKey("supplyCategoryDs.categoryName");  
  
        setFields(itemNameField, parentField); 
        
        setDataFormat(DSDataFormat.JSON);
        setFetchDataURL("rs/v1/categories/smartgwt.json");
  
        //setDataURL("testdata/supplyCategory.data.xml");           
        //setClientOnly(true);  
  
    }  
}  