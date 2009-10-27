package com.cubusmail.smartgwt.client.mail;

import java.util.Date;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MailRecord extends ListGridRecord {
	private String read;
	private String priority;
	private String attachment;
	private String from;
	private String subject;
	private Date receiveDate;
	private long size;

	public MailRecord(String read, String priority, String attachment,
			String from, String subject, Date receiveDate, long size) {
		this.read = read;
		this.priority = priority;
		this.attachment = attachment;
		this.from = from;
		this.subject = subject;
		this.receiveDate = receiveDate;
		this.size = size;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
