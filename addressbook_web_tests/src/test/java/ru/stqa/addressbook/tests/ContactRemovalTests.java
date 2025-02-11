package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData("Федя", "", ""));
        }
        app.contacts().removeContact();
    }

    @Test
    @DisplayName("Вызов и закрытие алерта при удалении контакта")
    public void isAlertCallingWithoutCheckingContactOnDelete() {
        app.contacts().pressDeleteButton();
        app.acceptAlert();
    }

    @Test
    void canRemoveAllContactsAtOnce() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData("first name", "middle name", "last name"));
        }
        app.contacts().removeAllContacts();
        Assertions.assertEquals(0, app.contacts().getCount());
    }
}
