package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    void whenSelectExistingElement() {
        SimpleMenu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача общая", STUB_ACTION);
        menu.add("Задача общая", "Задача первая", STUB_ACTION);
        Optional<Menu.MenuItemInfo> selected = menu.select("Задача первая");
        assertThat(selected.isPresent()).isTrue();
        assertThat(selected.get().getName()).isEqualTo("Задача первая");
    }

    @Test
    void whenSelectNonExistingElement() {
        SimpleMenu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача общая", STUB_ACTION);
        Optional<Menu.MenuItemInfo> selected = menu.select("Задача вторая");
        assertThat(selected.isPresent()).isFalse();
    }

    @Test
    void whenAddRootElement() {
        SimpleMenu menu = new SimpleMenu();
        assertThat(menu.add(Menu.ROOT, "Задача общая", STUB_ACTION)).isTrue();
    }

    @Test
    void whenAddChildElement() {
        SimpleMenu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача общая", STUB_ACTION);
        assertThat(menu.add("Задача общая", "Задача первая", STUB_ACTION)).isTrue();
    }
}