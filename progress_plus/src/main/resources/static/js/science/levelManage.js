var savaBtn = true;
let v = new Vue({
    el: "#wrapper",
    data: {
        //表单的自定义校检规则
        var: checkTrid = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择项目类型'))
            } else {
                callback();
            }
        },
        var: checkLname = (rule, value, callback) => {
            if (!value) {
                callback(new Error('级别名不能为空'))
            } else {
                const regCN = /^[\u4e00-\u9fa5]+|[a-zA-Z][A-Za-z\s]*[a-zA-Z]*|[a-zA-Z]+|\d*$/;
                if (!regCN.test(value)) {
                    callback(new Error('级别名只能为中英文(英文用空格分开)'))
                } else {
                    callback();
                }
            }
        },
        /*var: checkMoney = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入经费(比如:500万元)'))
            } else {
                callback();
            }
        },*/
        var: checkPart = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择经费区间'))
            } else {
                callback();
            }
        },
        // 添加的级别
        editedLevel: {
            lname: '',
            trid: '',
            remarks: '',
            status: '1',
            money: "", // 万元
            part: "", // 及以上，及以下，含
        },
        parts: [{
            part: "及以上"
        }, {
            part: "及以下"
        }, {
            part: "含"
        }],
        //表单校验规则的绑定
        levelRule: {
            lname: [
                {validator: checkLname, trigger: 'blur'}
            ],
            trid: [
                {validator: checkTrid, trigger: 'blur'}
            ],
            part: [
                {validator: checkPart, trigger: 'blur'}
            ]
        },
        // 搜索的类型
        searchedTypeIndex: null,
        // 添加级别的类型
        selectedType: null,
        trnames: [],
        // 页面加载效果
        loading: false,
        tableData: [],
        editDialog: false,
        searchedLname: null,
        editedType: '',
        // 分页参数
        pageNum: 1,
        pageSize: 10,
        total: 0,
        // 返回结果
        msg: {},
        // 防止重复查询
        lastQuery: {},
        error: "" // 错误信息
    },
    created() {
        this.getTrname();
        this.query()
    },
    methods: {
        // 校验经费
        checkMoney(){
            console.log(this.editedLevel.money)
            console.log(this.editedLevel.part)
            console.log(!this.editedLevel.money)
            if (!this.editedLevel.money) {
                this.error = "请输入经费(比如:500万元)";
            } else {
                if (!this.editedLevel.part) {
                    this.error = "请选择经费范围";
                } else if (this.editedLevel.part == "及以上" || this.editedLevel.part == "及以下") {
                    if (!this.editedLevel.money.match(/^(\d+)万元$/)) {
                        this.error = "请直接输入经费(比如:100万元)";
                    }else {
                        this.error = '';
                    }
                } else if (this.editedLevel.part == "含") {
                    if (!this.editedLevel.money.match(/^(\d+)万元-(\d+)万元$/)) {
                        this.error = "请输入区间(比如:100万元-500万元)";
                    }else {
                        let numbers = this.editedLevel.money.match(/\d+/g);
                        if (numbers.length == 2 && numbers[1] < numbers[0]){
                            this.error = "请输入区间(比如:100万元-500万元)";
                        }
                        this.error = '';
                    }
                }
            }
        },
        // 分页查询项目级别
        query() {
            // 防止下标为空
            let trid = '';
            if (this.searchedTypeIndex) {
                trid = this.trnames[this.searchedTypeIndex - 1].trid;
            }
            axios.get("../../techResultsLevel/get/" + this.pageNum + "/" + this.pageSize, {
                params: {
                    trid: trid,
                    lname: this.searchedLname
                }
            }).then(res => {
                this.msg.code = res.data.code;
                this.msg.msg = res.data.msg;
                // 防止重复查询
                this.lastQuery.searchedTypeIndex = this.searchedTypeIndex;
                this.lastQuery.searchedLname = this.searchedLname;
                this.lastQuery.pageNum = this.pageNum;
                this.lastQuery.pageSize = this.pageSize;
                if (res.data.code == 1) {
                    this.tableData = res.data.data.data;
                    this.total = res.data.data.total;
                } else {
                    this.tableData = []
                }
            })
        },
        // 打开添加对话框时初始化表单
        add() {
            this.editDialog = true;
            savaBtn = true;
            this.editedLevel = {
                lname: '',
                trid: '',
                remarks: '',
                status: '1'
            }
        },
        // 获取类型的种类
        getTrname() {
            axios.get("../../techResultsType/get/trname").then(res => {
                if (res.data.code == 1) {
                    this.trnames = res.data.data;
                } else {
                    this.$message({message: res.data.msg, type: "error", duration: 1000});
                }
            })
        },
        handleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
        loadTable() {
            if (this.searchedLname == '') {
                this.searchedLname = null;
            }
            // 防止重复点击
            if (this.lastQuery.searchedLname != this.searchedLname ||
                this.lastQuery.searchedTypeIndex != this.searchedTypeIndex ||
                this.lastQuery.pageSize != this.pageSize ||
                this.lastQuery.pageNum != this.pageNum) {
                if (this.lastQuery.pageNum == this.pageNum) {
                    this.pageNum = 1;
                }
                this.query();
                setTimeout(tmp => {
                    if (this.msg.code == 1) {
                        this.$message({message: this.msg.msg, type: "success", duration: 1000});
                    } else {
                        this.$message({message: this.msg.msg, type: "error", duration: 1000});
                    }
                }, 70)

            }
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.loadTable();
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.loadTable();
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        // 添加或修改
        save(formName) {
            if (this.editedType == "横向科研"){
                this.checkMoney();
                if (this.editedLevel.part == "含"){
                    this.editedLevel.lname = this.editedLevel.money + "(" + this.editedLevel.part + ")";
                }else {
                    this.editedLevel.lname = this.editedLevel.money + this.editedLevel.part;
                }
            }
            this.$refs[formName].validate((valid) => {//校检表单
                if (valid) {
                    if (this.error){
                        return;
                    }
                    if (savaBtn) {
                        savaBtn = false;
                        let method = this.editedLevel.leid ? "update" : "add";
                        axios.post("../../techResultsLevel/" + method, this.editedLevel).then(res => {
                            if (res.data.code == 1) {
                                this.query();
                                this.$message({message: res.data.msg, type: "success"})
                            } else {
                                this.$message({message: res.data.msg, type: "error"})
                            }
                            this.editDialog = false;
                        })
                    }
                }
            })
        },
        edit(obj) {
            this.editDialog = true;
            savaBtn = true;
            this.editedLevel = {
                leid: obj.leid,
                trid: obj.trid,
                lname: obj.lname,
                remarks: obj.remarks,
                status: obj.status
            };
            
        },
        del(row) {
            axios.post("../../techResultsLevel/status/" + row.leid + "/" + 0).then(res => {
                if (res.data.code == 1) {
                    row.status = 0;
                    this.$message({message: res.data.msg, type: "success"});
                } else {
                    this.$message({message: res.data.msg, type: "error"});
                }
            })
        },
        // 编辑时选择的类型
        typeChange(value) {
            this.editedType = this.trnames[value - 1].trname;
        },
        // 修改状态
        modifyStatus(row) {
            axios.post("../../techResultsLevel/status/" + row.leid + "/" + parseInt(row.status)).then(res => {
                if (res.data.code == 1) {
                    this.$message({message: res.data.msg, type: "success"});
                } else {
                    this.$message({message: res.data.msg, type: "error"});
                }
            })
        }
    }
})