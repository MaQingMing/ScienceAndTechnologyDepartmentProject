Vue.component("yc-upload", {
    props: ["isMultiple", "accessType", "actionUrl", "accessSize", "uploadData", "templateUrl", "buttonName"],
    template: `
      <div style="display: inline-block;">
          <el-button type="primary" size="small" @click="showDialog(true)">{{button_name[0]}}</el-button>
          <div class="mosh" style="width: 100%;height: 100%;position: absolute;top: 0;left: 0;background-color: rgba(0,0,0,0.5);z-index: 4;" @click="showDialog(false)" v-show="dialogVisit"></div>
          <div class="upload-box" style="width: 680px;min-height: 400px;max-height: 500px;box-sizing: border-box;border-radius: 3px;position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);background-color: #fff;z-index: 10;" v-show="dialogVisit">
            <div class="upload-box-title" style="width: 100%;height: 60px;border-bottom: 1px solid #ccc;font-size: 20px;display: flex;justify-content: space-between;">
              <div class="title-content" style="margin: 16px;">{{button_name[1]}}</div>
              <div class="title-icon" style="margin: 16px;"><i style="cursor: pointer;" class="el-icon-close" @click="showDialog(false)"></i></div>
            </div>
            <div class="upload-box-content" style=" margin: 10px 20px;">
              <el-alert
                  style="background-color: #EAF0FC;margin-bottom: 16px;"
                  title=""
                  type="info"
                  :closable="alertClose"
                  show-icon>
                <template slot="title">
                  只允许选择这些文件类型：
                  <el-tag style="margin: 0 3px;" type="warning" v-for="(item,index) in types" :key="index">{{item}}</el-tag>
                  。 单个文件不超过：
                  <el-tag style="margin: 0 3px;" type="danger">{{accessSize/1024/1024}}M</el-tag>
                  <el-button icon="el-icon-download" @click="downloadTemplate"
                  style="margin-left: 10px;" size="small" type="warning">下载模版</el-button>
                </template>
              </el-alert>
              <el-upload
                  class="upload-demo"
                  style="min-height: 200px;max-height: 300px;overflow-y: auto;overflow-x: hidden;margin: 0 20%;"
                  drag
                  with-credentials
                  ref="upload"
                  show-file-list
                  :data="uploadData"
                  :before-upload="beforeUpload"
                  :on-success="uploadSuccess"
                  :on-error="uploadError"
                  :file-list="fileList"
                  :accept="accessType"
                  :action="actionUrl"
                  :auto-upload="autoUpload"
                  :multiple="isMultiple">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              </el-upload>
            </div>
            <el-button style="float: right;margin-right: 20px;margin-bottom: 10px;" size="small" type="primary" @click="submitUpload">确 定</el-button>
            <el-button style="float: right;margin-right: 20px;margin-bottom: 10px;" size="small" @click="showDialog(false)" >取 消</el-button>
          </div>
          
      </div>
    `,
    data() {
        return {
            dialogVisit: false,
            alertClose: false,
            autoUpload: false,
            fileList: [],
            types: this.accessType.split(","),
            button_name: this.buttonName.split(",")
        }
    },
    methods: {
        showDialog(value) {
            this.dialogVisit = value;
            if (!value) {
                this.fileList = [];
            }
        },
        submitUpload() {
            this.$refs.upload.submit();
            this.$message.info("文件上传中");
        },
        uploadError(err) {
            this.$message.error("文件上传失败，请联系管理员");
        },
        uploadSuccess(response, file, filelist) {
            if (response.code == 1) {
                this.$message.success("文件上传成功");
            } else if (response.code == 99) {
                //下载错误信息的文件
                var a = document.createElement('a');
                a.setAttribute('href', "../gainApply/downloadError");
                document.body.appendChild(a);
                a.click();
                this.$message({showClose: true, duration: 0, message: response.msg, type: "error"});
            } else if (response.code == 0){
                // 文件上传失败，显示失败原因
                this.$message({showClose: true, duration: 0, message: response.msg, type: "error"});
            }

            this.showDialog(false);
        },
        beforeUpload(file) {
            const fileSuffix = file.name.substring(file.name.lastIndexOf("."));
            const fileSize = file.size;
            const whiteList = this.accessType.split(",");
            whiteList.forEach(item => {
                item = item.trim();         //去掉空格，防止这样写：  .zip, .ppt
            });

            if (whiteList.indexOf(fileSuffix) === -1) {
                this.$message.error("上传文件只能是：" + this.accessType);
                return false;
            }
            if (fileSize > this.accessSize) {
                this.$message.error("上传文件大小不能超过：" + this.accessSize / 1024 / 1024 + "M");
                return false;
            }
        }, downloadTemplate() {
            //下载模版文件
            if(typeof (this.templateUrl) !="undefined" && this.templateUrl!=null){
                var templateArray = this.templateUrl.split(",");
                let params = new URLSearchParams();
                params.append("prefix",templateArray[0]);
                params.append("fileName",templateArray[1]);
                const config={
                    responseType: 'blob' //这个一定要设置，否则会出现文件下载后打不开的情况
                };
                this.$message({showClose: true, message: '正在准备下载,请稍等!', duration: 2000, type: 'success'});
                axios.post('../monitor/server/downLoadTemplate', params, config).then(res =>{
                    let blob = new Blob([res.data], {
                        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; application/octet-stream',
                    });

                    const temp = templateArray[0]+"-"+res.headers["content-disposition"].split(";")[1].split("=")[1];
                    let fileName = decodeURIComponent(temp);
                    let objectUrl = URL.createObjectURL(blob);// 创建下载的链接
                    let a = document.createElement("a");
                    a.href = objectUrl;
                    a.download = fileName;
                    a.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));
                    window.URL.revokeObjectURL(blob);
                });
            }
        }
    }
});

/*
*
<yc-upload :upload-data="null" :is-multiple="true" :access-size="1024*1024*8" template-url=""
button-name="批量添加,批量添加系统用户"
access-type=".zip,.ppt" action-url="https://jsonplaceholder.typicode.com/posts/"></yc-upload>
:is-multiple   true多文件  false单文件
access-type  允许上传的文件类型 字符串
action-url  上传文件的地址 字符串
:access-size  单个文件上传大小限制 数值
:upload-data 请求的额外携带参数，对象
后端，文件对应的键是 file
*
* */