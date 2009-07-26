/* IdentityStore.java

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
package com.cubusmail.gwtui.client.stores;

import java.util.List;

import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.ObjectFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;

import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.IdentityListFields;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Store for identities.
 * 
 * @author Jürgen Schlierf
 */
public class IdentityStore extends Store {

	private UserAccount userAccount;

	public static RecordDef RECORD_DEF = new RecordDef( new FieldDef[] { new IntegerFieldDef(
			IdentityListFields.ID.name() ),
			new BooleanFieldDef( IdentityListFields.STANDARD.name() ),
			new StringFieldDef( IdentityListFields.DISPLAYNAME.name() ),
			new StringFieldDef( IdentityListFields.EMAIL.name() ),
			new StringFieldDef( IdentityListFields.ORGANISATION.name() ),
			new StringFieldDef( IdentityListFields.REPLY_TO.name() ),
			new StringFieldDef( IdentityListFields.INTERNET_ADDRESS.name() ),
			new StringFieldDef( IdentityListFields.ESCAPED_INTERNET_ADDRESS.name() ),
			new ObjectFieldDef( IdentityListFields.IDENTITY_OBJECT.name() ) } );

	/**
	 * 
	 */
	public IdentityStore() {

		super( new ArrayReader( 0, RECORD_DEF ) );
		setAutoLoad( true );
	}

	/**
	 * @return
	 */
	private Object[][] getIdentityArray() {

		List<Identity> identities = this.userAccount.getIdentities();
		if ( identities != null && identities.size() > 0 ) {
			Object[][] result = new Object[identities.size()][IdentityListFields.values().length];
			for ( int i = 0; i < identities.size(); i++ ) {
				Identity identity = identities.get( i );
				identity2Array( identity, result[i] );
			}
			return result;
		} else {
			return new Object[0][0];
		}
	}

	/**
	 * @param identity
	 * @param identityArray
	 */
	private void identity2Array( Identity identity, Object[] identityArray ) {

		identityArray[IdentityListFields.ID.ordinal()] = identity.getId();
		identityArray[IdentityListFields.STANDARD.ordinal()] = identity.isStandard();
		identityArray[IdentityListFields.DISPLAYNAME.ordinal()] = identity.getDisplayName();
		identityArray[IdentityListFields.EMAIL.ordinal()] = identity.getEmail();
		identityArray[IdentityListFields.ORGANISATION.ordinal()] = identity.getOrganisation();
		identityArray[IdentityListFields.REPLY_TO.ordinal()] = identity.getReplyTo();
		identityArray[IdentityListFields.INTERNET_ADDRESS.ordinal()] = identity.getInternetAddress();
		identityArray[IdentityListFields.ESCAPED_INTERNET_ADDRESS.ordinal()] = identity.getEscapedInternetAddress();
		identityArray[IdentityListFields.IDENTITY_OBJECT.ordinal()] = identity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.data.Store#reload()
	 */
	@Override
	public void reload() {

		setDataProxy( new MemoryProxy( getIdentityArray() ) );
		super.reload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.data.Store#load()
	 */
	@Override
	public void load() {

		setDataProxy( new MemoryProxy( getIdentityArray() ) );
		super.load();
	}

	/**
	 * @param identity
	 * @return
	 */
	public Record createRecord( Identity identity ) {

		Object[] array = new Object[IdentityListFields.values().length];
		identity2Array( identity, array );
		return RECORD_DEF.createRecord( array );
	}

	/**
	 * @param userAccount The userAccount to set.
	 */
	public void setUserAccount( UserAccount userAccount ) {

		this.userAccount = userAccount;
	}
}
