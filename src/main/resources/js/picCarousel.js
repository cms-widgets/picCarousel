CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            if (this.properties['maxImgUrl'].length == 4 && this.properties['minImgUrl'].length == 4) {
                onSuccess(this.properties);
                return this.properties;
            } else {
                onFailed("组件数据缺少,未能保存,请完善。");
                return false;
            }
        },
        uploadImage: function () {
            var me = this;
            uploadForm({
                ui: '#maxImg',
                inputName: 'file',
                maxWidth: 1920,
                maxHeight: 540,
                maxFileCount: 4,
                isCongruent: false,
                successCallback: function (files, data, xhr, pd) {
                    me.properties['maxImgUrl'].push(data.fileUri);
                },
                deleteCallback: function (resp, data, jqXHR) {
                    $.grep(that.properties['maxImgUrl'], function (cur, index) {
                        if (cur == data.fileUri) {
                            me.properties['maxImgUrl'].splice(index, 1);
                        }
                    });
                }
            });

            uploadForm({
                ui: '#minImg',
                inputName: 'file',
                maxWidth: 1200,
                maxHeight: 540,
                maxFileCount: 4,
                isCongruent: false,
                successCallback: function (files, data, xhr, pd) {
                    me.properties['minImgUrl'].push(data.fileUri);
                },
                deleteCallback: function (resp, data, jqXHR) {
                    $.grep(that.properties['minImgUrl'], function (cur, index) {
                        if (cur == data.fileUri) {
                            me.properties['minImgUrl'].splice(index, 1);
                        }
                    });
                }
            });
        },
        initProperties: function () {
            this.properties['maxImgUrl'] = [];
            this.properties['minImgUrl'] = [];
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.uploadImage();
        },
        close: function (globalId) {
            $('#maxImg').siblings().remove();
            $('#minImg').siblings().remove();
        }
    }
});
