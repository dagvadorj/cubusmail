/* IdentitiesPanel.java

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
package com.cubusmail.gwtui.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.KeyboardListener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.stores.IdentityStore;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.IdentityListFields;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Panel for identities.
 * 
 * @author Juergen Schlierf
 */
public class IdentitiesPanel extends Panel {

	private GridPanel gridPanel;
	private IdentityStore identityStore;
	private ModelFormPanel<Identity> detailsFormPanel;
	private UserAccount userAccount;

	private List<Component> formFields;

	private static ColumnConfig[] COLUMN_CONFIG = new ColumnConfig[] {
			new ColumnConfig(TextProvider.get().identities_panel_table_standard(), IdentityListFields.STANDARD.name(),
					50, true),
			new ColumnConfig(TextProvider.get().identities_panel_table_name(), IdentityListFields.DISPLAYNAME.name(),
					100, true),
			new ColumnConfig(TextProvider.get().identities_panel_table_email(), IdentityListFields.EMAIL.name(), 100,
					true),
			new ColumnConfig(TextProvider.get().identities_panel_table_organisation(), IdentityListFields.ORGANISATION
					.name(), 100, true),
			new ColumnConfig(TextProvider.get().identities_panel_table_reply(), IdentityListFields.REPLY_TO.name(),
					100, true) };

