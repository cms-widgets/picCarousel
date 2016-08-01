/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.service.entity.support.WidgetIdentifier;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author CJ
 */
public class WidgetInfo implements Widget {
    /*
     * 图片轮播控件必须要有一个图片数组数据["1.png","2.png"]
     */
    public static final String VALID_MAX_PICS = "maxImgUrl";
    public static final String VALID_MIN_PICS = "minImgUrl";

    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";


    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.picCarousel";
    }

    @Override
    public String widgetId() {
        return "picCarousel";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "图片轮播";
        }
        return "Picture carousel";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
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
        Map<String, Resource> map = new HashMap<>();
        map.put("img/banner01.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/banner02.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/banner03.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/banner04.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/banner05.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/banner05.jpg", new ClassPathResource("img/banner01.jpg", getClass().getClassLoader()));
        map.put("img/sd03.png", new ClassPathResource("img/sd03.png", getClass().getClassLoader()));
        map.put("js/picCarousel.js", new ClassPathResource("js/picCarousel.js", getClass().getClassLoader()));
        map.put("thumbnail/picCarousel1Style.png", new ClassPathResource("thumbnail/picCarousel1Style.png"
                , getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.isCompatibleWith(Javascript)) {
            return new ClassPathResource("js/picCarousel.js", getClass().getClassLoader());
        }
        return null;
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultStyle()};
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
        String[] maxPicArr = (String[]) componentProperties.get(VALID_MAX_PICS);
        String[] minPicArr = (String[]) componentProperties.get(VALID_MIN_PICS);
        if (maxPicArr == null || maxPicArr.length != 4 || minPicArr == null || minPicArr.length != 4) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) {
        List<String> maxImages = new ArrayList<>();
        ComponentProperties properties = new ComponentProperties();
        try {
            WidgetIdentifier identifier = new WidgetIdentifier(groupId(), widgetId(), version());
            maxImages.add(resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/" + "img/banner01.jpg").httpUrl().toURI().toString());
            maxImages.add(resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/" + "img/banner02.jpg").httpUrl().toURI().toString());
            maxImages.add(resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/" + "img/banner03.jpg").httpUrl().toURI().toString());
            maxImages.add(resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/" + "img/banner04.jpg").httpUrl().toURI().toString());
            properties.put("maxImgUrl", maxImages);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}