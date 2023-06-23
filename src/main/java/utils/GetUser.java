package utils;

public class GetUser {
    public static int getUid(String name) {
        for(var e : Bus.friendList) {
            if(e.name.equals(name)) {
                return e.ID;
            }
        }
        return -1;
    }

    public static String getName(int id) {
        for(var e: Bus.friendList) {
            if(e.ID == id) {
                return  e.name;
            }
        }
        return "我";
    }
}
