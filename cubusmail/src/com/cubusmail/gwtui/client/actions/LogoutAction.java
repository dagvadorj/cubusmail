/* LogoutAction.java

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
package com.cubusmail.gwtui.client.actions;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.IGWTFolder;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * @author schlierf
 * 
 */
public class LogoutAction extends GWTAction implements AsyncCallback<Void> {

	/**
	 * 
	 */
	public LogoutAction() {

		super();
		setText( TextProvider.get().actions_logout_text() );
		setImageName( ImageProvider.SYSTEM_LOGOUT );
		setTooltipText( TextProvider.get().actions_logout_tooltip() );
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
		EventBroker.get().fireLogut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		EventBroker.get().fireLogut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		ServiceProvider.getUserAccountService().saveUserAccount( GWTSessionManager.get().getUserAccount(),
				new AsyncCallbackAdapter<UserAccount>() {

					public void onSuccess( UserAccount result ) {

						if ( GWTSessionManager.get().getPreferences().isEmptyTrashAfterLogout() ) {
							deleteTrash();
						}
						else {
							logout();
						}
					}
				} );
	}

	/**
	 * 
	 */
	private void logout() {

		ServiceProvider.getCubusService().logout( this );
	}

	/**
	 * 
	 */
	private void deleteTrash() {

		IGWTFolder folder = GWTSessionManager.get().getMailbox().getTrashFolder();
		if ( folder != null ) {
			ServiceProvider.getMailboxService().emptyFolder( folder.getId(), new AsyncCallbackAdapter<Void>() {

				@Override
				public void onSuccess( Void result ) {

					logout();
				}
			} );
		}
		else {
			logout();
		}
	}
}
