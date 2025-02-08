package ru.stqa.addressbook.model;

public record ContactData(String firstName, String middleName, String lastName) {

    public ContactData() {
        this("", "", "");
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(firstName, this.middleName, this.lastName);
    }
}
