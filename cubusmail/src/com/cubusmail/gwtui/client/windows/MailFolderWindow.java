/* MailFolderWindow.java

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
package com.cubusmail.gwtui.client.windows;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListener;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.panels.MailFolderTreePanel;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Mail folder Dialog that shows the mail folder tree.
 * 
 * @author Jürgen Schlierf
 */
public class MailFolderWindow extends Window implements IGWTWindow {

	private MailFolderTreePanel treePanel;

	private Button okButton;
	private Button canelButton;

	private TreeNode selectedTreeNode;

	/**
	 * @param title
	 */
	MailFolderWindow() {

		super();
		setModal( true );
		setLayout( new FitLayout() );
		setCloseAction( Window.HIDE );

		setWidth( 250 );
		setHeight( 300 );
		setResizable( false );

		this.treePanel = new MailFolderTreePanel();
		this.treePanel.setEnableDD( false );
		this.treePanel.addListener( new TreePanelListenerAdapter() {

			public void onClick( TreeNode node, EventObject e ) {

				selectedTreeNode = node;
			}
		} );

		add( this.treePanel );

		this.okButton = new Button( TextProvider.get().common_button_ok() );
		addButton( this.okButton );

		this.canelButton = new Button( TextProvider.get().common_button_cancel(), new ButtonListenerAdapter() {

			public void onClick( Button button, EventObject e ) {

				hide();
			}
		} );
		addButton( this.canelButton );
	}

	public void addOKButtonListener( ButtonListener listener ) {

		this.okButton.addListener( listener );
	}

	public TreeNode getSelectedTreeNode() {

		return selectedTreeNode;
	}

	public String getSelectedFolderId() {

		if ( this.selectedTreeNode != null ) {
			return ((GWTMailFolder) this.selectedTreeNode.getUserObject()).getId();
		}
		else {
			return null;
		}
	}

	/*
	 * Because the tree creation doesn't work in init() the show method was
	 * overwritten.
	 * 
	 * @see com.gwtext.client.widgets.Window#show()
	 */
	@Override
	public void show() {

		super.show();
		this.treePanel.buildTree( GWTSessionManager.get().getMailbox() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#init()
	 */
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#validate()
	 */
	public boolean validate() {

		return true;
	}

	@Override
	public void close() {

		hide();
	}
}
