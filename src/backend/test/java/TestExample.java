import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestExample {

    @Test
    public void testContains() {
        assertThat(contains("bonjour", "on"), is(true));
        assertThat(contains("asdfghj", "on"), is(false));
        assertThat(contains("a", "abc"), is(false));
        assertThat(contains("", "on"), is(false));
        assertThat(contains("dsadsadwq", ""), is(true));
        assertThat(contains("", ""), is(true));

        assertThat(contains("ಮಣ್ಣಾಗಿ", "ಮ"), is(true));
        assertThat(contains("ಮಣ್ಣಾಗಿ", "ಗಿ"), is(true));
        assertThat(contains("ಮಣ್ಣಾಗಿ", "ಗಿಣ"), is(false));
        assertThat(contains("ಮಣ್ಣಾಗಿ", "ರ"), is(false));
    }

    private boolean contains(String text, String substring) {
        return false;
    }
}
