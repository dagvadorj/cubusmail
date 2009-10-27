package com.cubusmail.smartgwt.client.mail;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class MessageListCanvas extends ListGrid {

	public MessageListCanvas() {
		super();
		setAlternateRecordStyles(true);
		// setShowAllRecords(true);
		// setAutoFitData(Autofit.BOTH);
		// messageGrid.setAutoWidth();
		// messageGrid.setAutoHeight();
		setHoverWidth(200);
		setHoverHeight(20);

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

		setFields(readField, priorityField, attachmentField, fromField,
				subjectField, receivedField, sizeField);
		setData(MailData.getRecords());
	}

}
