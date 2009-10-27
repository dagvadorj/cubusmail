package com.cubusmail.smartgwt.client.mail;

import java.util.Date;

public class MailData {
	private static MailRecord[] records;

	public static MailRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static MailRecord[] getNewRecords() {
		return new MailRecord[] {
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243),
				new MailRecord("mail_unread.png", "", "", "Oliver Test",
						"Help from Support", new Date(), 43243) };
	}

}
