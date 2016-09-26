package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.Editor;
import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
public class MyWidgetTest extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return true;
    }

    @Override
    protected void editorWork(Widget widget, Editor editor, Supplier<Map<String, Object>> currentWidgetProperties) {

    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        List<WebElement> lis = webElement.findElements(By.tagName("img"));
        assertThat(lis.size()).isEqualTo(3);
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger
            , Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        WebElement webElement = uiChanger.apply(widget.defaultProperties(resourceService));
    }


}
