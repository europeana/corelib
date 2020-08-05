package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.Embedded;

/**
 * This class is made so that read can happen with Morphia.
 * <p>Structure of mongo for the address field should change and not be a wrapper of a class.</p>
 *
 * @author Simon Tzanakis
 * @since 2020-08-05
 */
public class Address {

  @Embedded(value = "AddressImpl")
  private AddressImpl addressImpl;

  public Address() {
  }

  public Address(AddressImpl addressImpl) {
    this.addressImpl = addressImpl;
  }

  public AddressImpl getAddressImpl() {
    return addressImpl;
  }

  public void setAddressImpl(AddressImpl addressImpl) {
    this.addressImpl = addressImpl;
  }
}
