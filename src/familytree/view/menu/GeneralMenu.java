package familytree.view.menu;

import familytree.view.commands.*;

import java.util.*;

public class GeneralMenu{
private final ConsoleUI consoleUI;
    private final List<Command> commandList;

    public GeneralMenu(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
        commandList = new ArrayList<>();
        commandList.add(new AddPerson(consoleUI));
        commandList.add(new LoadFile(consoleUI));
        commandList.add(new SaveFile(consoleUI));
        commandList.add(new GetPersonList(consoleUI));
        commandList.add(new SortMenuCommand(new SortSubMenu(consoleUI)));
        commandList.add(new Finish(consoleUI));
    }
    public String menu(){
        consoleUI.printAnswer("\nМеню:");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandList.size(); i++) {
            stringBuilder.append(i+1);
            stringBuilder.append(". ");
            stringBuilder.append(commandList.get(i).getDescription());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void execute(int choice){
        if (choice > 0 && choice <= commandList.size()) {
            Command command = commandList.get(choice-1);
            command.execute();
        } else {
            consoleUI.printAnswer("Выбрана некорректная команда.");
        }
    }

}
