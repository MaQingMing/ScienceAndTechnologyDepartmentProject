//获取数据库查出来的文件类型
function getFileType(filePath) {
    // 获取文件的扩展名
    var fileExtension = filePath.split('.').pop().toLowerCase();
    if (fileExtension === 'jpg' || fileExtension === 'png' || fileExtension === 'gif') {
        return 'picture';
    } else if (fileExtension === 'pdf') {
        return 'PDF';
    } else if (fileExtension === 'doc' || fileExtension === 'docx') {
        return 'Word';
    } else {
        return '未知类型'; // 可以根据需要返回其他类型
    }
}

let v = new Vue({
    el: "#app",
    data() {
        return {
            publicHeight: document.documentElement.clientHeight - 131,
            //备案名称
            name: '',
            //团队人员
            teamPeople: [],
            //要查询的备案名
            nameQuery: '',
            //要查询的人员
            usernameQuery: '',
            //备案类型
            values:'',
            options: [{
                value: '', // 选项中的值对应的属性名
                label: '', // 选项中显示的文本对应的属性名
            }],
            tableKey:0,
            //备案名称
            input: "",
            pageNum: 1,
            pageSize: 5,
            total: 0,
            form: [],
            //编辑查出来的数据
            FormData: {
                name: "",
                standardId: '',
                teamId: [],
                description: "",
                filePath: '',
                trid:'',
                leid:''
            },
            //查看自己申请的备案
            MytableData: [],
            //上传文件的类型
            fileData: [],
            //弹出层
            dialogVisible: false,
            //编辑弹出层
            dialogVisible_upadte: false,
            //备案详细
            textarea: '',
            options_pro: [
            ],
            name: '',
            trid: 0,
            leid: 0,
            //级联选择器的显示
            shows: [],
            //单选按钮的值
            radio: 0,
            //文件上传
            fileList_pro: [],
            //项目类型
            valuesLname:'',
            //编辑中的文件
            fileList: [
                {
                    name: '',
                    url: '',
                }
            ],
            fileListFilePath: [
                {
                    name: '',
                    url: ''
                }
            ],
            //文件上传后所返回来的地址
            file_final: [],
            //上传文件地址，拼接的
            fileFinal: '',
            //上传文件名
            fileName: [],
            //上传文件名，拼接
            fileFinalName: '',
            //编辑中上传文件名的数组
            fileNameUpdate: [],
            //返回过来的文件地址
            file_final_Update: [],
            //编辑中上传文件的地址，拼接的
            fileFinalUpdate: '',
            //编辑上传文件名，拼接
            fileFinalNameUpdate: '',
            dialogVisibleQuery: false,
            //第一级节点
            node1: [],
            //第二级点
            node2: [],
            //第三级别
            node3: [],
            clickedNodeId: null,
            total1: 0,
            total2: 0,
            total0: 0,
            options_1:[],
        }
    },
    methods: {
        // 当选择器的值发生变化时触发的方法
        handleChange(value) {
            this.trid=this.values
            this.queryLnameByTrid();
        },
        //查找小类型
        queryLnameByTrid(){
            var params = new URLSearchParams();
            params.append("trid",this.values)
            axios.post("../record/queryLnameByTrid",params).then(res=>{
                this.options_1=res.data.data.map(item=>({
                    value:item.leid,
                    label:item.lname
                }))
            })
        },
        //切换标题栏
        handleClick(tab, event) {
            this.pageNum = 1;
            this.tableKey+=1;
            this.queryApplication();
        },
        handleChangeLnameUpdate(){
            this.leid =this.FormData.leid
        },
        handleChangeUpadte(){
            this.trid=this.FormData.trid
            this.FormData.leid=''
            var params = new URLSearchParams();
            params.append("trid",this.trid)
            axios.post("../record/queryLnameByTrid",params).then(res=>{
                this.options_1=res.data.data.map(item=>({
                    value:item.leid,
                    label:item.lname
                }))
            })
        },
        //撤回打开的
        open(index, id) {
            this.$confirm('此操作将删除所提交的备案, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRecord(index, id)
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
            });
        },
        handleChangeLname(){
            this.leid=this.valuesLname;
        },
        //文件下载
        downloadFile(url, name) {
            // 创建一个隐藏的链接并触发下载
            const link = document.createElement("a");
            link.href = "../uploadfile/" + url;
            link.target = "_blank";
            link.download = name; // 设置下载的文件名
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        },
        //文件上传方法
        handleChangeFile(file, fileList) {
            const allowedExtensions = ['jpg', 'png', 'pdf', 'doc', 'docx'];
            const fileExtension = file.name.split('.').pop();
            if (file.size / 1024 / 1024 < 5 && allowedExtensions.includes(fileExtension.toLowerCase())) {
                this.fileList_pro.push(file);
            } else {
                if (file.size / 1024 / 1024 >= 5) {
                    this.$message('超出文件大小,请重新上传!');
                } else {
                    this.$message('不支持的文件类型');
                }
                this.handleRemove(file);
            }
        },
        handleChangeFileUpdate(file, fileList) {
            const allowedExtensions = ['jpg', 'png', 'pdf', 'doc', 'docx'];
            const fileExtension = file.name.split('.').pop();
            if (file.size / 1024 / 1024 < 5 && allowedExtensions.includes(fileExtension.toLowerCase())) {
                this.fileList.push(file);
            } else {
                if (file.size / 1024 / 1024 >= 5) {
                    this.$message('超出文件大小,请重新上传!');
                } else {
                    this.$message('不支持的文件类型');
                }
                this.handleRemoveUpdate(file);
            }
        },
        //关闭弹出层
        closeDialog() {
            this.dialogVisible = false
            this.fileFinal = ''
            this.file_Final = []
            this.teamPeople = []
            this.name = ''
            this.values = ''
            this.fileFinalName = ''
            this.fileName = []
            this.fileList_pro = []
        },
        //点击x号关闭弹出层
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    this.dialogVisible = false
                    this.fileFinal = ''
                    this.file_Final = []
                    this.teamPeople = []
                    this.name = ''
                    this.values = ''
                    this.fileFinalName = ''
                    this.fileName = []
                    this.fileList_pro = []
                    done();
                })
                .catch(_ => {
                });
        },
        //上传信息
        uploadfile() {
            let numbers = this.teamPeople.map(item => {
                // 使用正则表达式提取字符串中的数字部分
                let number = item.match(/[a-zA-Z0-9]+/);
                return number ? number[0] : ''; // 提取数字，若无数字则返回空字符串
            });
            let PeopleNumbers = numbers.filter(item => item !== '').join(',');
            if (this.name == '') {
                this.$message("请输入备案名称!")
                return;
            }
            if (this.values == ''){
                this.$message("请输入成果类型!")
                return;
            }
            if (this.valuesLname == ''){
                this.$message("请输入成果级别!")
                return;
            }
            if (this.teamPeople.length==0) {
                this.$message("请选择团队人员!")
                return;
            }
            if (this.fileList_pro.length == 0) {
                this.$message("请上传文件");
                return;
            }
            var date = new Date();
            var fullYear = date.getFullYear();
            var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 使用padStart来添加0
            var day = date.getDate().toString().padStart(2, '0'); // 使用padStart来添加0
            var nowadays = fullYear + '-' + month + '-' + day;
            var item = sessionStorage.getItem("user");
            var parse = JSON.parse(item);
            var username = parse.user.username;
            var urlSearchParams = new FormData();
            urlSearchParams.append("trid", this.trid)
            urlSearchParams.append("leid", this.leid)
            urlSearchParams.append("textarea", this.textarea)
            urlSearchParams.append("teamPeople", PeopleNumbers)
            urlSearchParams.append("name", this.name)
            urlSearchParams.append("values", this.values)
            urlSearchParams.append("date", nowadays)
            urlSearchParams.append("username", username);
            this.fileList_pro.forEach(file => {
                urlSearchParams.append("file", file.raw)
            })
            axios.post("../record/addRecord/upload", urlSearchParams, {
                headers: {
                    "Content-Type": "multipart/form-data" // 设置请求头
                }
            }).then(rs => {
                if (rs.data.code == 1) {
                    this.$message.success("提交备案成功");
                    this.queryApplication()
                    this.queryIdByUsername();
                } else {
                    this.$message.error("提交失败,请稍后重试!")
                }
            })
            this.fileFinal = ''
            this.file_Final = []
            this.teamPeople = []
            this.name = ''
            this.values = ''
            this.fileFinalName = ''
            this.fileName = []
            this.fileList_pro = []
            this.textarea = ''
            //关闭弹出层
            this.dialogVisible = false;
        },
        //点击查询触发的时间
        selectRecord() {
            this.pageNum = 1;
            this.queryApplication();
            this.queryIdByUsername();
        },
        imgDownload(file){
            this.downloadFile(file.url, file.name);
        },
        //查询提交备案
        queryApplication() {
            var item = sessionStorage.getItem("user");
            var parse = JSON.parse(item);
            var username = parse.user.username;
            var params = new URLSearchParams();
            params.append("name", this.nameQuery)
            params.append("username", username);
            params.append("radio", this.radio)
            params.append("pageSize", this.pageSize)
            params.append("pageNum", this.pageNum)
            this.fileData = {
                images: [],
                pdfs: [],
                words: []
            };
            axios.post("../record/queryRecord", params).then(res => {
                if (res.data.data == null) {
                    this.MytableData = []
                    this.total = 0
                } else {
                    this.MytableData = res.data.data.map(item => {
                        var filePaths = item.filePath.split(','); // 通过逗号分割文件路径
                        var images = [];
                        var pdf = [];
                        var word = [];
                        for (let i = 0; i < filePaths.length; i++) {
                            filePaths[i] = "../" + filePaths[i];
                            var fileType = getFileType(filePaths[i]); // 假设getFileType是一个返回文件类型的函数
                            if (fileType === 'picture') {
                                images.push(filePaths[i]);
                            } else if (fileType === 'PDF') {
                                pdf.push(filePaths[i]);
                            } else if (fileType === 'Word') {
                                word.push(filePaths[i]);
                            }
                        }
                        return {
                            id:item.id,
                            date: item.date,
                            description: item.description,
                            fileName: item.fileName,
                            name: item.name,
                            status: item.status,
                            teamId: item.teamId,
                            rejection: item.rejection,
                            standardId: item.standardId,
                            images: images,
                            pdf: pdf,
                            word: word,
                            teamName:item.teamNames,
                            total:item.total,
                            trname:item.trname,
                            lname:item.lname
                        };
                    });
                    if (res.data.data.length == 0) {
                        this.total = 0
                    } else {
                        this.total = res.data.data[0].total
                    }
                }
            })
        },
        //监听select值发生变化的方法
        filterMethod(query, option) {
            this.options_pro = [
                // 其他选项
            ];
            let data = []
            if (query.length >= 1) {
                axios.get("../record/query_user", {
                    params: {
                        username: query
                    }
                }).then(res => {
                    data = res.data.data
                    this.options_pro = data.map(item => ({
                        label: item.nickname + ' ' + item.username,
                        value: item.username
                    }));
                })
            }
        },
        filterMethod_update(query, option) {
            this.options_pro = [
                // 其他选项
            ];
            let data = []
            if (query.length >= 1) {
                axios.get("../record/query_user", {
                    params: {
                        username: query
                    }
                }).then(res => {
                    data = res.data.data
                    this.options_pro = data.map(item => ({
                        label: item.nickname + ' ' + item.username,
                        value: item.nickname + item.username
                    }));
                })
            }
        },
        //不满足选择逻辑处理
        handleRemove(file, fileList) {
            this.fileList_pro = this.fileList_pro.filter((f) => file.name !== f.name)
            // 强制刷新组件以更新文件列表
            this.$forceUpdate();
        },
        //编辑中不满足选择逻辑处理
        handleRemoveUpdate(file, fileList) {
            this.fileList = this.fileList.filter((f) => file.name !== f.name)
            // 强制刷新组件以更新文件列表
            this.$forceUpdate();
        },
        //点击文件名时发生的方法
        handlePreview(file) {
            if (file.url) {
                // 如果文件的 URL 存在，则在新标签页中打开文件预览
                window.open("../uploadfile/" + file.url, '_blank');
            } else {
                // 如果文件的 URL 不存在，可以执行下载操作
                this.downloadFile(file.url, file.name);
            }
        },
        //下载文件
        downloadFile(url, fileName) {
            // 创建一个隐藏的链接并触发下载
            const link = document.createElement("a");
            link.href = "../uploadfile/" + url; // 替换成实际的下载链接
            link.target = "_blank";
            link.download = fileName; // 设置下载的文件名
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        },
        //文件超出方法
        handleExceed(files, fileList) {
            this.$message.error('最多只能上传 10 个文件');
        },
        //文件移除
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        handleEdit(index, row) {
            this.dialogVisible_upadte = true;
        },
        //点击撤回按钮
        deleteRecord(index, id) {
            var params = new URLSearchParams();
            params.append("id", id);
            axios.post("../record/deleteRecord", params).then(res => {
                if (res.data.code == 1) {
                    this.queryApplication();
                    this.queryIdByUsername();
                } else {
                    this.$message("请稍后重试");
                }
            })
        },
        handleDelete(index, row) {
        },
        //分页变化
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.queryApplication();
        },
        //分页变化
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.queryApplication();
        },
        //编辑备案
        renameRecord(index, id) {
            this.dialogVisible_upadte = true;
            var params = new URLSearchParams();
            params.append("id", id)
            axios.post("../record/queryRecordById", params).then(res => {
                this.FormData.id = res.data.data.id
                this.FormData.name = res.data.data.name;
                this.FormData.standardId = res.data.data.standardId;
                this.FormData.description = res.data.data.description;
                this.FormData.trid=res.data.data.standardId
                this.options_1=res.data.data.lnamesAndLeid.map(item=>({
                    value:item.leid,
                    label:item.lname
                }))
                this.FormData.leid=res.data.data.standardType
                this.FormData.teamNames = [];
                this.FormData.teamId.push(res.data.data.teamId)
                const teamNamesFromBack = res.data.data.teamNames;
                var names = teamNamesFromBack.map(item => item.nickname + item.username);
                this.FormData.teamNames.push(...names);
                var filePaths = res.data.data.filePath.split(",");
                var fileNames = res.data.data.fileName.split(",");
                // 清空 fileList 数组
                this.fileList = [];
                if (filePaths.length === 1) {
                    // 单个文件的情况
                    this.fileList.push({
                        name: res.data.data.fileName, // 文件名
                        url: res.data.data.filePath.trim(), // 文件的URL或路径
                    });
                } else {
                    // 多个文件的情况
                    for (let i = 0; i < filePaths.length; i++) {
                        this.fileList.push({
                            name: fileNames[i], // 文件名
                            url: filePaths[i].trim(), // 文件的URL或路径
                        });
                    }
                }
            });
        },
        //打开新增弹窗
        saveRecordDialg(){
            var item = sessionStorage.getItem("user");
            var parse = JSON.parse(item);
            var username = parse.user.username;
            var nickname = parse.user.nickname
            this.teamPeople.push(nickname+username)
            this.dialogVisible =true;
        },
        //编辑备案中的确定
        async UpdateRecord(id) {
            if (this.FormData.name == '') {
                this.$message("请输入备案名称");
                return;
            }
            if(this.FormData.trid==''){
                this.$message("请选择成果类型");
                return;
            }
            if(this.FormData.leid==''){
                this.$message("请选择成果级别");
                return;
            }
            if (this.FormData.teamNames.length == 0) {
                this.$message("请填写团队人员");
                return;
            }
            if (this.fileList.length == 0) {
                this.$message("请上传证明材料");
                return;
            }
            // 存放需要上传的文件对象
            const filesToUpload = [];
            // 存放返回过来的数据
            const otherFiles = [];
            for (let i = 0; i < this.fileList.length; i++) {
                if (this.fileList[i].raw) {
                    // 具有 raw 属性的文件对象是用来上传的
                    filesToUpload.push(this.fileList[i]);
                } else {
                    // 没有 raw 属性的文件对象放在另一个数组中
                    otherFiles.push(this.fileList[i]);
                }
            }
            for (let i = 0; i < otherFiles.length; i++) {
                this.file_final_Update.push(otherFiles[i].url);
                this.fileNameUpdate.push(otherFiles[i].name)
            }
            //处理团队人员
            const phoneNumberRegex = /\d+/g;
            var teamNumbers = []
            for (let i = 0; i < this.FormData.teamNames.length; i++) {
                var match = this.FormData.teamNames[i].match(phoneNumberRegex);
                //截取数字，就是账号
                const phoneNumber = match[match.length - 1];
                teamNumbers.push(phoneNumber)
            }
            const updateTeamPeople = teamNumbers.join(",");
            var date = new Date();
            var fullYear = date.getFullYear();
            var month = (date.getMonth() + 1).toString().padStart(2, '0');
            var day = date.getDate().toString().padStart(2, '0');
            var nowadays = fullYear + '-' + month + '-' + day;
            var params = new FormData();
            params.append("id", id);
            params.append("name", this.FormData.name)
            params.append("standardId", this.FormData.standardId)
            //上传的是账号    21020440137,21020440336
            params.append("teamPeople", updateTeamPeople)
            params.append("filePath", this.file_final_Update)
            params.append("textarea", this.FormData.description)
            params.append("date", nowadays)
            params.append("trid", this.trid)
            params.append("leid", this.leid)
            params.append("filename", this.fileNameUpdate),
                filesToUpload.forEach(file => {
                    params.append("file", file.raw)
                });
            await axios.post("../record/updateRecordUpdate", params, {
                headers: {
                    "Content-Type": "multipart/form-data" // 设置请求头
                }
            }).then(res => {
                if (res.data.code == 1) {
                    this.$message.success("备案修改成功");
                    //关闭弹出层
                    this.dialogVisible_upadte = false;
                    this.queryApplication()
                    this.queryIdByUsername();
                } else {
                    this.$message.error("提交失败,请稍后重试!")
                }

            })
        },
        //查看附件
        queryFilePath(index, id) {
            this.dialogVisibleQuery = true;
            var params = new URLSearchParams();
            params.append("id", id);
            axios.post("../record/queryFilepath", params).then(res => {
                var filePaths = res.data.data.file_path.split(",");
                var fileNames = res.data.data.file_name.split(",");
                // 清空 fileList 数组
                this.fileListFilePath = [];
                if (filePaths.length === 1) {
                    // 单个文件的情况
                    this.fileListFilePath.push({
                        name: res.data.data.file_name, // 文件名
                        url: '../' + res.data.data.file_path.trim(), // 文件的URL或路径
                    });
                } else {
                    // 多个文件的情况
                    for (let i = 0; i < filePaths.length; i++) {
                        this.fileListFilePath.push({
                            name: fileNames[i], // 文件名
                            url: '../' + filePaths[i].trim(), // 文件的URL或路径
                        });
                    }
                }
            })
        },
        //修改的文件上传
        async uploadUpdate() {
            this.file_final_Update = [];
            if (this.FormData.name == '') {
                this.$message("请输入备案名称");
                return;
            }
            if (this.FormData.standardId == '') {
                this.$message("请选择备案类型");
                return;
            }
            if (this.FormData.teamId.length == 0) {
                this.$message("请填写团队人员");
                return;
            }
            if (this.fileList.length == 0) {
                this.$message("请上传证明材料");
                return;
            }
            // 存放需要上传的文件对象
            const filesToUpload = [];
            // 存放返回过来的数据
            const otherFiles = [];
            for (let i = 0; i < this.fileList.length; i++) {
                if (this.fileList[i].raw) {
                    // 具有 raw 属性的文件对象是用来上传的
                    filesToUpload.push(this.fileList[i]);
                } else {
                    // 没有 raw 属性的文件对象放在另一个数组中
                    otherFiles.push(this.fileList[i]);
                }
            }
            for (let i = 0; i < filesToUpload.length; i++) {
                var params = new FormData();
                params.append("file", filesToUpload[i].raw)
                this.fileNameUpdate.push(filesToUpload[i].raw.name)
                await axios.post("../record/addRecord/upload", params, {
                    headers: {
                        "Content-Type": "multipart/form-data" // 设置请求头
                    }
                }).then(res => {
                    this.file_final_Update.push(res.data.data)
                    this.fileFinalUpdate = this.file_final_Update.join(",");
                    this.fileFinalNameUpdate = this.fileNameUpdate.join(",")
                })
            }
            for (let i = 0; i < otherFiles.length; i++) {
                this.file_final_Update.push(otherFiles[i].url);
                this.fileNameUpdate.push(otherFiles[i].name)
            }
        },
        //查类型
        queryKind() {
            axios.post("../record/queryKind").then(res => {
                this.options=res.data.data.map(item=>({
                    value:item.trid,
                    label:item.trname
                }))
            });

        },
        loadRecord(index, tableData) {
            this.$confirm('是否下载该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var params = new URLSearchParams();
                var data = [];
                if (tableData.images.length != 0) {
                    data.push(tableData.images)
                }
                if (tableData.pdf.length != 0) {
                    data.push(tableData.pdf)
                }
                if (tableData.word.length != 0) {
                    data.push(tableData.word)
                }
                params.append("filePath", data)
                params.append("filename", tableData.name)
                params.append("fileName", tableData.fileName)
                axios.post("../record/uploadZip", params, {responseType: 'blob'}).then(res => {
                    let data = res.data;
                    let headers = res.headers;
                    if (!data) {
                        return
                    }
                    let url = window.URL.createObjectURL(new Blob([data], {
                        type: headers['content-type']
                    }));
                    let link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url
                    const fileName = headers['content-disposition'];
                    let title = fileName.includes('fileName=') ? decodeURI(fileName.split('=')[1]) : '压缩文件.zip';
                    link.setAttribute('download', title);
                    document.body.appendChild(link)
                    link.click()
                    this.$message.success('数据导出成功');
                }, function (error) {
                    this.$message.error('数据导出错误');
                })
            })
        },
        queryIdByUsername() {
            var item = sessionStorage.getItem("user");
            var parse = JSON.parse(item);
            var username = parse.user.username;
            var params = new URLSearchParams();
            params.append("name", this.nameQuery)
            params.append("username", username);
            axios.post("../record/queryIdByUsername", params).then(res => {
                var totalBytag = res.data.data.map(item => ({
                    total: item.total,
                    status: item.status
                }))
                const status0Obj = totalBytag.find(item => item.status === 0);
                this.total0 = status0Obj ? status0Obj.total : 0;
                const status1Obj = totalBytag.find(item => item.status === 1);
                this.total1 = status1Obj ? status1Obj.total : 0;
                const status2Obj = totalBytag.find(item => item.status === 2);
                this.total2 = status2Obj ? status2Obj.total : 0;
            })
        }
    },
    created() {
        //查询备案
        this.queryApplication();
        this.queryKind();
        this.queryIdByUsername();
    },
})