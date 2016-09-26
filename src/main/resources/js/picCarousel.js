CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        saveComponent: function (onFailed) {

            this.properties.count = $(".count").val();
            if (this.properties.serial == null && this.properties.serial == '' ||
                this.properties.count == null || this.properties.count == '') {
                onFailed("组件显示条数应大于0，且必须选择一个图库。");
                return;
            }
        }
    }
});
