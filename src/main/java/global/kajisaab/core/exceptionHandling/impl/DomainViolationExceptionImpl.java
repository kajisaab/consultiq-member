package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.common.utils.JsonUtils;
import global.kajisaab.core.filterSpecification.SimpleViolation;
import global.kajisaab.core.filterSpecification.Violation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DomainViolationExceptionImpl extends RuntimeException {


    private final Set<Violation> violations = new HashSet<>();

    public DomainViolationExceptionImpl(Set<Violation> violations) {
        super(violations
                .stream()
                .map(Violation::getErrorMessage)
                .reduce((e1, e2) -> e1 + ", " + e2)
                .orElse("")
        );
        this.violations.addAll(violations);
    }

    public DomainViolationExceptionImpl(String violator, String message) {
        super(message);
        this.violations.add(SimpleViolation.of(violator, message));
    }
    public DomainViolationExceptionImpl(String violator, String message, Throwable throwable) {
        super(message,throwable);
        this.violations.add(SimpleViolation.of(violator, message));
    }

    public DomainViolationExceptionImpl(String violator, Throwable throwable) {
        super(violator, throwable);
        this.violations.add(SimpleViolation.of(violator, throwable.getMessage()));
    }

    public Set<Violation> getViolations() {
        return Collections.unmodifiableSet(this.violations);
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(violations);
    }
}
