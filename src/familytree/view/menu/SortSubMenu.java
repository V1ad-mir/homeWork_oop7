package familytree.view.menu;

import familytree.presenter.Presenter;
import familytree.view.View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SortSubMenu {
    private final Scanner scanner;
private final ConsoleUI consoleUI;
    public SortSubMenu( ConsoleUI consoleUI) {
        this.scanner = new Scanner(System.in);
        this.consoleUI = consoleUI;
    }

    public void show() {
        while (true) {
            printOptions();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        consoleUI.sortByBirthDate();
                        return;
                    case 2:
                        consoleUI.sortByFirstName();
                        return;
                    case 3:
                        consoleUI.sortByChildrenCount();
                        return;
                    case 4:
                        consoleUI.start();
                        return;
                    default:
                        consoleUI.printAnswer("Неверный выбор.");
                        break;
                }
            } catch (InputMismatchException e) {
                consoleUI.printAnswer("Неверный выбор. Введите корректное число.");
                scanner.nextLine();
            }
        }
    }

    private void printOptions() {
      consoleUI.printAnswer("Получить отсортированный список людей:");
       consoleUI.printAnswer("1. Отсортировать список по возрасту");
       consoleUI.printAnswer("2. Отсортировать список по имени");
       consoleUI.printAnswer("3. Отсортировать список по количеству детей");
       consoleUI.printAnswer("4. Назад");
    }
}
