package familytree;

import familytree.view.View;
import familytree.view.menu.ConsoleUI;

public class Main {
    public static void main(String[] args) {

       View view = new ConsoleUI();

        // Начать выполнение
        view.start();
    }

}
