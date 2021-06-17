package helper;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class ContentFilter {
    public static List<Item> getFilteredItemList(List<Item> itemList, ItemFilterType typeToFilterBy) {
        List<Item> list = new ArrayList<>();

        for (Item item : itemList) {
            switch (typeToFilterBy) {
                case GUITAR:
                    try {
                        list.add((Guitar) item);
                    } catch (Exception e) {
                    }
                    break;
                case PIANO:
                    try {
                        list.add((Piano) item);
                    } catch (Exception e) {
                    }
                    break;
                case STRING_INSTRUMENT:
                    try {
                        list.add((StringInstrument) item);
                    } catch (Exception e) {
                    }
                    break;
                case PERCUSSION_INSTRUMENT:
                    try {
                        list.add((PercussionInstrument) item);
                    } catch (Exception e) {
                    }
                    break;
                case WIND_INSTRUMENT:
                    try {
                        list.add((WindInstrument) item);
                    } catch (Exception e) {
                    }
                    break;
                case AMP:
                    try {
                        list.add((Amp) item);
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

    public static List<User> getFilteredUserList(List<User> userList, UserFilterType typeToFilterBy) {
        List<User> list = new ArrayList<>();
        for (User user : userList) {
            switch (typeToFilterBy) {
                case CUSTOMER:
                    try {
                        list.add((Customer) user);
                    } catch (Exception e) {
                    }
                    break;
                case ALL_ADMIN:
                    try {
                        list.add((Administrator) user);
                    } catch (Exception e) {
                    }
                    break;
                case HIGH_ADMIN:
                    try {
                        if (((Administrator) user).getAuth().equals(Administrator.authLevel.HIGH)) {
                            list.add((Administrator) user);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case LOW_ADMIN:
                    try {
                        if (((Administrator) user).getAuth().equals(Administrator.authLevel.LOW)) {
                            list.add((Administrator) user);
                        }
                    } catch (Exception e) {
                    }
                    break;
            }
        }
        return list;
    }

    public static List<User> getFilteredUserList(List<User> userList, String stringToFilterBy) {
        List<User> list = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(stringToFilterBy.toLowerCase())) {
                list.add(user);
            }
        }
        return list;
    }

    public enum UserFilterType {
        HIGH_ADMIN, LOW_ADMIN, ALL_ADMIN, CUSTOMER
    }

    public enum ItemFilterType {
        GUITAR, PIANO, STRING_INSTRUMENT, PERCUSSION_INSTRUMENT, WIND_INSTRUMENT, AMP
    }
}
