package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.assertj.core.api.Assertions.*;

@Disabled
class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    void whenPrintMenu() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        //menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        //menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        //menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
       // menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);
        System.setOut(printStream);
        printer.print(menu);
        String expectedOutput =
                "1. Сходить в магазин\n" + "  ";
                //+"  ----1.1. Купить продукты\n"
                //+"  --------1.1.1. Купить хлеб\n"
                //+"  --------1.1.2. Купить молоко\n";
                //+ "2. Покормить собаку\n";
        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }
}