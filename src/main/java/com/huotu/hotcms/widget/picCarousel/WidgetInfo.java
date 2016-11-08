/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.picCarousel;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryItem;
import com.huotu.hotcms.service.model.GalleryItemModel;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.service.CategoryService;
import com.huotu.hotcms.service.service.ContentService;
import com.huotu.hotcms.service.service.GalleryItemService;
import com.huotu.hotcms.service.service.GalleryService;
import com.huotu.hotcms.widget.*;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author CJ
 */
public class WidgetInfo implements Widget, PreProcessWidget {
    /*
     * 图片轮播控件必须要有一个图片数组数据["1.png","2.png"]
     */
    private static final String VALID_SERIAL = "serial";
    private static final String VALID_DATA_LIST = "dataList";
    private static final String VALID_COUNT = "count";


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
    public String dependVersion() {
        return "1.1.0";
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
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
        WidgetStyle style = WidgetStyle.styleByID(this, styleId);
        String serial = (String) componentProperties.get(VALID_SERIAL);
        if (serial == null || serial.equals("")) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        // 随意找一个数据源,如果没有。那就没有。。
        GalleryRepository galleryRepository = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(GalleryRepository.class);
        List<Gallery> galleryList = galleryRepository.findByCategory_Site(CMSContext.RequestContext().getSite());
        if (galleryList.isEmpty()) {
            Gallery gallery = initGallery(initCategory());
            initGalleryItem(gallery, resourceService);
            properties.put(VALID_SERIAL, gallery.getSerial());
        } else {
            properties.put(VALID_SERIAL, galleryList.get(0).getSerial());
        }
        properties.put(VALID_COUNT, 5);
        return properties;
    }

    @Override
    public void prepareContext(WidgetStyle style, ComponentProperties properties, Map<String, Object> variables
            , Map<String, String> parameters) {
        String serial = (String) properties.get(VALID_SERIAL);
        CMSDataSourceService cmsDataSourceService = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(CMSDataSourceService.class);
        int count = NumberUtils.parseNumber(properties.get(VALID_COUNT).toString(), Integer.class);
        List<GalleryItemModel> picImg = cmsDataSourceService.findGalleryItems(serial, count);
        variables.put(VALID_DATA_LIST, picImg);
    }


    /**
     * 初始化数据源
     *
     * @return
     */
    private Category initCategory() {
        CategoryService categoryService = getCMSServiceFromCMSContext(CategoryService.class);
        CategoryRepository categoryRepository = getCMSServiceFromCMSContext(CategoryRepository.class);
        Category category = new Category();
        category.setContentType(ContentType.Gallery);
        category.setName("默认数据源");
        categoryService.init(category);
        category.setSite(CMSContext.RequestContext().getSite());

        //保存到数据库
        categoryRepository.save(category);
        return category;
    }

    /**
     * 初始化一个图库
     *
     * @return
     */
    private Gallery initGallery(Category category) {
        GalleryService galleryService = getCMSServiceFromCMSContext(GalleryService.class);
        ContentService contentService = getCMSServiceFromCMSContext(ContentService.class);
        Gallery gallery = new Gallery();
        gallery.setTitle("默认图库标题");
        gallery.setDescription("这是一个默认图库");
        gallery.setCategory(category);
        contentService.init(gallery);
        galleryService.saveGallery(gallery);
        return gallery;
    }

    /**
     * 初始化一个图片
     *
     * @param gallery
     * @param resourceService
     * @return
     */
    private GalleryItem initGalleryItem(Gallery gallery, ResourceService resourceService) throws IOException {
        ContentService contentService = getCMSServiceFromCMSContext(ContentService.class);
        GalleryItemService galleryItemService = getCMSServiceFromCMSContext(GalleryItemService.class);
        GalleryItem galleryItem = new GalleryItem();
        galleryItem.setTitle("默认图片标题");
        galleryItem.setDescription("这是一个默认图片");
        ClassPathResource classPathResource = new ClassPathResource("img/picImg.png", getClass().getClassLoader());
        InputStream inputStream = classPathResource.getInputStream();
        String imgPath = "_resources/" + UUID.randomUUID().toString() + ".png";
        resourceService.uploadResource(imgPath, inputStream);
        galleryItem.setThumbUri(imgPath);
        galleryItem.setSize("xxx");
        galleryItem.setGallery(gallery);
        contentService.init(galleryItem);
        galleryItemService.saveGalleryItem(galleryItem);
        return galleryItem;
    }

}