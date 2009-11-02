package com.cubusmail.smartgwt.client.mail;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class MessageListCanvas extends SectionStack {

	public MessageListCanvas() {
		super();

		SectionStackSection section = new SectionStackSection("Inbox");
		section.setCanCollapse(false);
		section.setExpanded(true);
		section.setResizeable(true);

		TextItem textItem = new TextItem();
		textItem.setTitle("Search");
		DynamicForm searchCanvas = new DynamicForm();
		searchCanvas.setItems(textItem);

		Button searchButton = new Button("");
		searchButton.setBorder("0px");
		searchButton.setIcon(ImageProvider.FIND);
		searchButton.setAutoFit(true);
		section.setControls(searchCanvas, searchButton);
		

		ListGrid grid = new ListGrid();
		grid.setAlternateRecordStyles(true);
		grid.setWidth100();
		grid.setCellHeight(17);
		grid.setBaseStyle("myOtherGridCell");
		
		ListGridField readField = new ListGridField("read", "");
		readField.setAlign(Alignment.CENTER);
		readField.setType(ListGridFieldType.IMAGE);
		readField.setCanSort(false);
		readField.setCanFreeze(false);
		readField.setWidth(25);
		Button headerButton = new Button();
		headerButton.setIcon(ImageProvider.MSG_STATUS_READ);
		readField.setHeaderButtonProperties(headerButton);

		ListGridField priorityField = new ListGridField("priority", "Priority");
		priorityField.setWidth(25);
		priorityField.setAlign(Alignment.CENTER);
		priorityField.setType(ListGridFieldType.IMAGE);

		ListGridField attachmentField = new ListGridField("attachment",
				"Attachment");
		attachmentField.setAlign(Alignment.CENTER);
		attachmentField.setType(ListGridFieldType.IMAGE);
		attachmentField.setWidth(25);

		ListGridField fromField = new ListGridField("from", "From");
		fromField.setWidth(200);
		ListGridField subjectField = new ListGridField("subject", "Subject");
		subjectField.setWidth(250);
		ListGridField receivedField = new ListGridField("receiveDate",
				"Receive");
		receivedField.setWidth(150);
		ListGridField sizeField = new ListGridField("size", "Size");
		sizeField.setWidth(80);

		grid.setFields(readField, priorityField, attachmentField, fromField,
				subjectField, receivedField, sizeField);
		grid.setData(MailData.getRecords());

		section.setItems(grid);
		setSections(section);
	}
}
