/* GWTMailFolder.java

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
package com.cubusmail.common.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Respresenting a mail folder for gwt frondent.
 * 
 * @author Juergen Schlierf
 */
public class GWTMailFolder implements IGWTFolder, IsSerializable {

	public final static GWTMailFolder[] EMPTY_FOLDER_ARRAY = new GWTMailFolder[0];

	/**
	 * Unique ID for this folder.
	 */
	private String id;

	private String name;

	private IGWTFolder parent;

	private GWTMailFolder[] subfolders = EMPTY_FOLDER_ARRAY;

	private int unreadMessagesCount;

	private boolean inbox;

	private boolean draft;

	private boolean sent;

	private boolean trash;

	private boolean createSubfolderSupported;

	private boolean moveSupported;

	private boolean renameSupported;

	private boolean deleteSupported;

	private boolean emptySupported;

	/**
	 * @return
	 */
	public boolean hasEntries() {

		return this.subfolders.length > 0;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {

		return this.id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId( String id ) {

		this.id = id;
	}

	/**
	 * @return Returns the parent.
	 */
	public IGWTFolder getParent() {

		return this.parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent( IGWTFolder parent ) {

		this.parent = parent;
	}

	/**
	 * @return Returns the subfolders.
	 */
	public GWTMailFolder[] getSubfolders() {

		return this.subfolders;
	}

	/**
	 * @param subfolders The subfolders to set.
	 */
	public void setSubfolders( GWTMailFolder[] subfolders ) {

		this.subfolders = subfolders;
	}

	/**
	 * @return Returns the unreadMessagesCount.
	 */
	public int getUnreadMessagesCount() {

		return this.unreadMessagesCount;
	}

	/**
	 * @param unreadMessagesCount The unreadMessagesCount to set.
	 */
	public void setUnreadMessagesCount( int unreadMessagesCount ) {

		this.unreadMessagesCount = unreadMessagesCount;
	}

	/**
	 * @return Returns the inbox.
	 */
	public boolean isInbox() {

		return this.inbox;
	}

	/**
	 * @param inbox The inbox to set.
	 */
	public void setInbox( boolean inbox ) {

		this.inbox = inbox;
	}

	/**
	 * @return Returns the draft.
	 */
	public boolean isDraft() {

		return this.draft;
	}

	/**
	 * @param draft The draft to set.
	 */
	public void setDraft( boolean draft ) {

		this.draft = draft;
	}

	/**
	 * @return Returns the sent.
	 */
	public boolean isSent() {

		return this.sent;
	}

	/**
	 * @param sent The sent to set.
	 */
	public void setSent( boolean sent ) {

		this.sent = sent;
	}

	/**
	 * @return Returns the trash.
	 */
	public boolean isTrash() {

		return this.trash;
	}

	/**
	 * @param trash The trash to set.
	 */
	public void setTrash( boolean trash ) {

		this.trash = trash;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName( String name ) {

		this.name = name;
	}

	/**
	 * @return Returns the createSubfolderSupported.
	 */
	public boolean isCreateSubfolderSupported() {

		return this.createSubfolderSupported;
	}

	/**
	 * @param createSubfolderSupported The createSubfolderSupported to set.
	 */
	public void setCreateSubfolderSupported( boolean createSubfolderSupported ) {

		this.createSubfolderSupported = createSubfolderSupported;
	}

	/**
	 * @return Returns the moveSupported.
	 */
	public boolean isMoveSupported() {

		return this.moveSupported;
	}

	/**
	 * @param moveSupported The moveSupported to set.
	 */
	public void setMoveSupported( boolean moveSupported ) {

		this.moveSupported = moveSupported;
	}

	/**
	 * @return Returns the renameSupported.
	 */
	public boolean isRenameSupported() {

		return this.renameSupported;
	}

	/**
	 * @param renameSupported The renameSupported to set.
	 */
	public void setRenameSupported( boolean renameSupported ) {

		this.renameSupported = renameSupported;
	}

	/**
	 * @return Returns the deleteSupported.
	 */
	public boolean isDeleteSupported() {

		return this.deleteSupported;
	}

	/**
	 * @param deleteSupported The deleteSupported to set.
	 */
	public void setDeleteSupported( boolean deleteSupported ) {

		this.deleteSupported = deleteSupported;
	}

	/**
	 * @return Returns the emptySupported.
	 */
	public boolean isEmptySupported() {

		return this.emptySupported;
	}

	/**
	 * @param emptySupported The emptySupported to set.
	 */
	public void setEmptySupported( boolean emptySupported ) {

		this.emptySupported = emptySupported;
	}

	@Override
	public String toString() {

		return "Folder id=" + this.id + ", name=" + this.name;
	}
}
