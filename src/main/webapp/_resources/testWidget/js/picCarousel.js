var picCarousel = {
    properties: null,
    saveComponent: function () {
        if( this.properties['maxImgUrl'].length==4 && this.properties['minImgUrl'].length==4 ){
            return this.properties;
        }else{
            layer.msg("组件数据缺少,未能保存,请完善。");
            return null;
        }
    },
    uploadImage: function () {
        uploadForm( {
            ui: '#maxImg',
            inputName: 'file',
            maxWidth: 1920,
            maxHeight: 540,
            isCongruent: false,
            maxFileCount: 4,
            successCallback: function(files, data, xhr, pd) {
                this.properties['maxImgUrl'].push(data.fileUri);
            },
            deleteCallback: function (resp, data, jqXHR) {
                console.log(data);
                $.grep(this.properties['maxImgUrl'], function(cur,index){
                    if(cur==data.fileUri){
                        this.properties['maxImgUrl'].splice(index,1);
                    }
                });
            }
        });

        uploadForm({
            ui: '#minImg',
            inputName: 'file',
            maxWidth: 1200,
            maxHeight: 540,
            isCongruent: false,
            maxFileCount: 4,
            successCallback: function(files, data, xhr, pd) {
                this.properties['minImgUrl'].push(data.fileUri);
            },
            deleteCallback: function (resp, data, jqXHR) {
                console.log(data);
                $.grep(this.properties['minImgUrl'], function(cur,index){
                    if(cur==data.fileUri){
                        this.properties['minImgUrl'].splice(index,1);
                    }
                });
            }
        });
    },
    initProperties: function () {
        this.properties['maxImgUrl'] = [];
        this.properties['minImgUrl'] = [];
    },
    init: function (globalId) {
        this.properties= widgetProperties(globalId);
        this.initProperties();
        this.uploadImage();
    }
};
