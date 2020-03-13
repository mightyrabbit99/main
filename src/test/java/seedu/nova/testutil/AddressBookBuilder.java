package seedu.nova.testutil;

import seedu.nova.model.addressbook.NovaAddressBook;
import seedu.nova.model.common.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private NovaAddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new NovaAddressBook();
    }

    public AddressBookBuilder(NovaAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public NovaAddressBook build() {
        return addressBook;
    }
}