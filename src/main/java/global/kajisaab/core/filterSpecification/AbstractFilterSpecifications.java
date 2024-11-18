package global.kajisaab.core.filterSpecification;

import global.kajisaab.core.exceptionHandling.impl.DomainViolationExceptionImpl;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;

import static global.kajisaab.core.filterSpecification.CriteriaBuilderSupport.*;

public abstract class AbstractFilterSpecifications<Specs extends AbstractFilterSpecifications<Specs, FilterEntity>, FilterEntity> {

    protected  final Set<Violation> violations = new HashSet<>();
    protected final List<BiFunction<Root<FilterEntity>, CriteriaBuilder, Predicate>> andPredicates = new ArrayList<>();
    protected final List<BiFunction<Root<FilterEntity>, CriteriaBuilder, Predicate>> orPredicates = new ArrayList<>();

    private final Logger LOG = LoggerFactory.getLogger(AbstractFilterSpecifications.class);

    public QuerySpecification<FilterEntity> buildSpecification() {
        throwIfViolationExist();

//        return (root, criteriaQuery, criteriaBuilder) -> {
//            // Only include non-empty predicates in the 'and' clause
//            Predicate[] andPredicateArray = andPredicates.stream()
//                    .map(p -> p.apply(root, criteriaBuilder))
//                    .filter(Objects::nonNull)  // Filter out null predicates to avoid empty conditions
//                    .toArray(Predicate[]::new);
//
//            // Similarly, filter out null predicates for 'or' clause
//            Predicate[] orPredicateArray = orPredicates.stream()
//                    .map(p -> p.apply(root, criteriaBuilder))
//                    .filter(Objects::nonNull)
//                    .toArray(Predicate[]::new);
//
//            // Ensure there are valid predicates before adding AND/OR conditions
//            Predicate andPredicate = andPredicateArray.length > 0 ? criteriaBuilder.and(andPredicateArray) : null;
//            Predicate orPredicate = orPredicateArray.length > 0 ? criteriaBuilder.or(orPredicateArray) : null;
//
//            // Combine 'AND' and 'OR' clauses if they exist
//            if (andPredicate != null && orPredicate != null) {
//                return criteriaBuilder.and(orPredicate, andPredicate);
//            } else if (andPredicate != null) {
//                return andPredicate;
//            } else if (orPredicate != null) {
//                return orPredicate;
//            }
//
//            return criteriaBuilder.conjunction(); // Return a no-op predicate if no conditions
//        };

        return (root, criteriaQuery, criteriaBuilder) -> {
            var andPredicateArray = andPredicates.stream()
                    .map(p -> p.apply(root, criteriaBuilder))
                    .filter(Objects::nonNull) // Filter out null predicates
                    .toArray(Predicate[]::new);

            var orPredicateArray = orPredicates.stream()
                    .map(p -> p.apply(root, criteriaBuilder))
                    .filter(Objects::nonNull)
                    .toArray(Predicate[]::new);

            Predicate finalAndPredicate = andPredicateArray.length > 0 ? criteriaBuilder.and(andPredicateArray) : null;
            Predicate finalOrPredicate = orPredicateArray.length > 0 ? criteriaBuilder.or(orPredicateArray) : null;

            // Combine only if both predicates exist
            if (finalAndPredicate != null && finalOrPredicate != null) {
                return criteriaBuilder.and(finalOrPredicate, finalAndPredicate);
            } else if (finalAndPredicate != null) {
                return criteriaBuilder.and(finalAndPredicate);
            } else if (finalOrPredicate != null) {
                return criteriaBuilder.and(finalOrPredicate);
            }

            return criteriaBuilder.conjunction(); // Return no-op predicate if no conditions
        };
    }

    public <Value> Specs equal(String field, Value value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(equalCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public <Value extends Comparable<Value>> Specs lessThan(String field, Value value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(lessThanCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public <Value extends Comparable<Value>> Specs greaterThan(String field, Value value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(greaterThanCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public Specs anyLike(String field, String value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(anyLikeCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public Specs beginLike(String field, String value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(beginLikeCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public Specs endLike(String field, String value) {
        if (Objects.nonNull(value)) {
            addToAndPredicate(endLikeCriteria(value, root -> root.get(field)));
        }

        return this.self();
    }

    public <Value> Specs in(String field, List<Value> values) {
        if (Objects.nonNull(values) && !values.isEmpty()) {
            addToAndPredicate(inCriteria(values, root -> root.get(field)));
        }

        return this.self();
    }

    public <Value, InnerEntity> Specs innerEntityHasIn(
            String field,
            List<Value> values,
            BiFunction<Root<FilterEntity>, CriteriaBuilder, Join<FilterEntity, InnerEntity>> joinFunction
    ) {
        if (Objects.nonNull(values) && !values.isEmpty()) {
            values.forEach(value -> addToOrPredicate(
                    equalCriteria(value, root -> joinFunction.apply(root, null).get(field))
            ));
        }

        return this.self();
    }

    public <Value extends Comparable<Value>> Specs between(String field, Value start, Value end) {
        if (Objects.nonNull(start) && Objects.nonNull(end)) {
            addToAndPredicate(betweenCriteria(start, end, root -> root.get(field)));
        }

        return this.self();
    }

    public Specs andAnyLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(andAnyLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs andBeginLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(andBeginLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs andEndLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(andEndLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs orAnyLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(orAnyLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs orBeginLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(orBeginLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs orEndLike(String fieldOne, String fieldTwo, String valueOne, String valueTwo) {
        if (Objects.nonNull(valueOne) && Objects.nonNull(valueTwo)) {
            addToAndPredicate(orEndLikeCriteria(valueOne, root -> root.get(fieldOne), valueTwo, root -> root.get(fieldTwo)));
        }

        return this.self();
    }

    public Specs globalSearch(String keyword, Object filterObjectDto) {

        Field[] fields = filterObjectDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Make private fields accessible
            String fieldName = field.getName(); // Get the field name

            addToOrPredicate(anyLikeCriteria(keyword, root -> root.get(fieldName)));
        }

        return this.self();
    };

    protected void addToAndPredicate(BiFunction<Root<FilterEntity>, CriteriaBuilder, Predicate> predicateBiFunction) {
        andPredicates.add(predicateBiFunction);
    }

    protected void addToOrPredicate(BiFunction<Root<FilterEntity>, CriteriaBuilder, Predicate> predicateBiFunction) {
        orPredicates.add(predicateBiFunction);
    }

    protected void throwIfViolationExist() {
        if (!violations.isEmpty()) {
            throw new DomainViolationExceptionImpl(violations);
        }
    }

    @SuppressWarnings("unchecked")
    protected Specs self() {
        return (Specs) this;
    }
}
