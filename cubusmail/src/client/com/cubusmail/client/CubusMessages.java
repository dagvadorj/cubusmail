/* CubusMessages.java

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

package com.cubusmail.client;

import com.google.gwt.i18n.client.Messages;

/**
 * Messages constants.
 * 
 * @author schlierf
 *
 */
public interface CubusMessages extends Messages {
	// Login
	public String logindialog_title();
	public String logindialog_message();
	public String logindialog_progress_msg();
	public String logindialog_progress_text();
	public String logindialog_label_username();
	public String logindialog_label_password();
	public String logindialog_button_login();
	public String logindialog_empty_username();
	public String logindialog_empty_password();
	
	public String dialog_preferences_header();
	public String dialog_preferences_alert();
	public String dialog_contactlist_title();
	public String dialog_contactlist_label_to();
	public String dialog_contactlist_label_cc();
	public String dialog_contactlist_label_bcc();

	public String window_compose_message_title();
	public String window_compose_message_label_from();
	public String window_compose_message_label_priority();
	public String window_compose_message_label_acknowledgement();
	public String window_compose_message_label_to();
	public String window_compose_message_label_cc();
	public String window_compose_message_label_bcc();
	public String window_compose_message_label_subject();
	public String window_compose_message_priority_high();
	public String window_compose_message_priority_normal();
	public String window_compose_message_priority_low();
	public String window_compose_message_validate_input();
	
	public String contact_reading_pane_portlet_contact(); 
	public String contact_reading_pane_portlet_internet(); 
	public String contact_reading_pane_portlet_telecom(); 
	public String contact_reading_pane_portlet_private(); 
	public String contact_reading_pane_portlet_business(); 
	public String contact_reading_pane_portlet_notice(); 
	
	public String contact_reading_pane_label_firstname();
	public String contact_reading_pane_label_secondname();
	public String contact_reading_pane_label_lastname();
	public String contact_reading_pane_label_nickname();
	public String contact_reading_pane_label_company();
	public String contact_reading_pane_label_position();

	public String contact_reading_pane_label_email();
	public String contact_reading_pane_label_email2();
	public String contact_reading_pane_label_im();
	public String contact_reading_pane_label_website();

	public String contact_reading_pane_label_privatephone();
	public String contact_reading_pane_label_businessphone();
	public String contact_reading_pane_label_mobilephone();
	public String contact_reading_pane_label_pager();
	public String contact_reading_pane_label_privatefax();
	public String contact_reading_pane_label_businessfax();
	
	public String contact_reading_pane_label_street();
	public String contact_reading_pane_label_street2();
	public String contact_reading_pane_label_zipcode();
	public String contact_reading_pane_label_city();
	public String contact_reading_pane_label_state();
	
	public String contact_reading_pane_label_country();
	public String contact_reading_pane_label_custom1();
	public String contact_reading_pane_label_custom2();
	public String contact_reading_pane_label_custom3();
	public String contact_reading_pane_label_custom4();
	public String contact_reading_pane_label_notice();
	
	public String extended_search_panel_from();
	public String extended_search_panel_to();
	public String extended_search_panel_cc();
	public String extended_search_panel_subject();
	public String extended_search_panel_body();
	public String extended_search_panel_date_from();
	public String extended_search_panel_date_to();
	public String extended_search_panel_date_format();
	public String extended_search_panel_fieldset();
	public String extended_search_panel_search();
	
	public String identities_panel_table_standard();
	public String identities_panel_table_name();
	public String identities_panel_table_email();
	public String identities_panel_table_organisation();
	public String identities_panel_table_reply();
	public String identities_panel_title();
	public String identities_panel_new_identity();
	public String identities_panel_delete_identity(); 
	public String identities_panel_altert_delete();
	public String identities_panel_label_standard();
	public String identities_panel_label_name();
	public String identities_panel_label_email();
	public String identities_panel_label_organisation();
	public String identities_panel_label_reply();
	public String identities_panel_label_bcc();
	public String identities_panel_label_signature();
	public String identities_panel_label_htmlsignature();
	
