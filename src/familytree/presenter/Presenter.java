package familytree.presenter;

import familytree.model.Gender;
import familytree.model.Person;
import familytree.model.familyTree.FamilyTree;

import java.time.LocalDate;
import java.util.List;

public interface Presenter {
    void addAsChildToExistingPerson(Person child, String parentFirstName, String parentLastName);

    Person createPerson(String firstName, String lastName, LocalDate birthday, LocalDate dayOfDeath, Gender gender);

    List<Person> sortByFirstName();

    List<Person> sortByChildrenCount();

    List<Person> sortByBirthDate();

    List<Person> getPersonList();

    void loadFile(String filePath);

    void saveFile(String filePath);

    void printChildren(String firstName, String lastName);

    FamilyTree<Person> getCurrentFamilyTree();

    boolean hasRootPerson();

    void setRootPerson(Person person);

    void changeRootNode(Person newRoot);
}
