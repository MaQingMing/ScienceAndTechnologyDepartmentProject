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
let v =new Vue({
    el: "#app",
    data() {
        return {
            publicHeight: document.documentElement.clientHeight - 130,
            //备案名称
            name: "",
            //工号
            usernameQuery:'',
            //查询的值
            username:'',
            dialogVisibleQuery:false,
            //工号
            teamPeople:'',
            pageNum: 1,
            pageSize: 5,
            total: 0,
            //是否通过单选按钮
            radio: 0,
            //不同意的原因
            reason: '',
            options_pro: [],
            suggestions: [],     //下拉数据项
            tableData: [],
            activeName:0,
            //弹出层
            dialogVisible: false,
            dialogVisibleBack:false,
            //描述详细信息
            textarea: '',
            urls: [
            ],
            value1: '',
            multipleSelection: [],
            updateId:0,
            //更过时的文件
            fileList:[
                {
                    name:'',
                    url:'',
                }
            ],
            fileListFilePath: [
                {
                    name: '',
                    url: ''
                }
            ],
            total1:0,
            total2:0,
            total0:0,
        }
    },
    methods: {
        //切换标题栏
        handleClick(tab, event) {
            this.pageNum=1;
            this.queryApplication();
        },
        //通过处理
        handleEdit(index, id) {
            this.$confirm('此操作将通过该备案申请, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var params = new URLSearchParams();
                params.append("id",id)
                axios.post("../record/updateStatus",params).then(res=>{
                    if (res.data.code==1){
                        this.$message.success("通过成功")
                    }else {
                        this.$message("网络繁忙,请稍后重试");
                    }
                    this.queryApplication();
                    this.queryTotalBygoveruname();
                })
            }).catch(() => {
            });
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
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
        //  表格多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        opensaveRecord() {
            if(this.radio!=0){
                this.$message({
                    message: '需在待审核状态下使用!',
                    type: 'warning'
                });
                return;
            }
            if (this.multipleSelection.length==0){
                this.$message("请勾选要通过的备案!");
                return;
            }
            this.$confirm('此操作将通过多个备案申请, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.saveRecord();
            }).catch(() => {
            });
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
        saveRecord(){
            let id = this.multipleSelection.map(item => item.id);
            axios.post("../record/saveRecord",id,{
                headers: { 'Content-Type': 'application/json' }
            }).then(res=>{
                if (res.data.code==1){
                    this.$message.success("批量通过处理成功!");
                }else {
                    this.$message("修改失败,请稍后重试!");
                }
                this.queryApplication();
                this.queryTotalBygoveruname();
            })
        },
        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return (restaurant) => {
                return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },
        loadAll() {
            return [
                {"value": "很抱歉，我们发现您提交的材料中存在一些问题，需要进行修正后才能继续处理。"},
                {"value": "感谢您提交材料，但我们注意到其中可能存在一些不完整或不符合要求的部分，需要您进行修改后重新提交。"},
                {"value": "我们对您的提交非常感激，但经过初步审核发现有一些细节需要补充或调整，希望您能协助我们完成这些工作。"},
                {"value": "非常感谢您提供的材料，但我们发现其中的一些内容可能需要进一步确认或修改，以确保其符合我们的标准和要求。"},
                {"value": "我们非常感谢您的配合，但是您提交的材料似乎存在一些问题，需要您进行核对和修正后再次提交给我们。"},
            ];
        },
        handleSelect(item) {
            console.log(item);
        },
        selectBlur(e) {
            this.username = e.target.value;
            this.$forceUpdate(); // 强制更新
        },
        //监听select值发生变化的方法
        filterMethod(query, option){
            this.options_pro = [
                // 其他选项
            ];
            let data=[]
            if (query.length>=1){
                axios.get("../record/query_user",{
                    params:{
                        username:query
                    }
                }).then(res=>{
                    data=res.data.data
                    this.options_pro = data.map(item => ({
                        label: item.nickname + ' ' + item.username,
                        value: item.nickname
                    }));
                })
            }
        },
        selectRecord(){
            this.pageNum=1;
            this.queryApplication();
            this.queryTotalBygoveruname();
        },
        //查询提交备案
        queryApplication(){
            var params = new URLSearchParams();
            params.append("username",this.username)
            params.append("radio",this.radio)
            params.append("pageSize",this.pageSize)
            params.append("pageNum",this.pageNum)
            this.fileData = {
                images: [],
                pdfs: [],
                words: []
            };
            axios.post("../record/queryRecordByGovernuser",params).then(res=>{
                this.tableData = res.data.data.map(item => {
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
                if (res.data.data.length==0){
                    this.total=0;
                }else {
                    this.total=res.data.data[0].total
                }

            })
        },
        handleEditQuery(index,id){
            this.dialogVisibleQuery=true;
            var params = new URLSearchParams();
            params.append("id",id);
            axios.post("../record/queryFilepath",params).then(res=>{
                var filePaths = res.data.data.file_path.split(",");
                var fileNames = res.data.data.file_name.split(",");
                // 清空 fileList 数组
                this.fileList = [];
                if (filePaths.length === 1) {
                    // 单个文件的情况
                    this.fileList.push({
                        name: res.data.data.file_name, // 文件名
                        url:'../'+ res.data.data.file_path.trim(), // 文件的URL或路径
                    });
                } else {
                    // 多个文件的情况
                    for (let i = 0; i < filePaths.length; i++) {
                        this.fileList.push({
                            name: fileNames[i], // 文件名
                            url: '../'+filePaths[i].trim(), // 文件的URL或路径
                        });
                    }
                }
            })

        },
        //点击文件名时发生的方法
        handlePreview(file) {
            if (file.url) {
                // 如果文件的 URL 存在，则在新标签页中打开文件预览
                window.open("../"+file.url, '_blank');
            } else {
                // 如果文件的 URL 不存在，可以执行下载操作
                this.downloadFile(file.url,file.name);
            }
        },
        //下载文件
        downloadFile(url,fileName) {
            // 创建一个隐藏的链接并触发下载
            const link = document.createElement("a");
            link.href = "../"+url; // 替换成实际的下载链接
            link.target = "_blank";
            link.download = fileName; // 设置下载的文件名
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        },
        open(index,id){
            this.dialogVisibleBack=true
            this.updateId=id;
        },
        saveBack(){
            if (this.reason==''){
                this.$message("请填写审核不通过原因!")
                return;
            }
            var params = new URLSearchParams();
            params.append("id",this.updateId)
            params.append("reason",this.reason)
            axios.post("../record/updateBack",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("驳回成功!")
                }else {
                    this.$message("网络繁忙，请稍后重试");
                }
                this.reason='';
                this.dialogVisibleBack=false
                this.queryApplication();
                this.queryTotalBygoveruname();
            })
        },
        deleteBack(){
            this.reason='';
        },
        //查各状态的条数
        queryTotalBygoveruname(){
            var params = new URLSearchParams();
            params.append("username",this.username);
            axios.post("../record/queryTotalBygoveruname",params).then(res=>{
                var totalBytag=res.data.data.map(item=>({
                    total: item.total,
                    status:item.status
                }))
                const status0Obj = totalBytag.find(item => item.status === 0);
                this.total0= status0Obj ? status0Obj.total : 0;
                const status1Obj = totalBytag.find(item => item.status === 1);
                this.total1 = status1Obj ? status1Obj.total : 0;
                const status2Obj = totalBytag.find(item => item.status === 2);
                this.total2 = status2Obj ? status2Obj.total : 0;
            })
        },loadRecord(index, tableData) {
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
        }
    },
    created(){
        this.queryApplication();
        this.queryTotalBygoveruname();
    },
    mounted() {
        this.restaurants = this.loadAll();
    }
})