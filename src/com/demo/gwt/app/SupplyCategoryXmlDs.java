package com.demo.gwt.app;  
  
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
  
public class SupplyCategoryXmlDs extends DataSource {  
  
    private static SupplyCategoryXmlDs instance = null;  
  
    public static SupplyCategoryXmlDs getInstance() {  
        if (instance == null) {  
            instance = new SupplyCategoryXmlDs("supplyCategoryDs");  
        }  
        return instance;  
    }  
  
    public SupplyCategoryXmlDs(String id) {  
  
        setID(id);  
        setRecordXPath("/List/supplyCategory");  
  
  
        DataSourceTextField itemNameField = new DataSourceTextField("categoryName", "Item", 128, true);  
        itemNameField.setPrimaryKey(true);  
  
        DataSourceTextField parentField = new DataSourceTextField("parentID", null);  
        parentField.setHidden(true);  
        parentField.setRequired(true);  
        parentField.setRootValue("root");  
        parentField.setForeignKey("supplyCategoryDs.categoryName");  
  
  
        setFields(itemNameField, parentField);  
  
        setDataURL("testdata/supplyCategory.data.xml");  
          
        setClientOnly(true);  
  
    }  
}  