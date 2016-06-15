package com.huotu.wedget.picBanner;

import com.huotu.widget.test.WidgetTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author CJ
 */
public class MyWidgetTest extends WidgetTest {

    /**
     * 编辑器测试
     */
    @Test
    public void editor() throws Exception {
        mockMvc.perform(get("/editor"))
                .andDo(print());
    }


}
