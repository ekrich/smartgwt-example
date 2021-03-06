/* 
 * SmartGWT (GWT for SmartClient) 
 * Copyright 2008 and beyond, Isomorphic Software, Inc. 
 * 
 * SmartGWT is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License version 3 
 * as published by the Free Software Foundation.  SmartGWT is also 
 * available under typical commercial license terms - see 
 * http://smartclient.com/license 
 * 
 * This software is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * Lesser General Public License for more details. 
 */  
package com.demo.gwt.app;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
  
public class App extends HLayout implements EntryPoint {  
    private SearchForm searchForm;  
    private CategoryTreeGrid categoryTree;  
    private ItemListGrid itemList;  
    private ItemDetailTabPane itemDetailTabPane;  
    private Menu itemListMenu;  
  
	@Override
	public void onModuleLoad() {
        setWidth100();  
        setHeight100();  
        setLayoutMargin(10);  
  
        final DataSource supplyCategoryDS = SupplyCategoryDs.getInstance();  
        final DataSource supplyItemDS = SupplyItemDs.getInstance();  
  
        categoryTree = new CategoryTreeGrid(supplyCategoryDS);  
        categoryTree.setAutoFetchData(true);  
        categoryTree.addNodeClickHandler(new NodeClickHandler() {  
            public void onNodeClick(NodeClickEvent event) {  
                String category = event.getNode().getAttribute("categoryName");  
                //findItems(category);
                fetchItems(category, supplyItemDS);
            }  
        });  
  
        searchForm = new SearchForm(supplyItemDS);  
  
        //when showing options in the combo-box, only show the options from the selected category if appropriate  
        final ComboBoxItem itemNameCB = searchForm.getItemNameField();  
        itemNameCB.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				ListGridRecord record = categoryTree.getSelectedRecord();  
                if ((itemNameCB.getValue() != null) && record != null) {  
                    Criteria criteria = new Criteria();  
                    criteria.addCriteria("category", record.getAttribute("categoryName"));  
                    return criteria;  
                }  
                return null;
			}  
        });  
  
        setupContextMenu();  
  
        itemList = new ItemListGrid(supplyItemDS);  
        itemList.addRecordClickHandler(new RecordClickHandler() {  
            public void onRecordClick(RecordClickEvent event) {  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        itemList.addCellSavedHandler(new CellSavedHandler() {  
            public void onCellSaved(CellSavedEvent event) {  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        itemList.addCellContextClickHandler(new CellContextClickHandler() {  
            public void onCellContextClick(CellContextClickEvent event) {  
                itemListMenu.showContextMenu();  
                event.cancel();  
            }  
        });  
  
  
        SectionStack leftSideLayout = new SectionStack();  
        leftSideLayout.setWidth(280);  
        leftSideLayout.setShowResizeBar(true);  
        leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);  
        leftSideLayout.setAnimateSections(true);  
  
        SectionStackSection suppliesCategorySection = new SectionStackSection("Office Supply Categories");  
        suppliesCategorySection.setExpanded(true);  
        suppliesCategorySection.setItems(categoryTree);  
  
        SectionStackSection instructionsSection = new SectionStackSection("Instructions");  
        instructionsSection.setItems(new HelpPane());  
        instructionsSection.setExpanded(true);  
  
        leftSideLayout.setSections(suppliesCategorySection, instructionsSection);  
  
        SectionStack rightSideLayout = new SectionStack();  
        rightSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);  
        rightSideLayout.setAnimateSections(true);  
  
  
        searchForm.setHeight(60);  
        searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {  
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
                findItems(null);  
            }  
        });  
  
        SectionStackSection findSection = new SectionStackSection("Find Items");  
        findSection.setItems(searchForm);  
        findSection.setExpanded(true);  
  
        SectionStackSection supplyItemsSection = new SectionStackSection("Office Supply Items");  
        supplyItemsSection.setItems(itemList);  
        supplyItemsSection.setExpanded(true);  
  
        itemDetailTabPane = new ItemDetailTabPane(supplyItemDS, supplyCategoryDS, itemList);  
        SectionStackSection itemDetailsSection = new SectionStackSection("Item Details");  
        itemDetailsSection.setItems(itemDetailTabPane);  
        itemDetailsSection.setExpanded(true);  
  
        rightSideLayout.setSections(findSection, supplyItemsSection, itemDetailsSection);  
  
        addMember(leftSideLayout);  
        addMember(rightSideLayout);  
        draw();  
    }  
  
    private void setupContextMenu() {  
        itemListMenu = new Menu();  
        itemListMenu.setCellHeight(22);  
  
        MenuItem detailsMenuItem = new MenuItem("Show Details", "silk/application_form.png");  
        detailsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemDetailTabPane.selectTab(0);  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        final MenuItem editMenuItem = new MenuItem("Edit Item", "silk/application_form_edit.png");  
        editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemDetailTabPane.selectTab(1);  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        final MenuItem deleteMenuItem = new MenuItem("Delete Item", "silk/delete.png");  
        deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemList.removeSelectedData();  
                itemDetailTabPane.clearDetails(null);  
            }  
        });  
  
        itemListMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);  
    }  
  
  
    public void findItems(String categoryName) {  
  
        Criteria findValues;  
  
        String formValue = searchForm.getValueAsString("findInCategory");  
        ListGridRecord selectedCategory = categoryTree.getSelectedRecord();  
        if (formValue != null && selectedCategory != null) {  
            categoryName = selectedCategory.getAttribute("categoryName");  
            findValues = searchForm.getValuesAsCriteria();  
            findValues.addCriteria("category", categoryName);  
  
        } else if (categoryName == null) {  
            findValues = searchForm.getValuesAsCriteria();  
        } else {  
            findValues = new Criteria();  
            findValues.addCriteria("category", categoryName);  
        }  
  
        itemList.filterData(findValues);  
        itemDetailTabPane.clearDetails(categoryTree.getSelectedRecord());  
    }
    
    public void fetchItems(String categoryName, DataSource supplyItemDs) {  
    	  
        Criteria findValues;  
  
        String formValue = searchForm.getValueAsString("findInCategory");  
        ListGridRecord selectedCategory = categoryTree.getSelectedRecord();  
        if (formValue != null && selectedCategory != null) {  
            categoryName = selectedCategory.getAttribute("categoryName");  
            findValues = searchForm.getValuesAsCriteria();  
            findValues.addCriteria("category", categoryName);  
        } else if (categoryName == null) {  
            findValues = searchForm.getValuesAsCriteria();  
        } else {  
            findValues = new Criteria();  
            findValues.addCriteria("category", categoryName);  
        }  
         
        supplyItemDs.filterData(findValues);  
        itemDetailTabPane.clearDetails(categoryTree.getSelectedRecord());  
    }
}  