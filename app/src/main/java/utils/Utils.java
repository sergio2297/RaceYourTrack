package utils;

public abstract class Utils {

    /**
     * @return true iff obt is a Number 1 or a String "true".ignoreCase()
     */
    public static Boolean parseToBoolean(final Object obj) {
        if(obj instanceof Number && ((Number)obj).intValue() == 1
                || obj instanceof String && ((String)obj).equalsIgnoreCase("true")) {
            return true;
        }

        if(obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }

        return false;
    }
}
