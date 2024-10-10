package br.dev.schirmer.thinkon.domain.valueobjects;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;
import java.util.regex.Pattern;

public record PhoneNumber(String value) implements ValueObject<String> {
    private static final String CANADIAN_PHONE_NUMBER_REGEX = "\\+1\\d{3}\\d{3}\\d{4}";
    private static final Pattern PATTERN = Pattern.compile(CANADIAN_PHONE_NUMBER_REGEX);

    @Override
    public void validate(Boolean required, String fieldName, List<Notification> notifications) {
        if (required && value == null) {
            notifications.add(
                    new Notification(
                            fieldName,
                            fieldName + " is required."
                    )
            );
            return;
        }
        if (!PATTERN.matcher(value).matches()) {
            notifications.add(
                    new Notification(
                            fieldName,
                            "Invalid value."
                    )
            );
        }
    }
}