package com.atgcorp.retail.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Slf4j
@Converter(autoApply = true)
public class OffsetDateTimeConverter implements AttributeConverter<OffsetDateTime, String> {

    public static final String DATE_TIME_OFFSET_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss[.SSSSSSSSS][.SSSSSS][.SSSSS][.SSSS][.SSS]X";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_OFFSET_FORMATTER_PATTERN);

    @Override
    public String convertToDatabaseColumn(OffsetDateTime offsetDateTime) {
        log.debug("Converting OffsetDateTime to Database Column");
        String value = null;
        if (!ObjectUtils.isEmpty(offsetDateTime)) {
            if (!offsetDateTime.getOffset().equals(ZoneOffset.UTC)) {
                value = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC).format(formatter);
            } else {
                value = offsetDateTime.format(formatter);
            }
        }
        return value;
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(String dateTimeString) {
        log.debug("Converting String DateTime to Entity Attribute: ", dateTimeString);
        OffsetDateTime value = null;
        dateTimeString = dateTimeString.trim();
        if (StringUtils.hasText(dateTimeString)) {
            value = OffsetDateTime.parse(dateTimeString, formatter);
            if (!value.getOffset().equals(ZoneOffset.UTC)) {
                value = value.withOffsetSameInstant(ZoneOffset.UTC);
            }
        }
        return value;
    }
}

