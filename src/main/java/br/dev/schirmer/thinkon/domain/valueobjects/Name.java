package br.dev.schirmer.thinkon.domain.valueobjects;

import br.dev.schirmer.thinkon.domain.exceptions.Notification;

import java.util.List;
import java.util.regex.Pattern;

public record Name(String value) implements ValueObject<String> {
    private static final String NAME_REGEX = "^.{4,}$";
    private static final Pattern PATTERN = Pattern.compile(NAME_REGEX, Pattern.CASE_INSENSITIVE);

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
                            "Input should be at least 4 characters long."
                    )
            );
        }
    }
}
