package be.steformations.it.javaweb.contacts.modele;

import java.util.List;

import be.steformations.java_data.contacts.interfaces.beans.Contact;
import be.steformations.java_data.contacts.interfaces.beans.Tag;

public class TagsParContact {
	Contact contact = null;
	List<Tag> tag = null;
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}
	
	
}
