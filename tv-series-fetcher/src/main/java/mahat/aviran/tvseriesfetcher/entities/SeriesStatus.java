package mahat.aviran.tvseriesfetcher.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SeriesStatus {
    CANCELED("Canceled", "בוטלה"),
    ENDED("Ended", "נגמרה"),
    RETURNING_SERIES("Returning Series", "משודרת כיום");

    private final String rawValue;
    private final String hebrewValue;

    @JsonCreator
    public static SeriesStatus forValue(String value) {
        return Arrays.stream(SeriesStatus.values())
                .filter(enumValue -> enumValue.rawValue.equalsIgnoreCase(value))
                .findAny()
                .get();
    }
}
