package familytree.view.commands;

import familytree.view.menu.ConsoleUI;

public class AddPerson implements Command {

    private ConsoleUI consoleUI;

    public AddPerson(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Добавить человека в дерево";
    }

    @Override
    public void execute() {
        consoleUI.addChildToPerson();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}