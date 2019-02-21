package io.qameta.atlas.core;

import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.core.testdata.CustomException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class DefaultMethodTest {

    private Atlas atlas;

    @Before
    public void initAtlas() {
        atlas = new Atlas()
                .context(new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class)))
                .extension(new DefaultMethodExtension());
    }

    @Test
    public void shouldExecuteDefaultMethod() {
        InterfaceWithDefaultMethod instance = atlas
                .create(new Object(), InterfaceWithDefaultMethod.class);
        instance.doSomething();
    }

    @Test(expected = CustomException.class)
    public void shouldPropagateExceptionInDefaultMethod() {
        InterfaceWithDefaultMethodThrowable instance = atlas
                .create(new Object(), InterfaceWithDefaultMethodThrowable.class);
        instance.doSomething();
    }

    public interface InterfaceWithDefaultMethod {

        default void doSomething() {
        }

    }

    public interface InterfaceWithDefaultMethodThrowable {

        default void doSomething() {
            throw new CustomException();
        }

    }
}
