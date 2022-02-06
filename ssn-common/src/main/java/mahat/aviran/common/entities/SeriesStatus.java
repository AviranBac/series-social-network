package mahat.aviran.common.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SeriesStatus {
    CANCELED("Canceled"),
    ENDED("Ended"),
    RETURNING_SERIES("Returning Series");

    // The actual field value from TMDB
    private final String rawValue;

    // Used to retreive SeriesStatus from TMDB request
    @JsonCreator
    public static SeriesStatus forValue(String value) {
        return Arrays.stream(SeriesStatus.values())
                .filter(enumValue -> enumValue.rawValue.equalsIgnoreCase(value))
                .findAny()
                .orElse(null);
    }
}
