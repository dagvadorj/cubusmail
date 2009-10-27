package com.cubusmail.smartgwt.client.mail;

import java.util.Date;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MailRecord extends ListGridRecord {

	public MailRecord(String read, String priority, String attachment,
			String from, String subject, Date receiveDate, int size) {
		setRead(read);
		setPriority(priority);
		setAttachment(attachment);
		setFrom(from);
		setSubject(subject);
		setReceiveDate(receiveDate);
		setSize(size);
	}

	public String getRead() {
		return getAttribute("read");
	}

	public void setRead(String read) {
		setAttribute("read", read);
	}

	public String getPriority() {
		return getAttribute("priority");
	}

	public void setPriority(String priority) {
		setAttribute("property", priority);
	}

	public String getAttachment() {
		return getAttribute("attachment");
	}

	public void setAttachment(String attachment) {
		setAttribute("attachment", attachment);
	}

	public String getFrom() {
		return getAttribute("from");
	}

	public void setFrom(String from) {
		setAttribute("from", from);
	}

	public String getSubject() {
		return getAttribute("subject");
	}

	public void setSubject(String subject) {
		setAttribute("subject", subject);
	}

	public Date getReceiveDate() {
		return getAttributeAsDate("receiveDate");
	}

	public void setReceiveDate(Date receiveDate) {
		setAttribute("receiveDate", receiveDate);
	}

	public int getSize() {
		return getAttributeAsInt("size");
	}

	public void setSize(int size) {
		setAttribute("size", size);
	}
}
