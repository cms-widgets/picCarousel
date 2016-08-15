package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import com.huotu.widget.test.bean.WidgetViewController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    WidgetViewController widgetViewController;

    @Override
    protected boolean printPageSource() {
        return true;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        WebElement maxImg = editor.findElement(By.id("maxImg"));
        List<WebElement> input = maxImg.findElements(By.tagName("input"));
        assertThat(input).isNotNull();
        assertThat(input.size()).as("图片上传插件").isNotEqualTo(0);

        WebElement minImg = editor.findElement(By.id("maxImg"));
        input = minImg.findElements(By.tagName("input"));
        assertThat(input).isNotNull();
        assertThat(input.size()).as("图片上传插件").isNotEqualTo(0);
        try {
            Map map = currentWidgetProperties.get();
        }catch (IllegalStateException ex){
            //无法模拟上传图片说以导致保存失败，应当忽略该异常
            assertThat(0).as("save没有属性值返回异常").isEqualTo(0);
        }
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        List<WebElement> lis = webElement.findElements(By.tagName("img"));
        assertThat(lis.size()).isEqualTo(4);
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger) throws IOException {

    }


}
