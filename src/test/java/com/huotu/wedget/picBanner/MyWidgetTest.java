package com.huotu.wedget.picBanner;

import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.WebElement;

import java.util.Map;
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
    protected void editorWork(WebElement editor, Supplier<Map<String,Object>> currentWidgetProperties){
        editor.click();

        String alert = driver.switchTo().alert().getText();
        System.out.println(alert);

        Map<String,Object> ps = currentWidgetProperties.get();

        assertThat(currentWidgetProperties.get().get("count"))
                .isEqualTo(1);
    }
}
