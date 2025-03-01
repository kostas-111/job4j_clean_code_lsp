package ru.job4j.ood.isp.menu;

import java.util.List;
import java.util.Scanner;

/**
 * 6. Создайте простенький класс TodoApp. Этот класс будет представлять собой консольное приложение, которое позволяет:
 * Добавить элемент в корень меню;
 * Добавить элемент к родительскому элементу;
 * Вызвать действие, привязанное к пункту меню (действие можно сделать константой,
 * например, ActionDelete DEFAULT_ACTION = () -> System.out.println("Some action") и указывать при добавлении элемента в меню);
 * Вывести меню в консоль.
 */
public class TodoApp {
    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");
    private static final String DIVIDER = "--------------------------------------";
    private static final String ASK_INT = "Введите цифру для выбора пункта меню: ";
    private static final String ASK_PARENT_NAME = "Введите имя родителя: ";
    private static final String ASK_ELEMENT_NAME = "Введите имя элемента: ";

    private final Scanner scanner;
    private final Printer printer;
    private final Menu menu;

    public TodoApp(Scanner scanner,
                   Printer printer,
                   Menu menu) {
        this.scanner = scanner;
        this.printer = printer;
        this.menu = menu;
    }

    private void init() {
        List<String> appMenu = List.of(
                "1. Добавить элемент в корень меню",
                "2. Добавить элемент к родительскому элементу",
                "3. Вызвать действие, привязанное к пункту меню",
                "4. Вывести меню в консоль"
        );
        while (true) {
            showMenu(appMenu);
            int numInput = Integer.parseInt(askStr(ASK_INT));
            if (numInput < 1 || numInput > appMenu.size()) {
                System.out.printf("Неверный ввод! Вы можете выбрать число от 1 до %d\n", appMenu.size());
                continue;
            }
            switch (numInput) {
                case 1 -> addElement(menu.ROOT, askStr(ASK_ELEMENT_NAME));
                case 2 -> {
                    String strInput = askStr(ASK_PARENT_NAME);
                    addElement(strInput, askStr(ASK_ELEMENT_NAME));
                }
                case 3 -> selectElement(askStr(ASK_ELEMENT_NAME));
                case 4 -> {
                    System.out.println(DIVIDER);
                    System.out.println("Список:");
                    printer.print(menu);
                }
                default -> {
                }
            }
        }
    }

    private void showMenu(List<String> actions) {
        System.out.println(DIVIDER);
        System.out.println("Меню:");
        for (String action : actions) {
            System.out.println(action);
        }
    }

    private void addElement(String parentName, String childName) {
        if (menu.add(parentName, childName, DEFAULT_ACTION)) {
            System.out.println("Элемент успешно добавлен");
        } else {
            System.out.println("Не удалось добавить элемент");
        }
    }

    private void selectElement(String elementName) {
        System.out.println(DIVIDER);
        var element = menu.select(elementName);
        if (element.isPresent()) {
            System.out.printf("Выбранное действие для %s:\n", elementName);
            element.get().getActionDelegate().delegate();
        } else {
            System.out.println("Элемент с таким именем не найден!");
        }
    }

    private String askStr(String question) {
        System.out.println(DIVIDER);
        System.out.print(question);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();
        Menu menu = new SimpleMenu();
        TodoApp todoApp = new TodoApp(scanner, printer, menu);
        todoApp.init();
    }
}
