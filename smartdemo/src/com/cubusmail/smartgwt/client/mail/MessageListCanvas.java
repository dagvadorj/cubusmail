package com.cubusmail.smartgwt.client.mail;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class MessageListCanvas extends Canvas {

	public MessageListCanvas() {
		final ListGrid messageGrid = new ListGrid();
		messageGrid.setWidth(500);
		messageGrid.setHeight(224);
		messageGrid.setAlternateRecordStyles(true);
		messageGrid.setShowAllRecords(true);

		ListGridField readField = new ListGridField("read", "");
		readField.setAlign(Alignment.CENTER);
		readField.setType(ListGridFieldType.IMAGE);
		readField.setCanSort(false);
		Button headerButton = new Button();
		headerButton.setIcon(ImageProvider.MSG_STATUS_READ);
		readField.setHeaderButtonProperties(headerButton);
		
		ListGridField priorityField = new ListGridField("priority", "Priority");
		priorityField.setAlign(Alignment.CENTER);
		priorityField.setType(ListGridFieldType.IMAGE);
		
		ListGridField attachmentField = new ListGridField("attachment",
				"Attachment");
		attachmentField.setAlign(Alignment.CENTER);
		attachmentField.setType(ListGridFieldType.IMAGE);

		ListGridField fromField = new ListGridField("from", "From");
		ListGridField subjectField = new ListGridField("subject", "Subject");
		ListGridField receivedField = new ListGridField("receiveDate",
				"Receive");
		ListGridField sizeField = new ListGridField("size", "Size");

		messageGrid.setFields(readField, priorityField, attachmentField,
				fromField, subjectField, receivedField, sizeField);
		messageGrid.setCanResizeFields(true);
		messageGrid.setData(MailData.getRecords());

		addChild(messageGrid);
	}

}
