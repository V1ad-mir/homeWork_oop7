package familytree.model.validator;

import familytree.model.Person;
import familytree.presenter.Presenter;
import familytree.view.menu.ConsoleUI;

import java.util.Optional;

public class FamilyTreeValidator {
    private final Presenter presenter;

    public FamilyTreeValidator(Presenter presenter) {
        this.presenter = presenter;
    }

    public boolean personExistsInFamilyTree(String firstName, String lastName) {
        Optional<Person> person = searchPersonInFamilyTree(firstName, lastName);
        return person.isPresent();
    }

    private Optional<Person> searchPersonInFamilyTree(String firstName, String lastName) {
        if (presenter.getCurrentFamilyTree() == null || presenter.getCurrentFamilyTree().getRoot() == null) {
            return Optional.empty();
        }

        for (Person person : presenter.getCurrentFamilyTree()) {
            if (person.getFirstName().equalsIgnoreCase(firstName)
                    && person.getLastName().equalsIgnoreCase(lastName)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

}
