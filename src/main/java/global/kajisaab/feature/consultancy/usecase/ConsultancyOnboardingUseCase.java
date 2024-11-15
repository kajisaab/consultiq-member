package global.kajisaab.feature.consultancy.usecase;

import global.kajisaab.common.constants.ConsultancyStatus;
import global.kajisaab.common.dto.LabelValuePair;
import global.kajisaab.common.utils.JsonUtils;
import global.kajisaab.common.utils.SequentialNumberGenerator;
import global.kajisaab.core.SecurityUtils.PasswordHelper;
import global.kajisaab.core.emailService.EmailRequestBuilder;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.consultancy.dto.ConsultancySchemaGeneratorDto;
import global.kajisaab.feature.consultancy.entity.ConsultancyEntity;
import global.kajisaab.feature.consultancy.repository.ConsultancyRepository;
import global.kajisaab.feature.consultancy.service.SendUserCredEmail;
import global.kajisaab.feature.consultancy.usecase.request.ConsultancyOnboardingUseCaseRequest;
import global.kajisaab.feature.consultancy.usecase.response.ConsultancyOnboardingUseCaseResponse;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class ConsultancyOnboardingUseCase implements UseCase<ConsultancyOnboardingUseCaseRequest, ConsultancyOnboardingUseCaseResponse> {

    private final SecurityService securityService;

    private final ConsultancyRepository consultancyRepository;

    private final SendUserCredEmail sendUserCredEmail;

    private final Logger LOG = LoggerFactory.getLogger(ConsultancyOnboardingUseCase.class);

    @Inject
    public ConsultancyOnboardingUseCase(SecurityService securityService, ConsultancyRepository consultancyRepository, SendUserCredEmail sendUserCredEmail) {
        this.securityService = securityService;
        this.consultancyRepository = consultancyRepository;
        this.sendUserCredEmail = sendUserCredEmail;
    }


    @Override
    public Mono<ConsultancyOnboardingUseCaseResponse> execute(ConsultancyOnboardingUseCaseRequest request) throws BadRequestException {

//        Optional<Authentication> authentication = securityService.getAuthentication();

//        if (authentication.isPresent()) {

//            Authentication authDetails = authentication.get();

//            UserDetailsEntity userDetails = (UserDetailsEntity) authDetails.getAttributes().get("userDetails");

        String makerName = request.getMakerName();

        String makerEmail = request.getMakerEmail();

        String password = PasswordHelper.generateRandomPassword(7);

        String generatedSalt = PasswordHelper.generateRandomSalt();

        ConsultancyEntity consultancyDetails = this.generateConsultancyDetails(request, new LabelValuePair("Aman", UUID.randomUUID().toString()));

        return PasswordHelper.encode(password, generatedSalt)
                .flatMap(hashedPassword ->
                        consultancyRepository.save(consultancyDetails)
                                .doOnSuccess(consultancyEntity -> {
                                    // Run generateConsultancySchema asynchronously
                                    ConsultancySchemaGeneratorDto consultancySchemaGeneratorDto = new ConsultancySchemaGeneratorDto(makerName, makerEmail, password, hashedPassword, generatedSalt);
                                    generateConsultancySchemaAsync(consultancyDetails.getConsultancyCode(), consultancySchemaGeneratorDto);

                                    this.sendUserCredentialEmail();
                                })
                                .then(Mono.just(new ConsultancyOnboardingUseCaseResponse("Success")))
                );
//        }

//        throw new UnauthorizedException("User is not Authorized to access this api");
    }

    private ConsultancyEntity generateConsultancyDetails(ConsultancyOnboardingUseCaseRequest request, LabelValuePair createdBy) {

        ConsultancyEntity consultancy = new ConsultancyEntity();

        consultancy.setId(UUID.randomUUID().toString());
        consultancy.setConsultancyCode(SequentialNumberGenerator.getNextNumber());
        consultancy.setRegisteredCompanyName(request.getRegisteredCompanyName());
        consultancy.setBusinessName(request.getBusinessName());
        consultancy.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        consultancy.setBusinessEmail(request.getBusinessEmail());
        consultancy.setBusinessLandlineNumber(request.getBusinessLandlineNumber());
        consultancy.setUserRegistrationCount(request.getUserRegistrationCount());
        consultancy.setStatus(ConsultancyStatus.ACTIVE.name()); // default status
        consultancy.setWebsite(request.getWebsite());
        consultancy.setLogo(request.getLogo());
        consultancy.setCountry(request.getCountry());
        consultancy.setState(request.getState());
        consultancy.setDistrict(request.getDistrict());
        consultancy.setMunicipality(request.getMunicipality());
        consultancy.setWard(request.getWard());
        consultancy.setStreet(request.getStreet());
        consultancy.setEnrolledOn(LocalDateTime.now());
        consultancy.setParentId(request.getParentId());
        consultancy.setMakerEmail(request.getMakerEmail());
        consultancy.setMakerName(request.getMakerName());
        consultancy.setBusinessContactPersonName(request.getBusinessContactPersonName());
        consultancy.setBusinessContactPersonMobileNumber(request.getBusinessContactPersonMobileNumber());
        consultancy.setBusinessContactPersonEmail(request.getBusinessContactPersonEmail());
        consultancy.setBusinessContactPersonLandline(request.getBusinessContactPersonLandline());
        consultancy.setBusinessContactPersonExtension(request.getBusinessContactPersonExtension());
        consultancy.setEmergencyContactPersonName(request.getEmergencyContactPersonName());
        consultancy.setEmergencyContactPersonEmail(request.getEmergencyContactPersonEmail());
        consultancy.setEmergencyContactPersonMobileNumber(request.getEmergencyContactPersonMobileNumber());
        consultancy.setEmergencyContactPersonLandline(request.getEmergencyContactPersonLandline());
        consultancy.setEmergencyContactPersonExtension(request.getEmergencyContactPersonExtension());
        consultancy.setCreatedBy(createdBy);
        consultancy.setCreatedOn(LocalDateTime.now());

        return consultancy;
    }


    private void generateConsultancySchemaAsync(String consultancyCode, ConsultancySchemaGeneratorDto consultancySchemaGeneratorDto) {
        Mono.defer(() -> generateConsultancySchema(consultancyCode, consultancySchemaGeneratorDto))
                .subscribeOn(Schedulers.boundedElastic()) // Run on a separate thread
                .subscribe(result -> LOG.info("Consultancy Schema Generated Successfully"),
                        error -> LOG.error("Failed to generate consultancy schema", error));
    }

    private Mono<Boolean> generateConsultancySchema(String consultancyCode, ConsultancySchemaGeneratorDto consultancySchemaGeneratorDto) {

        return consultancyRepository.generateConsultancySchema(consultancyCode, JsonUtils.toJson(consultancySchemaGeneratorDto));
    }

    private void sendUserCredentialEmail() {
        Mono.defer(() -> {
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("name", "Aman");
                    templateModel.put("signupDate", LocalDate.now().toString());

                    return this.sendUserCredEmail.execute(
                            EmailRequestBuilder.builder()
                                    .receiverEmail("amankhadka101@gmail.com")
                                    .subject("Welcome")
                                    .templateModel(templateModel)
                                    .body("welcomeEmail")
                                    .build());
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
