package io.qameta.atlas.webdriver;

import io.qameta.atlas.webdriver.extension.DriverProvider;
import io.qameta.atlas.webdriver.extension.Page;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Web Page.
 */
public interface WebPage extends WrapsDriver, SearchContext {

    @DriverProvider
    WebDriver getWrappedDriver();

    default void open(String url) {
        getWrappedDriver().get(url);
    }

    @Page
    <T extends WebPage> T onPage(Class<T> page);

}
