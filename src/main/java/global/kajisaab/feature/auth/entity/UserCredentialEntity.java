package global.kajisaab.feature.auth.entity;

import global.kajisaab.common.db.DbEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import jakarta.persistence.*;

@Entity
@Table(name = "user_credential")
public class UserCredentialEntity extends DbEntity {

    private String userId;

    private Integer maxLoginAttempts;

    private Integer loginAttempts;

    private String password;

    @TypeDef(type = DataType.JSON)
    @Column(name = "password_history")
    private String passwordHistory;

    private String generatedSalt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMaxLoginAttempts() {
        return maxLoginAttempts;
    }

    public void setMaxLoginAttempts(Integer maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(String passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public String getGeneratedSalt() {
        return generatedSalt;
    }

    public void setGeneratedSalt(String generatedSalt) {
        this.generatedSalt = generatedSalt;
    }
}
