package global.kajisaab.common.constants;

public enum TokenTypeEnum {

    ACCESS_TOKEN("ACCESS_TOKEN", "Access Token"),

    REFRESH_TOKEN("REFRESH_TOKEN", "Refresh Token");

    private final String name;

    private final String displayName;

    TokenTypeEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public static TokenTypeEnum getFromName(String name) throws IllegalArgumentException {
        for (TokenTypeEnum tokenTypeEnum : TokenTypeEnum.values()) {
            if (tokenTypeEnum.getName().equals(name)) {
                return tokenTypeEnum;
            }
        }
        throw new IllegalArgumentException("Cannot find the TokenTypeEnum with name: " + name);
    }

    public static TokenTypeEnum getFromDisplayName(String displayName) throws IllegalArgumentException {
        for (TokenTypeEnum tokenTypeEnum : TokenTypeEnum.values()) {
            if (tokenTypeEnum.getDisplayName().equals(displayName)) {
                return tokenTypeEnum;
            }
        }
        throw new IllegalArgumentException("Cannot find the TokenTypeEnum with display name: " + displayName);
    }
}
