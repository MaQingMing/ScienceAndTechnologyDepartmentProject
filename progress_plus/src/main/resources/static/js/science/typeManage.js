var savaBtn = true;
let v = new Vue({
    el: "#wrapper",
    data: {
        //表单的自定义校检规则
        var: checkTrname = (rule, value, callback) => {
            if (!value) {
                callback(new Error('类别不能为空'))
            }else {
                const regCN = /^[\u4e00-\u9fa5]+|[a-zA-Z][A-Za-z\s]*[a-zA-Z]*|[a-zA-Z]+$/;
                if (!regCN.test(value)) {
                    callback(new Error('类别只能为中英文(英文用空格分开)'))
                } else {
                    callback();
                }
            }
        },
        var: checkAccording = (rule, value, callback) => {
            if (!value) {
                callback(new Error('计分依据不能为空'))
            }else {
                /*const regCN = /^[\u4e00-\u9fa5]+|[a-zA-Z][A-Za-z\s]*[a-zA-Z]*|[a-zA-Z]+$/;
                if (!regCN.test(value)) {
                    callback(new Error('计分依据只能为中英文(英文用空格分开)'))
                } else {
                    callback();
                }*/
                callback();
            }
        },
        // 添加的属性
        type:{
            trname: '',
            remarks: '',
            status: '1',
            according: ''
        },
        //表单校验规则的绑定
        typeRule: {
            trname: [
                {validator: checkTrname, trigger: 'blur'}
            ],
            according: [
                {validator: checkAccording, trigger: 'blur'}
            ]
        },
        user: {},
        // 页面加载效果
        loading: false,
        // 表格数据
        tableData: [],
        total: 0,
        // 编辑表格
        editDialog: false,
        searchedTrname: null,
        lastQuery: null,
        // 响应结果信息
        msg: {
            code : '',
            msg : ''
        }
    },
    created() {
        this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {};
        this.query();
    },
    methods: {
        // 查询科技项目类别
        query(){
            axios.get("../../techResultsType/get/" + this.searchedTrname).then(res => {
                this.msg.code = res.data.code;
                this.msg.msg = res.data.msg;
                this.lastQuery = this.searchedTrname;
                if (res.data.code == 1){
                    this.tableData = res.data.data;
                    this.searchedTrname = null;
                }
            })
        },
        // 打开添加对话框时初始化表单
        add(){
            this.editDialog = true;
            savaBtn=true;
            this.type = {
                trname: '',
                remarks: '',
                status: '1',
                according: ''
            }
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        loadTable() {
            if (this.searchedTrname == ''){
                this.searchedTrname = null;
            }
            // 防止重复点击
            if (this.lastQuery != this.searchedTrname){
                this.query();
                setTimeout(tmp=>{
                    if (this.msg.code == 1){
                        this.$message({message: this.msg.msg, type:"success", duration: 1000});
                    }else {
                        this.$message({message: this.msg.msg, type:"error", duration: 1000});
                    }
                }, 60)

            }
        },
        // 添加或修改
        save(formName) {
            let _this = this;
            _this.$refs[formName].validate((valid) => {//校检表单
                if (valid) {
                    if (savaBtn){
                        savaBtn=false
                        let method = _this.type.trid ? "update" : "add";
                        axios.post("../../techResultsType/" + method, _this.type).then(res => {
                            if (res.data.code == 1){
                                _this.query()
                                _this.$message({message: res.data.msg, type:"success"});
                            }else {
                                _this.$message({message: res.data.msg, type:"error"});
                            }
                            _this.editDialog = false;
                        })
                    }
                }
            })
        },
        // 修改
        edit(obj) {
            this.editDialog = true;
            savaBtn = true;
            let editObj = {
                trid : obj.trid,
                trname : obj.trname,
                remarks : obj.remarks,
                status : obj.status,
                according: obj.according
            }
            this.type = editObj;
        },
        // 删除
        del(row) {
            axios.post("../../techResultsType/status/" + row.trid + "/" + 0).then(res =>{
                if (res.data.code == 1){
                    row.status = 0;
                    this.$message({message: res.data.msg, type:"success"});
                }else {
                    this.$message({message: res.data.msg, type:"error"});
                }
            })
        },
        // 修改状态
        modifyStatus(row){
            axios.post("../../techResultsType/status/" + row.trid + "/" + parseInt(row.status)).then(res => {
                if (res.data.code == 1){
                    this.$message({message: res.data.msg, type:"success"});
                }else {
                    this.$message({message: res.data.msg, type:"error"});
                }
            })
        }
    }
})