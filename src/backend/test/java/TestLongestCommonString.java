import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestLongestCommonString {

    @Test
    public void testLongestCommonString() {
        assertThat(longestCommonString("abcdefg", "de"), is("de"));
        assertThat(longestCommonString("abcdefg", "deUU"), is("de"));
        assertThat(longestCommonString("abcdefg", "fghi"), is("fg"));
        assertThat(longestCommonString("abcdefg", "not foun"), is(""));
        assertThat(longestCommonString("", "not foun"), is(""));
        assertThat(longestCommonString("abcdefg", ""), is(""));

        assertThat(longestCommonString("aa aaaaaa aaa", "aaaaaaaa"), is("aaaaaa"));

        assertThat(longestCommonString("久标准", "准久"), is("准"));
        assertThat(longestCommonString("久标准", "久标"), is("久标"));
        assertThat(longestCommonString("久标准", "入"), is(""));
    }

    private String longestCommonString(String abcdefg, String fghi) {
        return null;
    }
}
