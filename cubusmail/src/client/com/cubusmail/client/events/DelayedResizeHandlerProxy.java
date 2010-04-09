/* DelayedResizeEventProxy.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.client.events;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class DelayedResizeHandlerProxy implements ResizedHandler {

	private static int DELAY_MILLIS = 600;

	private ResizedHandler handler;

	private DelayTimer delayTimer;

	public static DelayedResizeHandlerProxy get( ResizedHandler handler ) {

		return new DelayedResizeHandlerProxy( handler );
	}

	private DelayedResizeHandlerProxy( ResizedHandler handler ) {

		this.handler = handler;
		this.delayTimer = new DelayTimer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.events.ResizedHandler#onResized(com.smartgwt
	 * .client.widgets.events.ResizedEvent)
	 */
	public void onResized( ResizedEvent event ) {

		this.delayTimer.cancel();
		this.delayTimer.setResizedEvent( event );
		this.delayTimer.schedule( DELAY_MILLIS );
	}

	private class DelayTimer extends Timer {

		private ResizedEvent resizedEvent;

		@Override
		public void run() {

			if ( handler != null ) {
				handler.onResized( this.resizedEvent );
			}
		}

		public void setResizedEvent( ResizedEvent resizedEvent ) {

			this.resizedEvent = resizedEvent;
		}
	}

}
