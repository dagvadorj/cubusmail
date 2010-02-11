package com.guitest.client;

import com.cubusmail.client.canvases.mail.MessageSearchForm;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Guitest implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.getBodyElement().removeChild(
				RootPanel.get("loadingWrapper").getElement());

		MessageSearchForm form = new MessageSearchForm();
		form.draw();
	}
}
