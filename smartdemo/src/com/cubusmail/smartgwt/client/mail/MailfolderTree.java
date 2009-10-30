package com.cubusmail.smartgwt.client.mail;

import com.cubusmail.smartgwt.client.DemoData;
import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class MailfolderTree extends SectionStack {

	public MailfolderTree() {
		SectionStackSection section = new SectionStackSection();
		section.setCanCollapse(false);
		section.setExpanded(true);
		section.setResizeable(true);

		TreeGrid tree = new TreeGrid();
		tree.setWidth100();
		tree.setHeight100();
		// setCustomIconProperty("icon");
		tree.setAnimateFolderTime(100);
		tree.setAnimateFolders(true);
		tree.setAnimateFolderSpeed(1000);
		// setNodeIcon("silk/application_view_list.png");
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
		treeData.setData(DemoData.getData());

		tree.setData(treeData);
		section.setControls(createToolbar());
		section.setItems(tree);

		setSections(section);
	}

	private ToolStrip createToolbar() {
		ToolStrip strip = new ToolStrip();
		strip.setAutoWidth();
		strip.setOverflow(Overflow.VISIBLE);
		strip.setBorder("0px");
		strip.addMember(createButton(ImageProvider.MSG_REFRESH));
		strip.addMember(createButton(ImageProvider.MAIL_FOLDER_NEW));
		strip.addMember(createButton(ImageProvider.MSG_MOVE));
		strip.addMember(createButton(ImageProvider.MAIL_FOLDER_DRAFTS));
		strip.addMember(createButton(ImageProvider.MAIL_FOLDER_DELETE));
		strip.addMember(createButton(ImageProvider.MAIL_FOLDER_TRASH_EMPTY));

		return strip;
	}

	private Button createButton(String icon) {
		Button button1 = new Button("");
		button1.setIcon(icon);
		button1.setWidth(22);
		button1.setBorder("0px");
		button1.setShowDown(true);
		button1.setShowOverCanvas(true);
		button1.setPadding(0);

		return button1;
	}
}
