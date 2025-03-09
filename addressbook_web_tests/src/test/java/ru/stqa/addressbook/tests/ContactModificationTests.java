package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
       if (app.hbm().getContactCount() == 0) {
           app.contacts().create(new ContactData("",
                   "First Name",
                   "Middle Name",
                   "Last Name",
                   "Address"));
       }
       var oldContacts = app.hbm().getContactList();
       var rnd = new Random();
       var index = rnd.nextInt(oldContacts.size());
       var testData = new ContactData()
               .withFirstName("modified first name")
               .withMiddleName("modified middle name")
               .withLastName("modified last name")
               .withAddress("modified address");
       app.contacts().modifyContact(oldContacts.get(index), testData);
       var newContacts = app.hbm().getContactList();
       var expectedList = new ArrayList<>(oldContacts);
       expectedList.set(index, testData
               .withId(oldContacts.get(index).id()));
       Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
       };
       newContacts.sort(compareById);
       expectedList.sort(compareById);
       Assertions.assertEquals(newContacts, expectedList);
    }
}
