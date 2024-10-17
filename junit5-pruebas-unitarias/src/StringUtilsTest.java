import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas para StringUtils")
class StringUtilsTest {

    @Test
    @DisplayName("Prueba el método reverse con un valor nulo")
    public void testReverseWithNullInput() {
        // GIVEN
        String input = null;

        // WHEN
        String result = StringUtils.reverse(input);

        // THEN
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Prueba el método reverse con un String válido")
    public void testReverseWithValidString() {
        // GIVEN
        String input = "test";

        // WHEN
        String result = StringUtils.reverse(input);

        // THEN
        String expectedResult = "tset";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Prueba el método isPalindrome con un valor nulo")
    public void testIsPalindromeWithNullInput() {
        // GIVEN
        String input = null;

        // WHEN
        boolean result = StringUtils.isPalindrome(input);

        // THEN
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Prueba el método isPalindrome con un String no palíndromo")
    public void testIsPalindromeWithNonPalindromeString() {
        // GIVEN
        String input = "hello";

        // WHEN
        boolean result = StringUtils.isPalindrome(input);

        // THEN
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Prueba el método isPalindrome con un String palíndromo")
    public void testIsPalindromeWithPalindromeString() {
        // GIVEN
        String input = "level";

        // WHEN
        boolean result = StringUtils.isPalindrome(input);

        // THEN
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Prueba el método truncate con un valor nulo")
    public void testTruncateWithNullInput() {
        // GIVEN
        String stringInput = null;
        int maxLength = 5;

        // WHEN
        String result = StringUtils.truncate(stringInput, maxLength);

        // THEN
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Prueba el método truncate con un maxLength negativo")
    public void testTruncateWithNegativeMaxLength() {
        // GIVEN
        String stringInput = "world";
        int maxLength = -1;

        // WHEN
        String result = StringUtils.truncate(stringInput, maxLength);

        // THEN
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Prueba el método truncate con un maxLength cero")
    public void testTruncateWithZeroMaxLength() {
        // GIVEN
        String stringInput = "example";
        int maxLength = 0;

        // WHEN
        String result = StringUtils.truncate(stringInput, maxLength);

        // THEN
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Prueba el método truncate con maxLength mayor que la longitud de la cadena")
    public void testTruncateWithMaxLengthGreaterThanStringLength() {
        // GIVEN
        String stringInput = "short";
        int maxLength = 10;

        // WHEN
        String result = StringUtils.truncate(stringInput, maxLength);

        // THEN
        Assertions.assertEquals(stringInput, result);
    }

    @Test
    @DisplayName("Prueba el método truncate con maxLength menor que la longitud de la cadena")
    public void testTruncateWithMaxLengthLesserThanStringLength() {
        // GIVEN
        String stringInput = "testingggg";
        int maxLength = 7;

        // WHEN
        String result = StringUtils.truncate(stringInput, maxLength);

        // THEN
        String expectedResult = "testing";
        Assertions.assertEquals(expectedResult, result);
    }

}
