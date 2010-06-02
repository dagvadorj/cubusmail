package com.cubusmail.client.canvases.addressbook;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.GWTConstants;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class AddressEditAddressForm extends AddressEditAbstractForm {

	private TextItem streetItem;
	private TextItem zipItem;
	private TextItem cityItem;
	private TextItem stateItem;
	private TextItem countryItem;

	public AddressEditAddressForm() {

		super();
		setNumCols( 5 );
		setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, 15, 15, "*" );

		this.streetItem = new TextItem( "streetItem" );
		this.streetItem.setHint( "Street" );
		this.streetItem.setShowHintInField( true );
		this.streetItem.setShowTitle( false );
		this.streetItem.setColSpan( 2 );
		this.zipItem = new TextItem( "zipItem" );
		this.zipItem.setHint( "Zip" );
		this.zipItem.setShowHintInField( true );
		this.zipItem.setShowTitle( false );
		this.cityItem = new TextItem( "cityItem" );
		this.cityItem.setShowTitle( false );
		this.cityItem.setHint( "City" );
		this.cityItem.setShowHintInField( true );
		this.stateItem = new TextItem( "stateItem" );
		this.stateItem.setHint( "State" );
		this.stateItem.setShowHintInField( true );
		this.stateItem.setShowTitle( false );
		this.countryItem = new TextItem( "countryItem" );
		this.countryItem.setHint( "Country" );
		this.countryItem.setShowHintInField( true );
		this.countryItem.setShowTitle( false );

		SpacerItem firstSpacer = new SpacerItem();
		firstSpacer.setStartRow( true );
		SpacerItem secondSpacer = new SpacerItem();
		secondSpacer.setStartRow( true );
		SpacerItem thirdSpacer = new SpacerItem();
		thirdSpacer.setStartRow( true );

		setItems( this.typeSelectionItem, this.streetItem, this.removeItem, this.addItem, firstSpacer, this.cityItem,
				secondSpacer, this.stateItem, this.zipItem, thirdSpacer, this.countryItem );
	}

	@Override
	@SuppressWarnings("unchecked")
	public Address getValue() {

		Address result = new Address();

		if ( getType() == AddressEditFormTypeEnum.PRVATE_ADDRESS ) {
			result.setPrivateStreet( (String) this.streetItem.getValue() );
			result.setPrivateZipcode( (String) this.zipItem.getValue() );
			result.setPrivateCity( (String) this.cityItem.getValue() );
			result.setPrivateState( (String) this.stateItem.getValue() );
			result.setPrivateCountry( (String) this.countryItem.getValue() );
		}
		else {
			result.setWorkStreet( (String) this.streetItem.getValue() );
			result.setWorkZipcode( (String) this.zipItem.getValue() );
			result.setWorkCity( (String) this.cityItem.getValue() );
			result.setWorkState( (String) this.stateItem.getValue() );
			result.setWorkCountry( (String) this.countryItem.getValue() );
		}

		return result;
	}

	@Override
	public void setValue( Object value ) {

		Address address = (Address) value;
		if ( value == null ) {
			if ( this.streetItem.getValue() != null ) {
				this.streetItem.clearValue();
			}
			if ( this.zipItem.getValue() != null ) {
				this.zipItem.clearValue();
			}
			if ( this.cityItem.getValue() != null ) {
				this.cityItem.clearValue();
			}
			if ( this.stateItem.getValue() != null ) {
				this.stateItem.clearValue();
			}
			if ( this.countryItem.getValue() != null ) {
				this.countryItem.clearValue();
			}
		}
		else if ( getType() == AddressEditFormTypeEnum.PRVATE_ADDRESS ) {
			this.streetItem.setValue( address.getPrivateStreet() );
			this.zipItem.setValue( address.getPrivateZipcode() );
			this.cityItem.setValue( address.getPrivateCity() );
			this.stateItem.setValue( address.getPrivateState() );
			this.countryItem.setValue( address.getPrivateCountry() );
		}
		else {
			this.streetItem.setValue( address.getWorkStreet() );
			this.zipItem.setValue( address.getWorkZipcode() );
			this.cityItem.setValue( address.getWorkCity() );
			this.stateItem.setValue( address.getWorkState() );
			this.countryItem.setValue( address.getWorkCountry() );
		}
	}
}
