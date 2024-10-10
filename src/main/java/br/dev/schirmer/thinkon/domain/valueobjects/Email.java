package br.dev.schirmer.thinkon.domain.valueobjects;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;
import java.util.regex.Pattern;

public record Email(String value) implements ValueObject<String> {
    private static final String CANADIAN_PHONE_NUMBER_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final Pattern PATTERN = Pattern.compile(CANADIAN_PHONE_NUMBER_REGEX, Pattern.CASE_INSENSITIVE);

    public void validate(Boolean required, List<Notification> notifications) {
        if (required && value == null) {
            notifications.add(
                    new Notification(
                            "email",
                            "email is required."
                    )
            );
            return;
        }
        if (!PATTERN.matcher(value).matches()) {
            notifications.add(
                    new Notification(
                            "email",
                            "Invalid email address."
                    )
            );
        }
    }
}
