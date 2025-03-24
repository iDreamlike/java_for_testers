package ru.stqa.addressbook.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
/*        for (var firstName : List.of("", "first name")) {
            for (var middleName : List.of("", "middle name")) {
                for (var lastName : List.of("", "last name")) {
                    result.add(new ContactData("", firstName, middleName, lastName, randomFile("src/test/resources/images")));
                }
            }
        }
        for (int i = 0; i<5; i++) {
            result.add(new ContactData("", CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), randomFile("src/test/resources/images")));
        }*/
        var mapper = new YAMLMapper();
        var value = mapper.readValue(new File("contacts.yaml"), new TypeReference<List<ContactData>>() {});
        result.addAll(value);
        return result;
    }

    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().create(contact);
        var newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<>(List.of(new ContactData("", "name'", "", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().create(contact);
        var newContacts = app.hbm().getContactList();
        Assertions.assertEquals(newContacts, oldContacts);
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10));
        if (app.hbm().getGroupCount() == 0) {
            app.groups().create(new GroupData("",
                    "group name",
                    "group header",
                    "group footer"));
        }
        if (app.hbm().getContactCount() == 0) {
            app.contacts().create(new ContactData("",
                    "firstname",
                    "middlename",
                    "lastname",
                    "address"));
        }
        var group = app.hbm().getGroupList().get(0);
        var oldRelated = app.hbm().getContactsInGroup(group);
        contact = app.hbm().getContactList().get(0);
        app.contacts().addContactToGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newRelated.sort(compareById);
        var expectedList = new ArrayList<>(oldRelated);
        expectedList.add(contact.withId(newRelated.get(newRelated.size() - 1).id()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newRelated, expectedList);
    }

    @Test
    void canDeleteContactFromGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.groups().create(new GroupData("",
                    "group name",
                    "group header",
                    "group footer"));
        }
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10));
        var group = app.hbm().getGroupList().get(0);
        app.contacts().create(contact, group);

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().deleteFromGroup(group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size());
    }
}