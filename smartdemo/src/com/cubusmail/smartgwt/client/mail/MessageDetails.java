package com.cubusmail.smartgwt.client.mail;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MessageDetails extends Canvas {

	public MessageDetails() {
		VLayout mainlayout = new VLayout();
		mainlayout.setHeight100();
		mainlayout.setWidth100();

		HLayout header = new HLayout();
		header.setOverflow(Overflow.HIDDEN);
		header.setHeight("70px");
		header.setBackgroundColor("lightgrey");
		header.setWidth(70);
		header.setBorder("1px");

		HTMLPane labels = new HTMLPane();
		labels.setBorder("1px");
		labels
				.setContents("<h3>A little help from my friends</h3><b>From:<br>To:<br>Date:</b>");
		labels.setAutoHeight();
		labels.setOverflow(Overflow.HIDDEN);
		labels.setBackgroundColor("lightgrey");
		header.setMembers(labels);

		HTMLFlow body = new HTMLFlow();
		body.setBorder("1px");
		body
				.setContents("<p style='font-family: monospace; font-size: 10pt;'>Hallo,<br /><br />am 29.09. (n&auml;chste Woche Freitag) ist Ed Burns in M&uuml;nchen um die Wiesn zu besuchen (siehe JUGM Meeting vom 15.11.2005: <a href='http://www.jugm.de/mitglieder.htm' target='_blank'>http://www.jugm.de/mitglieder.htm</a>)!<br /><br />Davor ist geplant gemeinsam Abend zu essen (Wirtshaus um 18:00). Dabei ist Gelegenheit mit Ed Burns und Vertretern der Apache MyFaces Implementierung &uuml;ber JSF (W&uuml;nsche f&uuml;r 2.0) zu diskutieren.<br /><br />Anschlie&szlig;end (voraussichtlich 20:00) gehts dann auf die Wiesn.<br /><br /><br />Wer Lust hat sich anzuschlie&szlig;en schickt mir bitte ein Mail an ah@jugm.de bis sp&auml;testens Montag Abend damit ich entsprechend einen Tisch reservieren kann.<br /><br /><br />Viele Gr&uuml;&szlig;e<br />Andreas<br /><br /><br /> <br />Yahoo! Groups Links<br /><br />&lt;*&gt; Besuchen Sie Ihre Group im Web unter:<br />    <a href='http://de.groups.yahoo.com/group/gi-ak-jug/' target='_blank'>http://de.groups.yahoo.com/group/gi-ak-jug/</a><br /><br />&lt;*&gt; Um sich von der Group abzumelden, senden Sie eine Mail an:<br />    gi-ak-jug-unsubscribe@yahoogroups.de<br /><br />&lt;*&gt; Mit der Nutzung von Yahoo! Groups akzeptieren Sie unsere:<br />    <a href='http://de.docs.yahoo.com/info/utos.html' target='_blank'>http://de.docs.yahoo.com/info/utos.html</a><br /> <br /><br /><br /><br /></p>");
		body.setHeight("50px");
		body.setWidth100();

		mainlayout.setMembers(header, body);

		setChildren(mainlayout);
	}
}
