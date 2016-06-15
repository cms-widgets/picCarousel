/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.wedget.picBanner;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author CJ
 */
public class WidgetInfo implements Widget{

    /*
     * 图片轮播控件必须要有一个图片数组数据["1.png","2.png"]
     */
    public static final String validPicArray = "picArray";

    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String validStyleTemplate = "styleTemplate";

    @Override
    public String groupId() {
        return "com.huotu.wedget.picBanner";
    }

    @Override
    public String widgetId() {
        return "picBanner";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "图片轮播";
        }
        return "Picture carousel";
    }

    @Override
    public String description() {
        return "这是一个图片轮播，你可以对组件进行自定义修改。";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "这是一个图片轮播，你可以对组件进行自定义修改。";
        }
        return "This is a picture carousel,  you can make custom change the component.";
    }

    @Override
    public int dependBuild() {
        return 0;
    }

    @Override
    public Map<String, Resource> publicResources() {
        return null;
    }

    @Override
    public Resource widgetJs() {
        return new ClassPathResource("/js/picBanner.js", getClass().getClassLoader());
    }

    @Override
    public WidgetStyle[] styles() {

        WidgetStyle widgetStyle = new WidgetStyle() {

            @Override
            public String id() {
                return widgetId()+"1";
            }

            @Override
            public String name() {
                return "bootstrap 风格";
            }

            @Override
            public String name(Locale locale) {
                if (locale.equals(Locale.CHINESE)) {
                    return "bootstrap 风格";
                }
                return "bootstrap style";
            }

            @Override
            public String description() {
                return "基于bootstrap样式的图片轮播";
            }

            @Override
            public String description(Locale locale) {
                if (locale.equals(Locale.CHINESE)) {
                    return "基于bootstrap样式的图片轮播";
                }
                return "Based on the bootstrap style by picture";
            }

            @Override
            public Resource thumbnail() {
                return new ClassPathResource(MessageFormat.format("/thumbnail/{0}Style.png", id()), getClass().getClassLoader());
            }

            @Override
            public Resource previewTemplate() {
                return new ClassPathResource(MessageFormat.format("/template/{0}PreviewTemplate.html", id()), getClass().getClassLoader());
            }

            @Override
            public Resource browseTemplate() {
                return new ClassPathResource(MessageFormat.format("/template/{0}BrowseTemplate.html", id()), getClass().getClassLoader());
            }
        };
        return new WidgetStyle[]{widgetStyle};
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle[] widgetStyles = styles();
        boolean flag = false;
        if (widgetStyles == null || widgetStyles.length < 1) {
            throw new IllegalArgumentException();
        }
        for (WidgetStyle ws : widgetStyles) {
            if ((flag = ws.id().equals(styleId))) {
                break;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException();
        }
        String[] picArr = (String[]) componentProperties.get(validPicArray);
        String template = (String) componentProperties.get(validStyleTemplate);
        if (picArr == null || template == null || picArr.length > 0 ||  !"html".equals(template)) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Class springConfigClass() {
        return null;
    }


}