	public String preferences_panel_title();
	public String preferences_panel_label_general();
	public String preferences_panel_label_language();
	public String preferences_panel_label_timezone();
	public String preferences_panel_label_items();
	public String preferences_panel_label_short_tieme_format();
	public String preferences_panel_label_show_html();
	public String preferences_panel_label_compose_html();
	public String preferences_panel_label_reading_pane();
	public String preferences_panel_label_display();
	public String preferences_panel_label_reload_period();
	public String preferences_panel_label_theme();
	public String preferences_panel_label_mark_as_deleted();
	public String preferences_panel_label_empty_trash();
	public String preferences_panel_label_mail_server();
	
	public String message_reading_pane_panel_from();
	public String message_reading_pane_panel_to();
	public String message_reading_pane_panel_cc();
	public String message_reading_pane_panel_replyto();
	public String message_reading_pane_panel_date();
	public String message_reading_pane_panel_attachment();
	public String message_reading_pane_panel_acknolegement_title();
	public String message_reading_pane_panel_acknolegement_text();
	public String message_reading_pane_panel_warning();
	
	
	// Messages View
	public String views_messages_banner_from();
	public String views_messages_banner_to();
	public String views_messages_banner_cc();
	public String views_messages_banner_subject();
	public String views_messages_banner_attachments();
	public String views_messages_banner_priority();
	public String views_messages_columnheader_subject();
	public String views_messages_columnheader_from();
	public String views_messages_columnheader_recieved();
	public String views_messages_columnheader_size();

	// message grid
	public String grid_messages_subject();
	public String grid_messages_from();
	public String grid_messages_to();
	public String grid_messages_date();
	public String grid_messages_size();
	public String grid_messages_count();
	public String grid_messages_no_messages();
	public String grid_messages_page();
	public String grid_messages_after_page();
	public String grid_messages_search();
	public String grid_messages_search_tooltip(); 
	public String grid_messages_extended_search();
	
	// Actions
	public String actions_copymessages_text();
	public String actions_copymessage_tooltip();
	public String actions_copymessage_title();
	public String actions_movemessage_text();
	public String actions_movemessage_tooltip();
	public String actions_movemessage_title();
	public String actions_copymovemessage_alert();
	public String actions_deletemessage_text();
	public String actions_deletemessage_tooltip();
	public String actions_refresh_text();
	public String actions_refresh_tooltip();
	public String actions_newmessage_text();
	public String actions_newmessage_tooltip();
	public String actions_reply_text();
	public String actions_reply_tooltip();
	public String actions_replyall_text();
	public String actions_replyall_tooltip();
	public String actions_forward_text();
	public String actions_forward_tooltip();
	public String actions_markread_text();
	public String actions_markread_tooltip();
	public String actions_markunread_text();
	public String actions_markunread_tooltip();
	public String actions_markdeleted_text();
	public String actions_markdeleted_tooltip();
	public String actions_markundeleted_text();
	public String actions_markundeleted_tooltip();
	public String actions_download_attachment_text();
	public String actions_download_attachment_tooltip();
	public String actions_view_attachment_text();
	public String actions_print_message_text();
	public String actions_print_message_tooltip();
	public String actions_remove_attachment_text();
	public String actions_remove_attachment_tooltip();
	public String actions_showmessagesource_text();
	public String actions_showmessagesource_tooltip();

	public String actions_deletefolder_text();
	public String actions_deletefolder_tooltip();
	public String actions_deletefolder_warning();
	public String actions_emptyfolder_text();
	public String actions_emptyfolder_tooltip();
	public String actions_emptyfolder_warning();
	public String actions_movefolder_text();
	public String actions_movefolder_tooltip();
	public String actions_movefolder_title(String folderName);
	public String actions_newfolder_text();
	public String actions_newfolder_tooltip();
	public String actions_newfolder_question();
	public String actions_renamefolder_text();
	public String actions_renamefolder_tooltip();
	public String actions_renamefolder_question();
	public String actions_refreshfolder_text();
	public String actions_refreshfolder_tooltip();
	
	public String actions_compose_send_text();
	public String actions_compose_send_tooltip();
	public String actions_compose_send_hint_header();
	public String actions_compose_send_hint_text();
	public String actions_compose_cancel_tooltip();
	public String actions_compose_savedraft_text();
	public String actions_compose_savedraft_tooltip();
	public String actions_compose_addattachment_text();
	public String actions_compose_addattachment_tooltip();	

