package global.kajisaab.common.constants;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum UserTypeEnum {

    AGENT("Agent"),

    SUB_AGENT("Sub Agent"),

    STUDENT("Student"),

    MANAGER("Manager"),

    RECEPTIONIST("Receptionist"),

    COUNSELOR("Counselor"),

    MANAGING_DIRECTOR("Managing Director");

    private final String displayName;

    UserTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }

    public static UserTypeEnum fromDisplayName(String displayName){
        for(UserTypeEnum userTypeEnum: UserTypeEnum.values()){
            if(userTypeEnum.getDisplayName().equals(displayName)) {
                return userTypeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }


}
