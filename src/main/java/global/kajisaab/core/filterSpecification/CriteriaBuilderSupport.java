package global.kajisaab.core.filterSpecification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CriteriaBuilderSupport {

    public static final String PERCENT_SIGN = "%";

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> anyLikeCriteria(
            String field,
            Function<Root<E>, Path<String>> expressionFunction
    ){
        String searchTermWithWildcards = "%" + field + "%";

        return (root, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(expressionFunction.apply(root)),
                searchTermWithWildcards.toLowerCase()
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> beginLikeCriteria(
            String field,
            Function<Root<E>, Path<String>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .like(expressionFunction.apply(root), appendPercentSign(field));
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> endLikeCriteria(
            String field,
            Function<Root<E>, Path<String>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .like(expressionFunction.apply(root), prependPercentSign(field));
    }

    public static <T, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> equalCriteria(
            T field,
            Function<Root<E>, Path<T>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .equal(expressionFunction.apply(root), field);
    }

    public static <T, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> notEqualCriteria(
            T field,
            Function<Root<E>, Path<T>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .notEqual(expressionFunction.apply(root), field);
    }

    public static <T extends Comparable<T>, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> betweenCriteria(
            T start, T end,
            Function<Root<E>, Path<T>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .between(expressionFunction.apply(root), start, end);
    }

    public static <T extends Comparable<T>, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> lessThanCriteria(
            T field,
            Function<Root<E> , Path<T>> expressionFunction
    ){
        return (root, criteriaBuilder) -> criteriaBuilder
                .lessThan(expressionFunction.apply(root), field);
    }


    public static <T extends Comparable<T>, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> greaterThanCriteria(
            T field,
            Function<Root<E>, Path<T>> expressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder
                .greaterThan(expressionFunction.apply(root), field);
    }

    public static <T extends Comparable<T>, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> lessThanOrEqualToCriteria(
            T field,
            Function<Root<E>, Path<T>> expressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(expressionFunction.apply(root), field);
    }

    public static <T extends Comparable<T>, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> greaterThanOrEqualToCriteria(
            T field,
            Function<Root<E>, Path<T>> expressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(expressionFunction.apply(root), field);
    }

    public static <T, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> inCriteria(
            List<T> fields,
            Function<Root<E>, Path<T>> expressionFunction
    ) {
        return (root, criteriaBuilder) -> {
            var inClause = criteriaBuilder.in(expressionFunction.apply(root));
            fields.forEach(inClause::value);
            return inClause;
        };
    }

    public static <T, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> notInCriteria(
            List<T> fields,
            Function<Root<E>, Path<T>> expressionFunction
    ) {
        return (root, criteriaBuilder) -> {
            var inClause = criteriaBuilder.in(expressionFunction.apply(root));
            fields.forEach(inClause::value);
            return inClause.not();
        };
    }

    public static <T, U, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> andEqualCriteria(
            T firstField, Function<Root<E>, Path<T>> firstExpressionFunction,
            U secondField, Function<Root<E>, Path<U>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(firstExpressionFunction.apply(root), firstField),
                criteriaBuilder.equal(secondExpressionFunction.apply(root), secondField)
        );
    }

    public static <T, U, E> BiFunction<Root<E>, CriteriaBuilder, Predicate> orEqualCriteria(
            T firstField, Function<Root<E>, Path<T>> firstExpressionFunction,
            U secondField, Function<Root<E>, Path<U>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(firstExpressionFunction.apply(root), firstField),
                criteriaBuilder.equal(secondExpressionFunction.apply(root), secondField)
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> andAnyLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.like(firstExpressionFunction.apply(root), decorateWithPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), decorateWithPercentSign(secondField))
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> andBeginLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.like(firstExpressionFunction.apply(root), appendPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), appendPercentSign(secondField))
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> andEndLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.like(firstExpressionFunction.apply(root), prependPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), prependPercentSign(secondField))
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> orAnyLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(firstExpressionFunction.apply(root), decorateWithPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), decorateWithPercentSign(secondField))
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> orBeginLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(firstExpressionFunction.apply(root), appendPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), appendPercentSign(secondField))
        );
    }

    public static <E> BiFunction<Root<E>, CriteriaBuilder, Predicate> orEndLikeCriteria(
            String firstField, Function<Root<E>, Path<String>> firstExpressionFunction,
            String secondField, Function<Root<E>, Path<String>> secondExpressionFunction
    ) {
        return (root, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(firstExpressionFunction.apply(root), prependPercentSign(firstField)),
                criteriaBuilder.like(secondExpressionFunction.apply(root), prependPercentSign(secondField))
        );
    }

    private static String appendPercentSign(String field) {
        return field + PERCENT_SIGN;
    }

    private static String prependPercentSign(String field) {
        return PERCENT_SIGN + field;
    }

    private static String decorateWithPercentSign(String field) {
        return PERCENT_SIGN + field + PERCENT_SIGN;
    }
}
