package {{ group }};

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class LibraryTest {

    @Test
    void greetFormatsTheName() {
        assertThat(Library.greet("Java")).isEqualTo("Hello from Java!");
    }

    @Test
    void greetRejectsNull() {
        assertThatThrownBy(() -> Library.greet(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
