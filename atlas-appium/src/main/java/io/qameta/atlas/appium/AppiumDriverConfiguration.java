package io.qameta.atlas.appium;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.appium.context.AppiumDriverContext;
import io.qameta.atlas.appium.extension.*;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.webdriver.extension.ShouldMethodExtension;
import io.qameta.atlas.webdriver.extension.ToStringMethodExtension;

import java.util.Collections;


/**
 * Appium configuration.
 */
//CHECKSTYLE:OFF: ClassDataAbstractionCoupling
public class AppiumDriverConfiguration extends Configuration {

    public AppiumDriverConfiguration(final AppiumDriver appiumDriver) {
        registerContext(new AppiumDriverContext(appiumDriver));
        registerExtension(new AppiumDriverProviderExtension());
        registerExtension(new DefaultMethodExtension());
        registerExtension(new AppiumFindByExtension());
        registerExtension(new ToStringMethodExtension());
        registerExtension(new LongPressExtension());
        registerExtension(new SwipeDownOnExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new ToStringMethodExtension());
        registerExtension(new SwipeUpOnExtension());
        registerContext(new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class)));
    }
}
//CHECKSTYLE:ON: ClassDataAbstractionCoupling
