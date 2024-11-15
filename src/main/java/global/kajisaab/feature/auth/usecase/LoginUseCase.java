package global.kajisaab.feature.auth.usecase;

import global.kajisaab.common.dto.LabelValuePair;
import global.kajisaab.core.SecurityUtils.PasswordHelper;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.jwtService.JwtService;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.auth.dto.LoginResponseUserDetails;
import global.kajisaab.feature.auth.dto.LoginResponseUserDetailsBuilder;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.auth.repository.UserCredentialRepository;
import global.kajisaab.feature.auth.repository.UserDetailsRepository;
import global.kajisaab.feature.auth.usecase.request.LoginUseCaseRequest;
import global.kajisaab.feature.auth.usecase.response.LoginUseCaseResponse;
import global.kajisaab.feature.roles.repository.RolesRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class LoginUseCase implements UseCase<LoginUseCaseRequest, LoginUseCaseResponse> {

    private final UserDetailsRepository userDetailsRepository;

    private final UserCredentialRepository userCredentialRepository;

    private final RolesRepository rolesRepository;

    private final JwtService jwtService;

    @Inject
    public LoginUseCase(UserDetailsRepository userDetailsRepository, UserCredentialRepository userCredentialRepository, RolesRepository rolesRepository, JwtService jwtService) {
        this.userDetailsRepository = userDetailsRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.rolesRepository = rolesRepository;
        this.jwtService = jwtService;
    }

    /**
     * Executes the login process by first finding the user by email, validating the user credentials,
     * and generating the necessary tokens and permissions.
     *
     * @param request The login request containing user credentials and consultancy code.
     * @return A Mono containing the login response with access token, refresh token, and user details.
     */
    @Override
    public Mono<LoginUseCaseResponse> execute(LoginUseCaseRequest request) {
        return findUserByEmail(request.email())
                .flatMap(userDetails -> validateUserAndPassword(userDetails, request.password()))
                .flatMap(userDetails -> generateTokensAndPermissions(userDetails, request.consultancyCode()));
    }

    /**
     * Finds a user by email. If no user is found, it returns an error.
     *
     * @param email The email address of the user.
     * @return A Mono containing the user details if found.
     */
    private Mono<UserDetailsEntity> findUserByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BadRequestException("Cannot find the user with " + email + " email")));
    }

    /**
     * Validates the user's credentials by checking if the password matches the stored password.
     * If the user is inactive, an error is returned. If the password is incorrect, an error is thrown.
     *
     * @param userDetails The details of the user.
     * @param password    The password provided by the user.
     * @return A Mono containing the user details if the password is valid.
     */
    private Mono<UserDetailsEntity> validateUserAndPassword(UserDetailsEntity userDetails, String password) {
        if (!userDetails.isActive()) {
            return Mono.error(new BadRequestException("User is disabled. Please contact Admin"));
        }
        return userCredentialRepository.findByUserId(userDetails.getId())
                .flatMap(userCred -> PasswordHelper.isEqualEncoding(userCred.getPassword(), password, userCred.getGeneratedSalt())
                        .flatMap(isPasswordValid -> isPasswordValid
                                ? Mono.just(userDetails)
                                : Mono.error(new BadRequestException("Invalid password"))));
    }

    /**
     * Generates access and refresh tokens for the user, fetches the user's roles,
     * and gathers their permissions based on the roles. It then returns a response containing
     * the tokens, user details, and permissions.
     *
     * @param userDetails     The details of the authenticated user.
     * @param consultancyCode The consultancy code provided during login.
     * @return A Mono containing the login response with tokens, user details, and permissions.
     */
    private Mono<LoginUseCaseResponse> generateTokensAndPermissions(UserDetailsEntity userDetails, String consultancyCode) {
        // Generate the access and refresh tokens for the user
        String accessToken = jwtService.generateAccessToken(userDetails, consultancyCode);
        String refreshToken = jwtService.generateRefreshToken(userDetails, consultancyCode);

        // Map the user's roles into a list of role IDs
        List<String> roleIds = userDetails.getRoles().stream()
                .map(LabelValuePair::value)
                .collect(Collectors.toList());

        // Fetch the roles and permissions associated with the user's roles
        return rolesRepository.findAllByIdIn(roleIds)
                .collectList()
                .flatMap(rolesList -> {
                    // Aggregate all the permissions from the roles
                    List<String> permissions = rolesList.stream()
                            .flatMap(role -> role.getPermissions().stream())
                            .collect(Collectors.toList());

                    // Build the user details response
                    LoginResponseUserDetails userDetailsResponse = LoginResponseUserDetailsBuilder.builder()
                            .fullName(userDetails.getFullName())
                            .roles(userDetails.getRoles().stream().map(LabelValuePair::label).collect(Collectors.toList()))
                            .position(userDetails.getUserPosition())
                            .image(userDetails.getUserImage())
                            .build();

                    // Return the login response with tokens, user details, and permissions
                    return Mono.just(new LoginUseCaseResponse(accessToken, refreshToken, userDetailsResponse, permissions));
                });
    }
}
