package bt.tsi.daw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactBook {

	private static ArrayList<Contact> contacts = new ArrayList<Contact>();

	public static boolean addContact(Contact contact) {
		var candAdd = contacts.stream().filter(c -> c.getName().equalsIgnoreCase(contact.getName()))
				.collect(Collectors.toList()).isEmpty();

		return candAdd && contacts.add(contact);
	}

	public static Optional<Contact> find(String name) {
		Contact contact = contacts.stream().filter(c -> name.equalsIgnoreCase(c.getName())).findAny().orElse(null);
		return contact == null ? Optional.ofNullable(null) : Optional.of(contact);
	}

	public static boolean remove(String name) {
		return contacts.removeIf(c -> c.getName().equalsIgnoreCase(name));
	}

	public static List<Contact> list() {
		return Collections.unmodifiableList(contacts);
	}
}