//define(function (require, exports, module) {
    //var index = 0;
    //var widgetId;


    //exports.init = function () {
    //    banner.init();
    //};
    //exports.setWidgetId = function (id) {
    //    banner.setWidgetId(id);
    //}
//});

var banner = {
    index: 0,
    widgetId:null,
    styleItemClick: function () {
        $(".bannerItem").on("click", function () {
            //todo 获取样式id的预览视图
            var styleId = $(this).attr("data-styleId");
        });
    },
    uploadImage: function () {
        $(".imgfile").on("change", function () {
            var info = wdigetPreperties(this);
            $.ajaxFileUpload({
                url: "/cms/resourceUpload",
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: $(this).attr('id'), //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                enctype: "multipart/form-data",
                method: "post",
                data: {
                    ownerId: info.ownerId
                },
                success: function (json)  //服务器成功响应处理函数
                {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                if ($(this).attr("id") == 'file1') {
                                    info.maxUrls[index] = json.data.fileUri;
                                } else {
                                    info.minUrls[index] = json.data.fileUri;
                                }
                                $('#' + widgetId).data(info);
                                index++;
                                $(this).parent().parent().children("ul").append(-+-
                                        '<li class="item">' +
                                    '<div style="position:relative;">' +
                                    '<img src="' + json.data.fileUri + '" />' +
                                    '<div>' +
                                    '<p style="height:20px; width:100%;background-color: rgba(100,100,100,0.5); text-align:center; color:#FFFFFF;position:absolute; bottom:0px;"' +
                                    ' class="deleteImg" >删除</p>' +
                                    '</div>' +
                                    '</div>' +
                                    '</li>');
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
            updatePreperties(info);
        });
    },
    delUploadImage: function () {
        $(".deleteImg").on("click", function () {
            var fileUri = $(this).parent().parent().children("img").attr("src");
            var info = wdigetPreperties(this);
            $.ajax({
                url: "deleteResource",
                data: {fileUri: fileUri, ownerId: info.ownerId},
                type: "post",
                dataType: "json",
                success: function (json) {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                $(this).parent().parent().parent().remove();
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
                error: function (data) {
                    $.jBox.tip("error");
                }
            });
        });
    },
    setWidgetId:function(widgetId){
        widgetId = widgetId;
    },
    init: function () {
        banner.styleItemClick();
        banner.uploadImage();
        banner.delUploadImage();
    }
};