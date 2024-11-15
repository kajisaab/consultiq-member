package global.kajisaab.core.SecurityUtils;

import com.google.common.hash.Hashing;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;

@Singleton
public class PasswordHelper {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static Mono<String> encode(String plainText, String salt) {
        String firstHash = Hashing.sha256().hashString(plainText, StandardCharsets.UTF_8).toString();
        String finalHash = "";
        if (salt != null)
            finalHash = Hashing.sha256().hashString(firstHash + salt, StandardCharsets.UTF_8).toString();
        else finalHash = firstHash;
        return Mono.just(finalHash);
    }

    public static Mono<Boolean> isEqualEncoding(String encodedPassword, String plainText, String salt) {
        return encode(plainText, salt).map(x -> Objects.equals(encodedPassword, x));
    }

    public static String generateRandomSalt() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomPassword(int length) {


        StringBuilder password = new StringBuilder(length);

        // Ensure password contains at least one character from each category
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(RANDOM.nextInt(SPECIAL_CHARACTERS.length())));

        // Fill the remaining characters randomly from all characters
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())));
        }

        // Shuffle the characters to avoid predictable patterns
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length);
            // Swap characters
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }
}
