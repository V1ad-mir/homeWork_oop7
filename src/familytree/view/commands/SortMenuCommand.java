package familytree.view.commands;

import familytree.view.menu.SortSubMenu;

public class SortMenuCommand implements Command {
    private final SortSubMenu subMenu;

    public SortMenuCommand(SortSubMenu subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public String getDescription() {
        return "Получить отсортированный список людей";
    }

    @Override
    public void execute() {
        subMenu.show();
    }
}
