package ru.job4j.ood.isp.menu;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        boolean success = false;

        if (Objects.equals(Menu.ROOT, parentName)) {
            MenuItem child = new SimpleMenuItem(childName, actionDelegate);
            rootElements.add(child);
            success = true;
        } else {
            Optional<ItemInfo> foundItem = findItem(parentName);
            if (foundItem.isPresent()) {
                MenuItem parent = foundItem.get().menuItem;
                MenuItem child = new SimpleMenuItem(childName, actionDelegate);
                parent.getChildren().add(child);
                success = true;
            }
        }
        return success;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<ItemInfo> foundItem = findItem(itemName);

        if (foundItem.isPresent()) {
            ItemInfo itemInfo = foundItem.get();
            MenuItem menuItem = itemInfo.menuItem;
            String number = itemInfo.number;

            return Optional.of(
                    new MenuItemInfo(
                            menuItem.getName(),
                            menuItem.getChildren().stream().map(MenuItem::getName).collect(Collectors.toList()),
                            menuItem.getActionDelegate(),
                            number
                    )
            );
        }
        return Optional.empty();
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        DFSIterator dfsIterator = new DFSIterator();
        return new Iterator<MenuItemInfo>() {
            @Override
            public boolean hasNext() {
                return dfsIterator.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                ItemInfo currentItem = dfsIterator.next();
                return new MenuItemInfo(
                        currentItem.menuItem,
                        currentItem.number
                );
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        DFSIterator iterator = new DFSIterator();
        Optional<ItemInfo> result = Optional.empty();
        while (iterator.hasNext()) {
            ItemInfo currentItem = iterator.next();

            if (currentItem.menuItem.getName().equals(name)) {
                result = Optional.of(currentItem);
            }
        }
        return result;
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    /*
    итератор для обхода дерева меню с помощью алгоритма DFS (Depth-First Search),
    что переводится как "поиск в глубину"
     */
    private class DFSIterator implements Iterator<ItemInfo> {

        private Deque<MenuItem> stack = new LinkedList<>();

        private Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        private MenuItem menuItem;
        private String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}