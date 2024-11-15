package global.kajisaab.feature.auth.entity;

import global.kajisaab.common.db.DbEntity;
import global.kajisaab.common.dto.LabelValuePair;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_details")
public class UserDetailsEntity extends DbEntity {

    private String fullName;

    private String email;

    private String employeeId;

    private String userType;

    private String userImage;

    private String userPosition;

    private boolean blocked;

    private boolean active;

    @TypeDef(type = DataType.JSON)
    @Column(name = "roles")
    private List<LabelValuePair> roles;

    @TypeDef(type = DataType.JSON)
    @Column(name = "branch")
    private LabelValuePair branch;

    private String selectedTheme;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<LabelValuePair> getRoles() {
        return roles;
    }

    public void setRoles(List<LabelValuePair> roles) {
        this.roles = roles;
    }

    public String getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(String selectedTheme) {
        this.selectedTheme = selectedTheme;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public LabelValuePair getBranch() {
        return branch;
    }

    public void setBranch(LabelValuePair branch) {
        this.branch = branch;
    }
}
