package global.kajisaab.common.constants;

public enum ConsultancyStatus {

    ACTIVE("ACTIVE", "Active"),

    INACTIVE("INACTIVE", "Inactive");

    private final String name;

    private final String displayName;

    ConsultancyStatus(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public static ConsultancyStatus getFromName(String name) throws IllegalArgumentException {
        for (ConsultancyStatus consultancyStatus : ConsultancyStatus.values()) {
            if (consultancyStatus.getName().equals(name)) {
                return consultancyStatus;
            }
        }
        throw new IllegalArgumentException("Cannot find the ConsultancyStatus with name: " + name);
    }

    public static ConsultancyStatus getFromDisplayName(String displayName) throws IllegalArgumentException {
        for (ConsultancyStatus consultancyStatus : ConsultancyStatus.values()) {
            if (consultancyStatus.getDisplayName().equals(displayName)) {
                return consultancyStatus;
            }
        }
        throw new IllegalArgumentException("Cannot find the ConsultancyStatus with display name: " + displayName);
    }

}
