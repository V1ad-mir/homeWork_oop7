package familytree.view.menu;

import familytree.model.Gender;
import familytree.model.Person;
import familytree.model.storage.FileManager;
import familytree.model.validator.DateValidator;
import familytree.model.validator.GenderValidator;
import familytree.presenter.FamilyTreePresenter;
import familytree.presenter.Presenter;
import familytree.view.View;

import java.time.LocalDate;
import java.util.*;

public class ConsoleUI implements View {

    private final Scanner scanner;
    private Presenter presenter;
    private GeneralMenu generalMenu;
    private boolean work;


    public ConsoleUI() {
        presenter = new FamilyTreePresenter();
        work = true;
        scanner = new Scanner(System.in);
        generalMenu = new GeneralMenu(this);
    }

    public void printAnswer(String text) {
        System.out.println(text);
    }

    public void start() {
        showWelcomeMessage();
        while (work) {
            System.out.println(generalMenu.menu());
            printAnswer("Выберите действие:");

            try {
                int choice = scanner.nextInt();
                generalMenu.execute(choice);
            } catch (InputMismatchException e) {
                printAnswer("Неверный выбор. Введите корректное число.");
                scanner.nextLine();
            }
        }
    }


    public void addAsChild(Person child) {
        printAnswer("Введите имя родителя:");
        String parentFirstName = scanner.nextLine();
        printAnswer("Введите фамилию родителя:");
        String parentLastName = scanner.nextLine();

        presenter.addAsChildToExistingPerson(child, parentFirstName, parentLastName);
    }

    public void addChildToPerson() {
        Person child = createPerson();

        if (!presenter.hasRootPerson()) {
            presenter.setRootPerson(child);
            printAnswer("Добавлен новый корневой элемент: " + child);
            return;
        }
        printAnswer("1. Добавить человека как ребенка к существующему человеку\n2. Добавить человека как родителя текущему корневому узлу.");
        int choice = scanner.nextInt();
        clearBuffer();
        switch (choice) {
            case 1:
                addAsChild(child);
                break;
            case 2:
                presenter.changeRootNode(child);
                break;
            default:
                printAnswer("Неверный выбор.");
                break;
        }
    }

    public void getPersonList() {
        listToString(presenter.getPersonList());
    }

    public void getChildren() {
        String firstName = getInput("Введите имя:");
        String lastName = getInput("Введите фамилию:");
        presenter.printChildren(firstName, lastName);
    }

    public void loadFile() {
        printAnswer("Предупреждение: если вы продолжите, текущее дерево будет потеряно.");
        printAnswer("Рекомендуется сохранить текущее дерево перед загрузкой нового.");
        printAnswer("1. Продолжить загрузку и заменить текущее дерево");
        printAnswer("2. Вернуться назад");
        printAnswer("3. Сохранить текущее дерево и продолжить загрузку");

        int choice;
        try {
            choice = getIntInput();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    return;
                case 3:
                    printAnswer("Введите имя загружаемого файла:");
                    String filePath = scanner.next();
                    presenter.saveFile(filePath);
                    break;
                default:
                    printAnswer("Неверный выбор.");
            }
        } catch (InputMismatchException e) {
            printAnswer("Неверный ввод. Пожалуйста, введите число.");
            clearBuffer();
        }
        printAnswer("Введите имя загружаемого файла:");
        String filePath = scanner.next();
        presenter.loadFile(filePath);
    }

    public void saveFile() {
        printAnswer("Введите имя сохраняемого файла:");
        String filePath = scanner.next();
        presenter.saveFile(filePath);
        printAnswer("Дерево сохранено в файл '" + filePath + "'.");

    }

    public void sortByFirstName() {
        listToString(presenter.sortByFirstName());
    }

    public void sortByChildrenCount() {
        listToString(presenter.sortByChildrenCount());
    }

    public void sortByBirthDate() {
        listToString(presenter.sortByBirthDate());
    }

    public void finish() {
        printAnswer("Окончание работы");
        work = false;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setGeneralMenu(GeneralMenu generalMenu) {
        this.generalMenu = generalMenu;
    }

    private Person createPerson() {
        String firstName = getInput("Введите имя:");
        String lastName = getInput("Введите фамилию:");
        printAnswer("Введите дату рождения в формате 2016-12-25:");
        LocalDate birthday = DateValidator.getValidDate();
        clearBuffer();

        printAnswer("Введите дату смерти в формате 2016-12-25 (если человек жив, нажмите 'Enter'):");
        LocalDate dayOfDeath = DateValidator.getValidDate();

        Gender gender = GenderValidator.getValidGender();
        presenter.createPerson(firstName, lastName, birthday, dayOfDeath, gender);
        return presenter.createPerson(firstName, lastName, birthday, dayOfDeath, gender);
    }

    private void printMenu() {
        printAnswer("Меню:");
        printAnswer(generalMenu.menu());
    }

    private void showWelcomeMessage() {
        printAnswer("Добро пожаловать в приложение Family Tree!");
    }

    private int getIntInput() {
        int choice;
        try {
            choice = scanner.nextInt();
            clearBuffer();
            return choice;
        } catch (InputMismatchException e) {
            printAnswer("Неверный ввод. Пожалуйста, введите число.");
            clearBuffer();
            return -1;
        }
    }

    private void clearBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.next();
    }

    private void listToString(List<Person> personList) {
        System.out.println(personList.toString());
    }

}



