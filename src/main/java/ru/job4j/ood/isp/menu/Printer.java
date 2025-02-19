package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo itemInfo : menu) {
            System.out.println(printMenu(itemInfo));
        }
    }

    /*
    В методе добавляются пробелы исходя из длины номера в меню.
     Выбираем максимальную длину между 0 и длиной номера минус единица -
     на полученное число повторов выводим "----" и прибавляем к пробелам
     номер
     */
    private String printMenu(Menu.MenuItemInfo itemInfo) {
        StringBuilder sb = new StringBuilder();
        String number = itemInfo.getNumber();
        return sb.append("----".repeat(Math.max(0, number.split("\\.").length - 1)))
                .append(number)
                .append(" ")
                .append(itemInfo.getName())
                .toString();
    }
}