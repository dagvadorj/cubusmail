/* MoveFolderAction.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.actions.folder;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;

import com.cubusmail.gwtui.client.actions.GWTFolderAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.exceptions.folder.GWTMailFolderException;
import com.cubusmail.gwtui.client.exceptions.folder.GWTMailFolderExistException;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.MailFolderWindow;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Action for moving mail folders
 * 
 * @author Jürgen Schlierf
 */
public class MoveFolderAction extends GWTFolderAction implements AsyncCallback<Object> {

	private ButtonListenerAdapter okButtonListener;

	/**
	 * 
	 */
	public MoveFolderAction() {

		super();
		setText( TextProvider.get().actions_movefolder_text() );
		setImageName( ImageProvider.MAIL_FOLDER_MOVE );
		setTooltipText( TextProvider.get().actions_movefolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.actions.GWTAction#onClick(com.gwtext.client
	 * .widgets.menu.BaseItem, com.gwtext.client.core.EventObject)
	 */
	public void onClick( BaseItem item, EventObject e ) {

		WindowRegistry.MOVE_FOLDER_WINDOW.open( getSelectedTreeNode().getText() );
		addButtonListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.actions.GWTAction#onClick(com.gwtext.client
	 * .widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onClick( Button button, EventObject e ) {

		WindowRegistry.MOVE_FOLDER_WINDOW.open( getSelectedTreeNode().getText() );
		addButtonListener();
	}

	/**
	 * 
	 */
	private void addButtonListener() {

		if ( this.okButtonListener == null ) {
			this.okButtonListener = new OKButtonListener();
		}
		WindowRegistry.MOVE_FOLDER_WINDOW.get( MailFolderWindow.class ).addOKButtonListener( this.okButtonListener );
	}

	/**
	 * For external use too.
	 * 
	 * @param sourceFolderId
	 * @param targetFolderId
	 */
	public void moveFolder( String sourceFolderId, String targetFolderId ) {

		PanelRegistry.LEFT_PANEL.mask();
		ServiceProvider.getMailboxService().moveFolder( sourceFolderId, targetFolderId, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
		GWTMailFolderException e = (GWTMailFolderException) caught;
		if ( caught instanceof GWTMailFolderExistException ) {
			MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().exception_folder_already_exist(
					e.getFolderName() ) );
		} else {
			MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().exception_folder_move(
					e.getFolderName() ) );
		}
		EventBroker.get().fireFoldersReload();
		PanelRegistry.LEFT_PANEL.unmask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Object result ) {

		PanelRegistry.LEFT_PANEL.unmask();
		EventBroker.get().fireFoldersReload();
	}

	private class OKButtonListener extends ButtonListenerAdapter {

		public void onClick( Button button, EventObject e ) {

			MailFolderWindow window = WindowRegistry.MOVE_FOLDER_WINDOW.get( MailFolderWindow.class );
			if ( window.getSelectedTreeNode() != null ) {
				WindowRegistry.MOVE_FOLDER_WINDOW.close();
				String sourceFolderId = ((GWTMailFolder) getSelectedTreeNode().getUserObject()).getId();
				String targetFolderId = window.getSelectedFolderId();
				moveFolder( sourceFolderId, targetFolderId );
			} else {
				MessageBox.alert( "Bitte wählen einen Ordner aus" );
			}
		}
	}
}