	/**
	 * @param account
	 */
	public IdentitiesPanel() {

		super(TextProvider.get().identities_panel_title());
		setLayout(new BorderLayout());
		setBorder(false);

		ColumnModel columnModel = new ColumnModel(COLUMN_CONFIG);
		columnModel.setRenderer(0, new Renderer() {

			public String render( Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
					Store store ) {

				boolean checked = ((Boolean) value).booleanValue();
				return "<img class=\"checkbox\" src=\"js/ext/resources/images/default/menu/"
						+ (checked ? "checked.gif" : "unchecked.gif") + "\"/>";
			}
		});

		this.identityStore = new IdentityStore();
		this.gridPanel = new GridPanel(this.identityStore, columnModel);

		Toolbar toolbar = new Toolbar();
		toolbar.addButton(new ToolbarButton(TextProvider.get().identities_panel_new_identity(),
				new ButtonListenerAdapter() {

					@Override
					public void onClick( Button button, EventObject e ) {

						Identity newIdentity = new Identity();
						newIdentity.setEmail(GWTSessionManager.get().getMailbox().getEmailAddress());
						newIdentity.setUserAccount(GWTSessionManager.get().getUserAccount());
						Record record = identityStore.createRecord(newIdentity);

						identityStore.add(record);
						gridPanel.getSelectionModel().selectRecords(record);
						userAccount.getIdentities().add(newIdentity);
					}
				}, ImageProvider.IDENTITY_ADD));

		toolbar.addButton(new ToolbarButton(TextProvider.get().identities_panel_delete_identity(),
				new ButtonListenerAdapter() {

					@Override
					public void onClick( Button button, EventObject e ) {

						Record record = gridPanel.getSelectionModel().getSelected();
						if (record != null) {
							Identity identity = (Identity) record
									.getAsObject(IdentityListFields.IDENTITY_OBJECT.name());

							if (!identity.isStandard()) {
								userAccount.getIdentities().remove(identity);
								identityStore.remove(record);
							} else {
								MessageBox.alert(TextProvider.get().identities_panel_altert_delete());
							}
						}
					}
				}, ImageProvider.IDENTITY_DELETE));
		this.gridPanel.setTopToolbar(toolbar);

		this.gridPanel.setBorder(false);
		this.gridPanel.setFrame(false);
		this.gridPanel.setStripeRows(true);
		this.gridPanel.setEnableDragDrop(false);
		this.gridPanel.getView().setAutoFill(true);
		this.gridPanel.getView().setForceFit(true);
		this.gridPanel.setAutoHeight(false);
		this.gridPanel.setHeight(200);
		this.gridPanel.setSelectionModel(new RowSelectionModel(true)); // single
		// select
		this.gridPanel.getSelectionModel().addListener(new IdentityRowSelectionListener());
		this.gridPanel.addGridCellListener(new GridCellListenerAdapter() {

			public void onCellClick( GridPanel grid, int rowIndex, int colIndex, EventObject e ) {

				// if already selected, don't deselect
				if (grid.getColumnModel().getDataIndex(colIndex).equals(IdentityListFields.STANDARD.name())
						&& e.getTarget(".checkbox", 1) != null) {
					Record record = grid.getStore().getAt(rowIndex);
					boolean isSelected = record.getAsBoolean(IdentityListFields.STANDARD.name());
					if (!isSelected) {
						Record[] records = grid.getStore().getRecords();
						Identity identity = null;
						for (int i = 0; i < records.length; i++) {
							records[i].set(IdentityListFields.STANDARD.name(), false);
							identity = (Identity) records[i].getAsObject(IdentityListFields.IDENTITY_OBJECT.name());
							identity.setStandard(false);
						}
						record.set(IdentityListFields.STANDARD.name(), true);
						identity = (Identity) record.getAsObject(IdentityListFields.IDENTITY_OBJECT.name());
						identity.setStandard(true);
						detailsFormPanel.updateForm();
					}
				}
			}
		});

		add(this.gridPanel, new BorderLayoutData(RegionPosition.CENTER));

		// Identity details
		this.detailsFormPanel = new ModelFormPanel<Identity>();
		this.detailsFormPanel.setFrame(true);
		this.detailsFormPanel.setBorder(false);
		this.detailsFormPanel.setLabelAlign(Position.RIGHT);
		this.detailsFormPanel.setLabelWidth(110);
		this.detailsFormPanel.setButtonAlign(Position.RIGHT);
		this.detailsFormPanel.setAutoHeight(true);

		Checkbox standardCheckbox = new Checkbox(TextProvider.get().identities_panel_label_standard(), "standard");
		standardCheckbox.setDisabled(true);
		this.detailsFormPanel.add(standardCheckbox);

		TextField textfield = null;
		KeyListener keyListener = new KeyListener() {

			public void onKey( int key, EventObject e ) {

				if (key == KeyboardListener.KEY_ENTER) {
					resumeIdentities();
				}
			}
		};

		this.formFields = new ArrayList<Component>();

		this.detailsFormPanel.add(textfield = new TextField(TextProvider.get().identities_panel_label_name(),
				"displayName"), new AnchorLayoutData("80%"));
		textfield.addKeyListener(KeyboardListener.KEY_ENTER, keyListener);
		this.formFields.add(textfield);

		this.detailsFormPanel.add(
				textfield = new TextField(TextProvider.get().identities_panel_label_email(), "email"),
				new AnchorLayoutData("80%"));
		textfield.addKeyListener(KeyboardListener.KEY_ENTER, keyListener);
		this.formFields.add(textfield);

		this.detailsFormPanel.add(textfield = new TextField(TextProvider.get().identities_panel_label_organisation(),
				"organisation"), new AnchorLayoutData("80%"));
		textfield.addKeyListener(KeyboardListener.KEY_ENTER, keyListener);
		this.formFields.add(textfield);

		this.detailsFormPanel.add(textfield = new TextField(TextProvider.get().identities_panel_label_reply(),
				"replyTo"), new AnchorLayoutData("80%"));
		textfield.addKeyListener(KeyboardListener.KEY_ENTER, keyListener);
		this.formFields.add(textfield);

		this.detailsFormPanel.add(textfield = new TextField(TextProvider.get().identities_panel_label_bcc(), "bcc"),
				new AnchorLayoutData("80%"));
		textfield.addKeyListener(KeyboardListener.KEY_ENTER, keyListener);
		this.formFields.add(textfield);

		this.detailsFormPanel.add(textfield = new TextArea(TextProvider.get().identities_panel_label_signature(),
				"signature"), new AnchorLayoutData("60%"));
		this.formFields.add(textfield);

		// Checkbox checkbox = new Checkbox(
		// TextProvider.get().identities_panel_label_htmlsignature(),
		// "htmlSignature" );
		// this.detailsFormPanel.add( checkbox );
		// this.formFields.add( checkbox );

		Button button = new Button(TextProvider.get().common_button_apply(), new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				resumeIdentities();
			}
		});
		this.detailsFormPanel.addButton(button);
		this.formFields.add(button);

		add(this.detailsFormPanel, new BorderLayoutData(RegionPosition.SOUTH));
	}

	@Override
	protected void afterRender() {

		disableForm();
	}

	/**
	 * @param account
	 */
	public void setUserAccount( UserAccount account ) {

		this.userAccount = account;
		this.identityStore.setUserAccount(account);
		this.identityStore.reload();
	}

	/**
	 * 
	 */
	public void resumeIdentities() {

		this.detailsFormPanel.updateModel();
		this.identityStore.reload();
	}

	/**
	 * 
	 */
	private void enableForm() {

		for (Component field : this.formFields) {
			field.enable();
		}
	}

	/**
	 * 
	 */
	private void disableForm() {

		for (Component field : this.formFields) {
			field.disable();
		}
	}

	/**
	 * Selection listener.
	 * 
	 * @author Juergen Schlierf
	 */
	private class IdentityRowSelectionListener extends RowSelectionListenerAdapter {

		@Override
		public void onSelectionChange( RowSelectionModel sm ) {

			if (sm.getCount() == 1) {
				enableForm();
				Identity identity = (Identity) sm.getSelected().getAsObject(IdentityListFields.IDENTITY_OBJECT.name());
				detailsFormPanel.setModel(identity);
				detailsFormPanel.updateForm();
			} else {
				disableForm();
			}
		}

	}
}
