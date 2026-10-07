package hu.parlament.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class VoteValidationException extends RuntimeException {

    private final List<String> errors;

    public VoteValidationException(List<String> errors) {
      super("Validation failed");
      this.errors = List.copyOf(errors);
    }

    public VoteValidationException(String error) {
      this(List.of(error));
    }
}
