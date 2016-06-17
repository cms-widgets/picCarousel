var picBanner = {
    widgetId: null,
    styleItemClick: function () {
        $(".picCarouselItem").on("click", function () {
            //todo 获取样式id的预览视图
            var styleId = $(this).attr("data-styleId");
        });
    },
    uploadImage: function () {
        var properties = widgetProperties(this);
        properties['maxImgUrl'] = [];
        properties['minImgUrl'] = [];

        $(".imgfile").on("change", function () {
            $.ajaxFileUpload({
                url: "/cms/resourceUpload",
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: $(this).attr('id'), //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                type: 'post',
                data: {
                    ownerId: properties.ownerId //todo 待获取
                },
                success: function (json)  //服务器成功响应处理函数
                {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                var className = "maxDeleteImg";
                                if ($(this).attr("id") == 'picCarouselFile1') {
                                    properties['maxImgUrl'].push({"fileUri": json.data.fileUri});
                                } else {
                                    properties['minImgUrl'].push({"fileUri": json.data.fileUri});
                                    className = "minDeleteImg";
                                }
                                $(this).parent().parent().children("ul").append(
                                    '<li class="item">'
                                    + '<div style="position:relative;">'
                                    + '<img src="' + json.data.fileUri + '" />'
                                    + '<div>'
                                    + '<p style="height:20px; width:100%;background-color: rgba(100,100,100,0.5);text-align:center; '
                                    + 'color:#FFFFFF;position:absolute; bottom:0px;"'
                                    + ' class="' + className + '" >删除</p>'
                                    + '</div>'
                                    + '</div>'
                                    + '</li>');
                                layer.msg("操作成功", {time: 2000});
                                break;
                            case 403:
                                layer.msg("没有权限", {time: 2000});
                                break;
                            case 502:
                                layer.msg("服务器错误,请稍后再试", {time: 2000});
                                break;
                        }
                    }

                },
                error: function ()//服务器响应失败处理函数
                {
                    layer.msg("服务器错误,请稍后再试", {time: 2000});
                }
            });
            // todo 更新属性
            updatePreperties(properties);
        });
    },
    deleteImg: function () {
        $(".imagesDiv").on("click",".maxDeleteImg", function () {
            var fileUri = $(this).parent().parent().children("img").attr("src");
            delImg(fileUri, 'maxImgUrl')
        });

        $(".imagesDiv").on("click",".minDeleteImg", function () {
            var fileUri = $(this).parent().parent().children("img").attr("src");
            delImg(fileUri, 'minImgUrl')
        });

        function delImg(uri, type) {
            var properties = widgetProperties(this);
            $.ajax({
                url: "cms/deleteResource",
                data: {
                    fileUri: uri, ownerId: properties.ownerId  //todo 待获取
                },
                type: "post",
                dataType: "json",
                success: function (json) {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                $.grep(properties[type], function (cur, index) {
                                    if (cur['fileUri'] == uri) {
                                        properties[type].splice(index, 1);
                                    }
                                });
                                $(this).parent().parent().parent().remove();
                                // todo 更新属性
                                updatePreperties(properties);
                                break;
                            case 403:
                                layer.msg("没有权限", {time: 2000});
                                break;
                            case 500:
                                layer.msg("服务器错误,请稍后再试", {time: 2000});
                                break;
                        }
                    }
                },
                error: function () {
                    layer.msg("操作错误", {time: 2000});
                }
            });
        }
    },
    setWidgetId: function (widgetId) {
        widgetId = widgetId;
    },
    init: function () {
        picBanner.styleItemClick();
        picBanner.uploadImage();
        picBanner.deleteImg();
    }
};
