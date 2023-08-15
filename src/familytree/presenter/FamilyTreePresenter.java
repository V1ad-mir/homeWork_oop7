package familytree.presenter;

import familytree.model.Gender;
import familytree.model.Person;
import familytree.model.exceptions.SerializationException;
import familytree.model.familyTree.FamilyTree;
import familytree.model.service.PersonManager;
import familytree.model.service.PersonService;
import familytree.model.validator.FamilyTreeValidator;
import familytree.model.storage.FileManager;
import familytree.model.storage.FileStorage;
import familytree.model.storage.SerializationStorage;
import familytree.view.View;
import familytree.view.menu.ConsoleUI;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FamilyTreePresenter implements Presenter {

    private final FileManager fileManager;
    private PersonManager personManager;
    private FamilyTree<Person> currentFamilyTree;

    private final FamilyTreeValidator familyTreeValidator;


    public FamilyTreePresenter() {
        currentFamilyTree = new FamilyTree<>();
        this.fileManager = new FileStorage(new SerializationStorage());
        this.personManager = new PersonService(currentFamilyTree);
        this.familyTreeValidator = new FamilyTreeValidator(this);
    }

    @Override
    public Person createPerson(String firstName, String lastName, LocalDate birthday, LocalDate dayOfDeath, Gender gender) {
        if (familyTreeValidator.personExistsInFamilyTree(firstName, lastName)) {
            return null;
        }

        return new Person.Builder(firstName, lastName)
                .birthday(birthday)
                .gender(gender)
                .dayOfDeath(dayOfDeath)
                .build();
    }
    @Override
    public void addAsChildToExistingPerson(Person child, String parentFirstName, String parentLastName) {
        Person parent = personManager.findPersonByNameAndSurname(parentFirstName, parentLastName);
        if (child == null) {
            return;
        }
        if (parent != null) {
            parent.addChild(child);
        } else {
            System.out.println("Родитель не найден в семейном древе.");
        }
    }

    @Override
    public List<Person> sortByFirstName() {
        return personManager.getPersonsSortedByName();
    }

    @Override
    public List<Person> sortByChildrenCount() {
        return personManager.getPersonsSortedByChildrenCount();
    }

    @Override
    public List<Person> sortByBirthDate() {
        return personManager.getPersonsSortedByAge();
    }

    @Override
    public List<Person> getPersonList() {
        return personManager.getAllPersons();
    }

    @Override
    public void loadFile(String filePath) {
        try {
            FamilyTree<Person> loadedTree = fileManager.loadFamilyTree(filePath);
            if (loadedTree != null) {
                currentFamilyTree = loadedTree;
                personManager = new PersonService(currentFamilyTree);
            } else {
                System.out.println("Проблема при загрузке файла. Дерево не было загружено.");
            }
        } catch (SerializationException e) {
            throw new SerializationException("Ошибка при загрузке файла: " + e.getMessage());
        }

        personManager = new PersonService(currentFamilyTree);
    }

    @Override
    public void saveFile(String filePath) {
        if (currentFamilyTree.getRoot() != null) {
            try {
                fileManager.saveFamilyTree(currentFamilyTree, filePath);
            } catch (SerializationException e) {
                System.out.println("Ошибка при сохранении файла: " + e.getMessage());
            }
        } else {
            System.out.println("Семейное древо еще не загружено!");
        }
        System.out.println("Дерево сохранено в файл '" + filePath + "'.");

    }

    public boolean hasRootPerson() {
        return currentFamilyTree.getRoot() != null;
    }
    public void setRootPerson(Person person){
        currentFamilyTree.setRoot(person);
    }

    @Override
    public FamilyTree<Person> getCurrentFamilyTree() {
        return currentFamilyTree;
    }

    @Override
    public void printChildren(String firstName, String lastName) {
        Optional<Person> parent = Optional.ofNullable(personManager.findPersonByNameAndSurname(firstName, lastName));
        List<Person> children = personManager.getChildrenOfPerson(parent);
        System.out.println(children.toString());
    }

    @Override
    public void changeRootNode(Person newRoot) {
        personManager.changeRootNode(newRoot);
    }
}