	public String actions_newmessage_tocontact_text();
	public String actions_newmessage_tocontact_tooltip();
	public String actions_add_contact_text();
	public String actions_add_contact_tooltip();
	public String actions_add_to_contactlist();
	public String actions_edit_contact_text();
	public String actions_edit_contact_tooltip();
	public String actions_delete_contact_text();
	public String actions_delete_contact_tooltip();
	public String actions_new_contactfolder_text();
	public String actions_new_contactfolder_tooltip();
	public String actions_new_contactfolder_question();
	public String actions_delete_contactfolder_text();
	public String actions_delete_contactfolder_tooltip();
	public String actions_rename_contactfolder_text();
	public String actions_rename_contactfolder_tooltip();
	public String actions_rename_contactfolder_question();

	public String actions_open_preferences_text();
	public String actions_open_preferences_tooltip();

	public String actions_reading_pane_text();
	public String actions_reading_pane_tooltip();
	public String actions_reading_pane_hide();
	public String actions_reading_pane_right();
	public String actions_reading_pane_bottom();
	
	public String actions_logout_text();
	public String actions_logout_tooltip();

	public String toolbar_manager_to_originator();
	public String toolbar_manager_to_all();
	public String toolbar_manager_mark();
	
	public String contact_folder_panel_title();
	public String contact_folder_panel_recipients();
	
	public String contact_list_panel_title();
	public String contact_list_panel_col_name();
	public String contact_list_panel_col_email();
	public String contact_list_panel_col_company();
	public String contact_list_panel_no_contacts();
	
	public String contact_window_address_panel();
	public String contact_window_label_contact_data();
	public String contact_window_label_person();
	public String contact_window_label_firstname();
	public String contact_window_label_secondname();
	public String contact_window_label_lastname();
	public String contact_window_label_nickname();
	public String contact_window_label_company();
	public String contact_window_label_position();
	public String contact_window_label_internet();
	public String contact_window_label_email();
	public String contact_window_label_secondemail();
	public String contact_window_label_im();
	public String contact_window_label_website();
	public String contact_window_label_telecom();
	public String contact_window_label_privatephone();
	public String contact_window_label_businessphone();
	public String contact_window_label_mobilephone();
	public String contact_window_label_pager();
	public String contact_window_label_privatefax();
	public String contact_window_label_businessfax();
	public String contact_window_label_private();
	public String contact_window_label_business();
	public String contact_window_label_street();
	public String contact_window_label_street2();
	public String contact_window_label_zipcode();
	public String contact_window_label_city();
	public String contact_window_label_state();
	public String contact_window_label_country();
	public String contact_window_label_custom();
	public String contact_window_label_custom1();
	public String contact_window_label_custom2();
	public String contact_window_label_custom3();
	public String contact_window_label_custom4();
	public String contact_window_label_notice();
	public String contact_window_alert_title();
	public String contact_window_alert_text();
	
	// Common
	public String common_application_title();
	public String common_button_cancel();
	public String common_button_ok();
	public String common_button_apply();
	public String common_button_save();
	public String common_button_close();
	public String common_mailbox();
	public String common_view_text();
	public String common_remove_text();
	public String common_mask_text();
	public String common_error();
	public String common_seconds();
	public String common_mintues();
	public String common_half_an_hour();
	public String common_never();
	public String common_message_date_format();

	// Excpetions
	public String exception_login_connection();
	public String exception_login_authentication();
	public String exception_logout();
	public String exception_folder_rename(String folderName);
	public String exception_folder_move(String folderName);
	public String exception_folder_empty(String folderName);
	public String exception_folder_delete(String folderName);
	public String exception_folder_create(String folderName);
	public String exception_folder_close();
	public String exception_folder_already_exist(String folderName);
	public String exception_folder_could_not_deleted();
	public String exception_compose_invalid_address(String address);
	public String exception_compose_send();
	public String exception_compose_save_draft();
	public String exception_invalid_session();
	
	// demo mode
	public String demo_username();
	public String demo_password();
}
