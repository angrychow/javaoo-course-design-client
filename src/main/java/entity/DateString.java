package entity;

public class DateString {
    public String dateString;

    public String getMonth() {
        return dateString.substring(5,7);
    }
    public String getDate() {
        return dateString.substring(8,10);
    }
    public String getHours() {
        return dateString.substring(11,13);
    }

    public String getMinutes() {
        return dateString.substring(14,16);
    }
}
