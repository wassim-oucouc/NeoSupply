package org.example.neosupply;

import org.example.neosupply.config.TestDatabaseConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Import(TestDatabaseConfiguration.class)
class NeoSupplyApplicationTests {

    @Test
    void contextLoads() {
    }

}
