package io.sureshg

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import org.mockito.ArgumentMatchers.anyInt
import java.util.stream.*


@DisplayName("JUnit5 Kotlin Tests")
object KotlinTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `1 + 1 = 2`() {
        assertEquals(2, 1 + 1)
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource("0,    1,   1", "1,    2,   3", "49,  51, 100", "1,  100, 101")
    fun add(x: Int, y: Int, result: Int) {
        assertEquals(result, x + y)
    }

    @ParameterizedTest(name = "{0} - {1} = {2}")
    @MethodSource("argProvider")
    fun `subtract two numbers`(x: Int, y: Int, result: Int) {
        assertEquals(result, x - y)
    }

    @JvmStatic
    fun argProvider() =
        Stream.of(
            Arguments.of(2, 1, 1),
            Arguments.of(200, 100, 100),
            Arguments.of(100, 100, 0)
        )

    @Test
    @DisplayName("Mock tests a final class")
    fun `test mock objects`() {
        /* Given */
        val mock = mock<MockClass> {
            on { add(anyInt(), anyInt()) } doReturn 100
        }

        /* When */
        val result = mock.add(1, 20)
        assertEquals(100, result)

        /* Then */
        verify(mock).add(any(), eq(20))
    }
}