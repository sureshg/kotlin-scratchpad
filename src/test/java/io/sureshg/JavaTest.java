package io.sureshg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JUnit5 Java Tests")
class JavaTest {

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void addTest() {
    assertEquals(2, 1 + 1);
  }

  @ParameterizedTest(name = "{0} + {1} = {2}")
  @CsvSource({"0,    1,   1", "1,    2,   3", "49,  51, 100", "1,  100, 101"})
  void add(int x, int y, int result) {
    assertEquals(result, x + y);
  }
}
