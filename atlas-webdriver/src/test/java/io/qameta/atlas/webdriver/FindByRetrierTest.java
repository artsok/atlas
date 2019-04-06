package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.core.internal.EmptyRetryer;
import io.qameta.atlas.webdriver.extension.FindBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.util.Collections;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebDriver;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.when;

public class FindByRetrierTest {

    @Test(expected = NotFoundException.class)
    public void retryChildFind() {
        WebElement parentOrigin = mockWebElement();
        WebElement childOrigin = mockWebElement();

        when(parentOrigin.findElement(By.xpath("//div"))).thenThrow(new NotFoundException());
        when(childOrigin.isDisplayed()).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);

        parent.child().isDisplayed();
    }



    @Test(expected = NotFoundException.class)
    public void shouldSetGlobalRetryThroughDefaultRetryer() {
        WebElement parentOrigin = mockWebElement();
        when(parentOrigin.isDisplayed()).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()))
                .context(new RetryerContext(
                        new DefaultRetryer(3000L, 1000L, Collections.singletonList(Throwable.class))));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);
        parent.isDisplayed();
    }

    @Test(expected = NotFoundException.class)
    public void shouldSetGlobalRetryThroughEmptyRetryer() {
        WebElement parentOrigin = mockWebElement();
        when(parentOrigin.isDisplayed()).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()))
                .context(new RetryerContext(new EmptyRetryer()));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);
        parent.isDisplayed();
    }

    interface ParentElement extends AtlasWebElement {
        @Retry(timeout = 8000)
        @FindBy("//div")
        NestedElement child();
    }

    interface NestedElement extends AtlasWebElement {
        boolean isDisplayed();
    }
}
