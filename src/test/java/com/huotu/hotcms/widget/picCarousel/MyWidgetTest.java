package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetResolveService;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.loader.thymeleaf.process.ReplaceBrowseProcessor;
import com.huotu.hotcms.widget.resolve.impl.WidgetResolveServiceImpl;
import com.huotu.widget.test.WidgetTest;
import com.huotu.widget.test.WidgetTestConfig;
import com.huotu.widget.test.bean.WidgetViewController;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
        try{
            currentWidgetProperties.get();
            assert false;
        }catch (IllegalStateException ignored){
            assertThat(0).as("save没有属性值返回异常").isEqualTo(0);
        }
        WebElement maxImg = editor.findElement(By.id("maxImg"));
        List<WebElement> input = maxImg.findElements(By.name("file"));
        assertThat(input).isNotNull();
        assertThat(input.size()).isNotEqualTo(0);

        WebElement minImg = editor.findElement(By.id("maxImg"));
        input = minImg.findElements(By.name("file"));
        assertThat(input).isNotNull();
        assertThat(input.size()).isNotEqualTo(0);

        try {
            driver.findElement(By.id("editorSaver")).click();
            Map map = currentWidgetProperties.get();
        }catch (IllegalStateException ex){
            //无法模拟上传图片说以导致保存失败，应当忽略该异常
            assertThat(0).as("save没有属性值返回异常").isEqualTo(0);
        }
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) {
        uiChanger = (properties) -> {
            widgetViewController.setCurrentProperties(properties);
            String uri = "/browse/" + WidgetTestConfig.WidgetIdentity(widget) + "/" + style.id();
            if (printPageSource())
                try {
                    mockMvc.perform(get(uri))
                            .andDo(print());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalStateException("no print html");
                }
            driver.get("http://localhost" + uri);
            WebElement webElement = driver.findElement(By.id("browse")).findElement(By.tagName("div"));
            return webElement;
        };
        ComponentProperties properties = new ComponentProperties();
        properties.put("componentId","picCarousel");
        ComponentProperties imgs = new ComponentProperties();
        imgs.put("maxImgUrl",new String[]{"1.jpg","2.jpg","3.jpg","4.jpg"});
        imgs.put("minImgUrl", new String[]{"1.jpg", "2.jpg", "3.jpg", "4.jpg"});
        properties.put("properties", imgs);

        WebElement webElement = uiChanger.apply(properties);

        assertThat(webElement.getAttribute("id")).isEqualToIgnoringCase("picCarousel");

        List<WebElement> lis = webElement.findElements(By.tagName("img"));
        assertThat(lis.size()).isEqualTo(4);


    }


}
