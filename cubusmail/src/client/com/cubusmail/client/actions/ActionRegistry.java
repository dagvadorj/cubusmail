/* ActionRegistry.java

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
package com.cubusmail.client.actions;

import java.util.HashMap;
import java.util.Map;

import com.cubusmail.client.actions.folder.DeleteFolderAction;
import com.cubusmail.client.actions.folder.EmptyFolderAction;
import com.cubusmail.client.actions.folder.NewFolderAction;
import com.cubusmail.client.actions.folder.RefreshFolderAction;
import com.cubusmail.client.actions.folder.RenameFolderAction;
import com.cubusmail.client.actions.message.LoadMessageAction;

/**
 * Registry for all actions used by Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum ActionRegistry {
	// comman actions
	LOGIN,

	// message actions
	LOAD_MESSAGE,
	
	// mail folder actions
	REFRESH_FOLDER, NEW_FOLDER, MOVE_FOLDER, RENAME_FOLDER, DELETE_FOLDER, EMPTY_FOLDER;

	private static Map<ActionRegistry, IGWTAction> ACTION_MAP = new HashMap<ActionRegistry, IGWTAction>();

	/**
	 * @return
	 */
	public IGWTAction get() {

		IGWTAction result = ACTION_MAP.get( this );
		if ( result == null ) {
			result = create();
			ACTION_MAP.put( this, result );
		}

		return result;
	}

	/**
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IGWTAction> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}

	/**
	 * create actions
	 */
	private IGWTAction create() {

		switch (this) {
		case LOGIN:
			return new LoginAction();

			// messages
		case LOAD_MESSAGE:
			return new LoadMessageAction();
			
		case REFRESH_FOLDER:
			return new RefreshFolderAction();
		case NEW_FOLDER:
			return new NewFolderAction();
		case RENAME_FOLDER:
			return new RenameFolderAction();
		case DELETE_FOLDER:
			return new DeleteFolderAction();
		case EMPTY_FOLDER:
			return new EmptyFolderAction();
		}

		throw new IllegalArgumentException( "Action missing: " + name() );
	}

	/**
	 * 
	 */
	public void execute() {

		get().execute();
	}
}
