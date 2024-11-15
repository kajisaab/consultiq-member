package global.kajisaab.common.constants;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum RoleEnum {

    SUPER_ADMIN("Super Admin"),

    USER("User"),

    ADMIN("Admin");

    private final String displayName;

    RoleEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }

    public static RoleEnum fromDisplayName(String displayName){
        for(RoleEnum roleEnum: RoleEnum.values()){
            if(roleEnum.getDisplayName().equals(displayName)) {
                return roleEnum;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
