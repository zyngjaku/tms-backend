package io.github.zyngjaku.tmsbackend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleTests {
    @Test
    public void passSimpleTest() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    public void passSimpleTest2() {
        assertThat(2).isEqualTo(2);
    }

    @Test
    public void passSimpleTest3() {
        assertThat(3).isEqualTo(3);
    }

    @Test
    public void passSimpleTest4() {
        assertThat(4).isEqualTo(4);
    }

    @Test
    public void passSimpleTest5() {
        assertThat(5).isEqualTo(5);
    }

    @Test
    public void passFailTest() {
        assertThat(1).isEqualTo(2);
    }

    @Test
    @Disabled("Ignore tests, not ready")
    public void simpleSkipTest() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @Disabled("Ignore tests, not ready")
    public void simpleSkipTest2() {
        assertThat(1).isEqualTo(2);
    }
}
