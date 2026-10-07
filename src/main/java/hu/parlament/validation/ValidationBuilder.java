package hu.parlament.validation;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public final class ValidationBuilder<X> {

    private final X entity;
    private final List<String> errors = new ArrayList<>();

    private ValidationBuilder(X entity) {
        this.entity = entity;
    }

    public static <X> ValidationBuilder<X> of(X entity) {
        return new ValidationBuilder<>(entity);
    }

    public ValidationBuilder<X> failIf(Predicate<X> predicate, String message) {
        return failIf(predicate, (e) -> message);
    }

    public ValidationBuilder<X> failIf(Predicate<X> predicate, Function<X,String> message) {
        if (predicate.test(entity)) {
            errors.add(message.apply(entity));
        }
        return this;
    }

    public void validate() {
        if (!errors.isEmpty()) {
            log.error("Validation failed: {}", errors);
            throw new VoteValidationException(errors);
        }
    }
}