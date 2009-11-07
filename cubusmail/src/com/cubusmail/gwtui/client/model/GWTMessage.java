/* GWTMessage.java

   Copyright (c) 2009 Juergen Schlierf, All Rights Reserved
   
   This file is part of Cubusmail (http://code.google.com/p/cubusmail/).
	
   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.
	
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.
	
   You should have received a copy of the GNU Lesser General Public
   License along with Cubusmail. If not, see <http://www.gnu.org/licenses/>.
   
 */
package com.cubusmail.gwtui.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * client side message representation.
 * 
 * @author Juergen Schlierf
 */
public class GWTMessage implements IsSerializable {

	private long id;
	private boolean read;
	private boolean readBefore;
	private boolean draft;
	private boolean htmlMessage;
	private boolean hasImages;
	private boolean trustImages;
	private String subject;
	private String from;
	private GWTAddress[] fromArray;
	private String to;
	private GWTAddress[] toArray;
	private String cc;
	private GWTAddress[] ccArray;
	private String bcc;
	private String replyTo;
	private GWTAddress[] replyToArray;
	private String messageText;
	private int priority;
	private Date date;
	private int size;
	private boolean acknowledgement;
	private GWTAttachment[] attachments = new GWTAttachment[0];

	private Long identityId;

	public GWTMessage() {

	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {

		return this.id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId( long id ) {

		this.id = id;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {

		return this.subject;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject( String subject ) {

		this.subject = subject;
	}

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {

		return this.from;
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public void setFrom( String from ) {

		this.from = from;
	}

	/**
	 * @return Returns the date.
	 */
	public Date getDate() {

		return this.date;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate( Date date ) {

		this.date = date;
	}

	/**
	 * @return Returns the size.
	 */
	public int getSize() {

		return this.size;
	}

	/**
	 * @param size
	 *            The size to set.
	 */
	public void setSize( int size ) {

		this.size = size;
	}

	/**
	 * @return Returns the messageText.
	 */
	public String getMessageText() {

		return this.messageText;
	}

	/**
	 * @param messageText
	 *            The messageText to set.
	 */
	public void setMessageText( String htmlContent ) {

		this.messageText = htmlContent;
	}

	/**
	 * @return Returns the to.
	 */
	public String getTo() {

		return this.to;
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public void setTo( String to ) {

		this.to = to;
	}

	/**
	 * @return Returns the attachments.
	 */
	public GWTAttachment[] getAttachments() {

		return this.attachments;
	}

	/**
	 * @param attachments
	 *            The attachments to set.
	 */
	public void setAttachments( GWTAttachment[] attachments ) {

		this.attachments = attachments;
	}

	/**
	 * @return Returns the cc.
	 */
	public String getCc() {

		return this.cc;
	}

	/**
	 * @param cc
	 *            The cc to set.
	 */
	public void setCc( String cc ) {

		this.cc = cc;
	}

	// for table usage
	public Object[] getRowData() {

		Object[] result = new Object[5];
		result[0] = new Long( this.id );
		result[1] = this.subject;
		result[2] = this.from;
		result[3] = this.date;
		result[4] = this.size;

		return result;
	}

	public boolean isRead() {

		return this.read;
	}

	public void setRead( boolean read ) {

		this.read = read;
	}

	public boolean isReadBefore() {

		return this.readBefore;
	}

	public void setReadBefore( boolean readBefore ) {

		this.readBefore = readBefore;
	}

	/**
	 * @return Returns the htmlMessage.
	 */
	public boolean isHtmlMessage() {

		return this.htmlMessage;
	}

	/**
	 * @param htmlMessage
	 *            The htmlMessage to set.
	 */
	public void setHtmlMessage( boolean htmlMessage ) {

		this.htmlMessage = htmlMessage;
	}

	/**
	 * @return Returns the bcc.
	 */
	public String getBcc() {

		return this.bcc;
	}

	/**
	 * @param bcc
	 *            The bcc to set.
	 */
	public void setBcc( String bcc ) {

		this.bcc = bcc;
	}

	/**
	 * @return Returns the hasImages.
	 */
	public boolean isHasImages() {

		return this.hasImages;
	}

	/**
	 * @param hasImages
	 *            The hasImages to set.
	 */
	public void setHasImages( boolean hasImages ) {

		this.hasImages = hasImages;
	}

	/**
	 * @return Returns the identityId.
	 */
	public Long getIdentityId() {

		return this.identityId;
	}

	/**
	 * @param identityId
	 *            The identityId to set.
	 */
	public void setIdentityId( Long identityId ) {

		this.identityId = identityId;
	}

	/**
	 * @return Returns the acknowledgement.
	 */
	public boolean isAcknowledgement() {

		return this.acknowledgement;
	}

	/**
	 * @param acknowledgement
	 *            The acknowledgement to set.
	 */
	public void setAcknowledgement( boolean acknowledgement ) {

		this.acknowledgement = acknowledgement;
	}

	/**
	 * @return Returns the replyTo.
	 */
	public String getReplyTo() {

		return this.replyTo;
	}

	/**
	 * @param replyTo
	 *            The replyTo to set.
	 */
	public void setReplyTo( String replyTo ) {

		this.replyTo = replyTo;
	}

	/**
	 * @return Returns the draft.
	 */
	public boolean isDraft() {

		return this.draft;
	}

	/**
	 * @param draft
	 *            The draft to set.
	 */
	public void setDraft( boolean draft ) {

		this.draft = draft;
	}

	/**
	 * @return Returns the priority.
	 */
	public int getPriority() {

		return this.priority;
	}

	/**
	 * @param priority
	 *            The priority to set.
	 */
	public void setPriority( int priority ) {

		this.priority = priority;
	}

	public GWTAddress[] getFromArray() {

		return this.fromArray;
	}

	public void setFromArray( GWTAddress[] fromArray ) {

		this.fromArray = fromArray;
	}

	public GWTAddress[] getToArray() {

		return this.toArray;
	}

	public void setToArray( GWTAddress[] toArray ) {

		this.toArray = toArray;
	}

	public GWTAddress[] getCcArray() {

		return this.ccArray;
	}

	public void setCcArray( GWTAddress[] ccArray ) {

		this.ccArray = ccArray;
	}

	public GWTAddress[] getReplyToArray() {

		return this.replyToArray;
	}

	public void setReplyToArray( GWTAddress[] replyToArray ) {

		this.replyToArray = replyToArray;
	}

	public boolean isTrustImages() {

		return this.trustImages;
	}

	public void setTrustImages( boolean trustImages ) {

		this.trustImages = trustImages;
	}
}
