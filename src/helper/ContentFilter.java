package helper;

import model.Item;

import java.util.ArrayList;
import java.util.List;

public class ContentFilter {
    public static List<Item> getFilteredItemList(List<Item> itemList, ItemFilterType typeToFilterBy) {
        List<Item> list = new ArrayList<>();

        for (Item item : itemList) {
            switch (typeToFilterBy) {
                case GUITAR:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                case PIANO:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                case STRING_INSTRUMENT:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                case PERCUSSION_INSTRUMENT:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                case WIND_INSTRUMENT:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                case AMP:
                    try {
                        list.add(item);
                    } catch (Exception e) {
                    }
                    break;
                default:
            }
        }
        return list;
    }

    public static List<Item> getFilteredItemList(List<Item> itemList, String stringToFilterBy) {
        List<Item> list = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(stringToFilterBy.toLowerCase())) {
                list.add(item);
            }
        }
        return list;
    }

    enum ItemFilterType {
        GUITAR, PIANO, STRING_INSTRUMENT, PERCUSSION_INSTRUMENT, WIND_INSTRUMENT, AMP
    }
}
