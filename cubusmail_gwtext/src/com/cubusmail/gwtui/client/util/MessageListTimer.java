/* MessageListTimer.java

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
package com.cubusmail.gwtui.client.util;

import com.google.gwt.user.client.Timer;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.domain.Preferences;

/**
 * Timer for releading the message list.
 * 
 * @author Juergen Schlierf
 */
public class MessageListTimer {

	private static MessageListTimer instance;

	private Timer timer;

	public static MessageListTimer get() {

		if ( instance == null ) {
			instance = new MessageListTimer();
		}

		return instance;
	}

	private MessageListTimer() {

		this.timer = new Timer() {

			public void run() {

				EventBroker.get().fireMessagesReload();
			}
		};

	}

	public void start() {

		Preferences preferences = GWTSessionManager.get().getPreferences();
		if ( preferences.getMessagesReloadPeriod() > 0 ) {
			this.timer.scheduleRepeating( preferences.getMessagesReloadPeriod() );
		}
		else {
			this.timer.cancel();
		}
	}

	public void stop() {

		this.timer.cancel();
	}
}
