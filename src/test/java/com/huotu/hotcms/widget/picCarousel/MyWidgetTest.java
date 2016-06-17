package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        return false;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        //上传两张大图
        List<WebElement> imgfile = editor.findElements(By.className("imgfile"));
        imgfile.get(0).click();
        imgfile.get(0).click();
        Map<String,Object> ps = currentWidgetProperties.get();
        List<Map<String,Object>> maxList = (List<Map<String, Object>>) ps.get("maxImgUrl");
        assertThat(maxList.get(0).get("fileUri")).isEqualTo("img/file.jpe");
        assertThat(maxList.get(1).get("fileUri")).isEqualTo("img/file.jpe");

        //上传两张小图
        imgfile.get(1).click();
        imgfile.get(1).click();
        ps = currentWidgetProperties.get();
        List<Map<String,Object>> minList = (List<Map<String, Object>>) ps.get("minImgUrl");
        assertThat(minList.get(0).get("fileUri")).isEqualTo("img/file.jpe");
        assertThat(minList.get(1).get("fileUri")).isEqualTo("img/file.jpe");


        //依次删除两张大图
        List<WebElement> maxDelImgList = editor.findElements(By.className("maxDeleteImg"));
        maxDelImgList.get(0).click();
        ps = currentWidgetProperties.get();
        maxList = (List<Map<String, Object>>) ps.get("maxImgUrl");
        assertThat(maxList.size()).isEqualTo(1);

        maxDelImgList.get(1).click();
        ps = currentWidgetProperties.get();
        maxList = (List<Map<String, Object>>) ps.get("maxImgUrl");
        assertThat(maxList.size()).isEqualTo(0);

        //依次删除两张小图图
        List<WebElement> minDelImgList = editor.findElements(By.className("minDeleteImg"));
        minDelImgList.get(0).click();
        ps = currentWidgetProperties.get();
        minList = (List<Map<String, Object>>) ps.get("minImgUrl");
        assertThat(minList.size()).isEqualTo(1);

        minDelImgList.get(1).click();
        ps = currentWidgetProperties.get();
        minList = (List<Map<String, Object>>) ps.get("minImgUrl");
        assertThat(minList.size()).isEqualTo(0);
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) {
    }

}
