package global.kajisaab.common.constants;

public enum StatusEnum {

    ACTIVE("ACTIVE", "Active"),

    INACTIVE("INACTIVE", "Inactive");

    private final String name;

    private final String displayName;

    StatusEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public static StatusEnum getFromName(String name) throws IllegalArgumentException {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Cannot find the ConsultancyStatus with name: " + name);
    }

    public static StatusEnum getFromDisplayName(String displayName) throws IllegalArgumentException {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getDisplayName().equals(displayName)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Cannot find the ConsultancyStatus with display name: " + displayName);
    }
}
