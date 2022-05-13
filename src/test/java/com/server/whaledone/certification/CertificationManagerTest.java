package com.server.whaledone.certification;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CertificationManagerTest {

    @Test
    void randomGenerator() {
        CertificationManager certificationManager = new CertificationManager();
        for (int i = 0; i < 10; i++) {
            String s = certificationManager.randomGenerator(5);
            Assertions.assertThat(StringUtils.isNumeric(s)).isTrue();
        }
    }
}
