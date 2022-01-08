package mahat.aviran.common.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SeriesStatus {
    CANCELED("Canceled"),
    ENDED("Ended"),
    RETURNING_SERIES("On Air");

    private final String rawValue;
}
