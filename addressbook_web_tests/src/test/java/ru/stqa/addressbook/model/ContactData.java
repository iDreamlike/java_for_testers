package ru.stqa.addressbook.model;

public record ContactData(String id, String firstName, String middleName, String lastName, String address) {

    public ContactData() {
        this("", "", "", "", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.middleName, this.lastName, this.address);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, firstName, this.middleName, this.lastName, this.address);
    }

    public ContactData withMiddleName(String middleName) {
        return new ContactData(this.id, this.firstName, middleName, this.lastName, this.address);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.firstName, this.middleName, lastName, this.address);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.firstName, this.middleName, this.lastName, address);
    }
}
