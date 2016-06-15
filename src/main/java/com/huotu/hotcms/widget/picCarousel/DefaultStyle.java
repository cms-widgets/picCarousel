package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 轮播控件的默认样式
 * Created by lhx on 2016/6/15.
 */
public class DefaultStyle implements WidgetStyle {
    @Override
    public String id() {
        return "picCarousel1";
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
}
