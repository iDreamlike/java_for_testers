package ru.stqa.addressbook.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openContactsPage() {
        if (!manager.isElementPresent(By.name("MainForm"))) {
            click(By.linkText("home"));
        }
    }

    public boolean isContactPresent() {
        openContactsPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    public void create(ContactData contact) {
        openContactsPage();
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactsPage();
    }

    public void create(ContactData contact, GroupData group) {
        openContactsPage();
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToContactsPage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void returnToContactsPage() {
        click(By.linkText("home"));
    }

    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        removeSelectedContacts();
        returnToContactsPage();
    }

    public void pressDeleteButton() {
        openContactsPage();
        removeSelectedContacts();
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[id='%s']", contact.id())));
    }

    private void editContact(ContactData contact) {
        var rows = manager.driver.findElements(By.name("entry"));
        for (var row : rows) {
            var checkbox = row.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("id");
            if (Objects.equals(id, contact.id())) {
                row.findElement(By.cssSelector("td:nth-child(8)")).click();
                break;
            }
        }
    }

    private void removeSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeAllContacts() {
        openContactsPage();
        selectAllContacts();
        removeSelectedContacts();
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public List<ContactData> getList() {
        openContactsPage();
        var contacts = new ArrayList<ContactData>();
        var rows = manager.driver.findElements(By.name("entry"));
        for (var row : rows) {
            var firstname = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
            var lastname = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            var checkbox = row.findElement(By.name("selected[]"));
            var address = row.findElement(By.cssSelector("td:nth-child(4)")).getText();
            var id = checkbox.getAttribute("id");
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .withAddress(address));
        }
        return contacts;
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactsPage();
        editContact(contact);
        fillContactForm(modifiedContact);
        submitContactUpdate();
        openContactsPage();
    }

    private void submitContactUpdate() {
        click(By.name("update"));
    }
}
