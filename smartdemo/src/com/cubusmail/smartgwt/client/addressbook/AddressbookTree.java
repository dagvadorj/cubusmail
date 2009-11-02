package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.DemoData;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class AddressbookTree extends SectionStack {
	public AddressbookTree() {
		super();
		SectionStackSection section = new SectionStackSection();
		section.setCanCollapse(false);
		section.setExpanded(true);
		section.setResizeable(true);

		TreeGrid tree = new TreeGrid();
		tree.setWidth100();
		tree.setHeight100();
		tree.setAnimateFolderTime(100);
		tree.setAnimateFolders(true);
		tree.setAnimateFolderSpeed(1000);
		tree.setShowSortArrow(SortArrow.CORNER);
		tree.setShowAllRecords(true);
		tree.setLoadDataOnDemand(false);
		tree.setCanSort(false);
		tree.setCellHeight(17);
		tree.setShowHeader(false);

		TreeGridField field = new TreeGridField();
		field.setCanFilter(true);
		field.setName("name");
		field.setTitle("<b>SmartGWT Showcase</b>");
		tree.setFields(field);

		Tree treeData = new Tree();
		treeData.setModelType(TreeModelType.PARENT);
		treeData.setNameProperty("name");
		treeData.setOpenProperty("isOpen");
		treeData.setIdField("nodeID");
		treeData.setParentIdField("parentNodeID");
		treeData.setRootValue("root");
		treeData.setData(DemoData.getMailTreeData());

		tree.setData(treeData);
		section.setItems(tree);

		setSections(section);
	}
}
