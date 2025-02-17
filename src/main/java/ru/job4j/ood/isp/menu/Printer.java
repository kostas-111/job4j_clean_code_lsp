package ru.job4j.ood.isp.menu;

import java.util.Iterator;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        printMenu(menu.iterator(), 0);
    }

    private void printMenu(Iterator<Menu.MenuItemInfo> items, int depth) {
        while (items.hasNext()) {
            Menu.MenuItemInfo item = items.next();
            String indentation = "-".repeat(depth * 4);
            System.out.println(indentation + item.getNumber() + " " + item.getName());
            if (!item.getChildren().isEmpty()) {
                /*
                Если у элемента есть дети, вызываем рекурсию для печати детей
                 */
                printMenu(items, depth + 1);
            }
        }
    }
}
