package global.kajisaab.feature.auth.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Serdeable
@Introspected
public class LoginUserCaseRequestDto {

    @NotBlank(message = "Email is required")
    @Schema(description = "The email address of the user", defaultValue = "user@example.com")
    private String email = "user@example.com";

    @NotBlank(message = "Password is required")
    @Schema(description = "The password for the user account", defaultValue = "=FZ9[m7")
    private String password = "=FZ9[m7";


    public @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }
}
