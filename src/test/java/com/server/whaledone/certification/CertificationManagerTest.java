package com.server.whaledone.certification;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CertificationManagerTest {

    @InjectMocks
    CertificationManager certificationManager;

    @Test
    void randomGenerator() {
        for (int i = 0; i < 10; i++) {
            String s = certificationManager.randomGenerator(5);
            Assertions.assertThat(StringUtils.isNumeric(s)).isTrue();
        }
    }
}
