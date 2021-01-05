package com.conta.cloud.sat.util;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Codig Unidad Service Testing")
public class DateUtilsTest {
    
    @Test
    public void fromDate_formatCorrectDate() {
        String date = DateUtils.fromDate(new Date());
        assertNotNull(date);
    }

    @Test
    public void fromDate_formatNullDate_shouldReturnEmptyString() {
        String date = DateUtils.fromDate(null);
        assertNotNull(date);
        assertEquals("", date);
    }
}
