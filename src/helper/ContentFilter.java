package helper;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class ContentFilter {
    /**
     * Takes the itemList and filters items according to the given type, returns a new arraylist with filtered content
     * @param itemList
     * @param typeToFilterBy
     * @return new filtered item arrayList according to the given type
     */
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

    /**
     * Takes the itemList and filters items according to the given string, returns a new arraylist with filtered content
     * @param itemList
     * @param stringToFilterBy
     * @return new filtered item arrayList according to the given string
     */
    public static List<Item> getFilteredItemList(List<Item> itemList, String stringToFilterBy) {
        List<Item> list = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(stringToFilterBy.toLowerCase())) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Takes the userList and filters users according to the given type, returns a new arraylist with filtered content
     * @param userList
     * @param typeToFilterBy
     * @return new filtered user arrayList according to the given type
     */
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
                        if (((Administrator) user).getPrivilegeLevel() == 2) {
                            list.add((Administrator) user);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case LOW_ADMIN:
                    try {
                        if (((Administrator) user).getPrivilegeLevel() == 1) {
                            list.add((Administrator) user);
                        }
                    } catch (Exception e) {
                    }
                    break;
            }
        }
        return list;
    }

    /**
     * Takes the userList and filters users according to the given string, returns a new arraylist with filtered content
     * @param userList
     * @param stringToFilterBy
     * @return new filtered user arrayList according to the given string
     */
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
