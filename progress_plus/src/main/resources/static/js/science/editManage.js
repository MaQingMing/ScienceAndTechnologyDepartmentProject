var savaBtn = true;
let v = new Vue({
    el: "#wrapper",
    data: {
        publicHeight: document.documentElement.clientHeight - 115,
        //表单的自定义校检规则
        var: checkLeid = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择级别'));
            } else {
                callback()
            }
        },
        var: checkScore = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入科技分(数字)'));
            } else {
                callback()
            }
        },
        var: checkStage = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择专利阶段'));
            } else {
                callback()
            }
        },
        var: checkPosit = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择认定部门'));
            } else {
                callback()
            }
        },
        var: checkType = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择项目类型'));
            } else {
                callback()
            }
        },
        var: checkFoundScore = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入立项科技分(数字)'));
            } else {
                callback()
            }
        },
        var: checkCheckScore = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入验收科技分(数字)'));
            } else {
                callback()
            }
        },
        var: checkMaxScore = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入最高分'));
            } else {
                callback()
            }
        },
        selectedType: 1,   // 选中的类型
        searchedContext: '',   // 输入的负责人姓名
        searchedProject: '', // 输入的项目名称
        editedType: null, // 编辑选中的属性
        // editedLevel: null,  // 编辑选中的级别
        selected: null,    //选中编辑的类型
        editDialog: false,
        // 页面加载效果
        loading: false,
        tableData: [{
            reamrks: ''
        }],
        pageNum: 1,
        pageSize: 10,
        total: 0,
        dialogFormVisible: false,
        search: '',
        //表单校验规则的绑定
        projectRule: {
            leid: [
                {validator: checkLeid, trigger: 'blur'}
            ],
            score: [
                {validator: checkScore, trigger: 'blur'}
            ],
            stage: [
                {validator: checkStage, trigger: 'blur'}
            ],
            posit: [
                {validator: checkPosit, trigger: 'blur'}
            ],
            foundScore: [
                {validator: checkFoundScore, trigger: 'blur'}
            ],
            checkScore: [
                {validator: checkCheckScore, trigger: 'blur'}
            ],
            type: [
                {validator: checkType, trigger: 'blur'}
            ],
            maxScore: [
                {validator: checkMaxScore, trigger: 'blur'}
            ]
        },
        // 类别种类
        trnames: [{trid: 1, trname: "纵向科研"}],
        // 编辑的项目
        editedProject: {},
        // 认定部门
        sureDept: '',
        // 发送请求的控制器
        goalPath: 'directionStandard',
        // 与类型相对应的项目级别
        levels: [],
        // 部门集合
        depts: [],
        // 修改的项目id
        editId: null,
        // 自科|社科
        types: [{tname: '自然科学',}, {tname: '社会科学'}],
        // 专利阶段
        stages: [{sname: '申请阶段'}, {sname: '获得授权'}, {sname: '专利转化'}],
        // 返回结果
        msg: {},
        // 防止重复查询
        lastQuery: {},
        // 搜索条件被选中的项目级别
        selectedLeid: null,
        selectedTrname: "纵向科研",
        // 修改时被选中的部门
        selectedDeptId: null
    },
    created() {
        this.getTrname();
        this.getDept();
        this.query();
        this.getLname(this.selectedType);
        console.info(document.documentElement.clientHeight);
    },
    methods: {
        query() {
            axios.get("../../" + this.goalPath + "/get/" + this.pageNum + "/" + this.pageSize, {
                params: {
                    leid: this.selectedLeid,
                    context: this.searchedContext
                }
            }).then(res => {
                this.msg.code = res.data.code;
                this.msg.msg = res.data.msg;
                // 防止重复查询
                this.lastQuery.pageNum = this.pageNum;
                this.lastQuery.pageSize = this.pageSize;
                this.lastQuery.searchedContext = this.searchedContext;
                this.lastQuery.selectedLeid = this.selectedLeid;
                this.lastQuery.selectedType = this.selectedType;
                if (res.data.code == 1) {
                    this.tableData = res.data.data.data;
                    this.total = res.data.data.total;
                    console.log(this.tableData)
                    this.tableData.forEach(item => {
                        item.status += "";
                    })
                } else {
                    this.tableData = []
                }
                this.editDialog = false;
            })
        },
        // 打开添加对话框时初始化表单
        add() {
            this.editedProject = {
                status: '1',
                cash: '1'
            }
            this.editDialog = true;
            savaBtn = true;
            this.editedType = null
        },
        // 获取类型的种类
        getTrname() {
            axios.get("../../techResultsType/get/trname").then(res => {
                if (res.data.code == 1) {
                    this.trnames = res.data.data;
                } else {
                    this.$message({message: res.data.msg, showClose: true});
                }
            })
        },
        // 编辑时选择的类型
        typeChange(value) {
            // trid
            this.selected = value;
            // trname
            this.editedType = this.trnames[value - 1].trname;
            this.editedProject = {
                status: '1',
                cash: '1'
            }
            this.getLname(this.selected)
            // 不同类型，不同添加路径
            this.modifyGoalPath(this.editedType);
        },
        // 根据类型id查询项目级别
        getLname(trid) {
            axios.get("../../techResultsLevel/get/" + trid).then(res => {
                if (res.data.code == 1) {
                    this.levels = res.data.data;
                } else {
                    this.levels = []
                    this.$message({message: res.data.msg, showClose: true});
                }
            })
        },
        // 查询部门
        getDept() {
            axios.post("../../labeldept/showNoScience").then(res => {
                if (res.data.code == 1) {
                    this.depts = res.data.data;
                } else {
                    this.$message({message: res.data.msg, type: "error", showClose: true})
                }
            })
        },
        loadTable() {
            // 防止重复点击
            if (this.lastQuery.pageSize != this.pageSize ||
                this.lastQuery.pageNum != this.pageNum ||
                this.lastQuery.searchedContext != this.searchedContext ||
                this.lastQuery.selectedLeid != this.selectedLeid ||
                this.lastQuery.selectedType != this.selectedType) {
                if (this.lastQuery.pageNum == this.pageNum) {
                    this.pageNum = 1;
                }
                // 级别更新
                this.getLname(this.selectedType)
                this.query();
                setTimeout(tmp => {
                    if (this.msg.code == 1) {
                        this.$message({message: this.msg.msg, type: "success", duration: 1000});
                        this.$nextTick(() => {
                            this.$refs.table.doLayout(); // 解决表格错位
                        });
                    } else {
                        this.$message({message: this.msg.msg, duration: 1000});
                    }
                }, 80)

            }
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.getLname(this.selectedType);
            this.selected = null;
        },
        // 修改单次查询条数
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.loadTable();
        },
        // 修改页码
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.loadTable();
        },
        // 添加或修改
        save(formName) {
            this.$refs[formName].validate((valid) => {//校检表单
                if (valid) {
                    if (savaBtn) {
                        savaBtn = false
                        this.editedProject.trid = this.selected;
                        // 如果不为字符串就不用转换为id
                        if (typeof (this.editedProject.posit) == 'string'){
                            this.editedProject.posit = this.selectedDeptId;
                        }
                        let method = this.editedProject.id ? "update" : "add";
                        axios.post("../../" + this.goalPath + "/" + method, this.editedProject).then(res => {
                            if (res.data.code == 1) {
                                // 如果是添加，添加完后跳到添加类型的标签页
                                if (method == "add") {
                                    this.selectedType = this.selected;
                                }
                                this.editedType = null;
                                this.query();
                                this.selectedTrname = this.trnames[this.selected - 1].trname;
                                this.$forceUpdate();
                                this.$message({message: res.data.msg, type: "success", showClose: true})
                            } else {
                                this.$message({message: res.data.msg, type: "error", showClose: true})
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
            // 让编辑的类型为当前标签选中的类型名
            this.selected = obj.trid;
            this.editedType = this.trnames[this.selectedType - 1].trname;
            // 获取级别
            let editItem = {
                id: obj.id,
                type: obj.type ? obj.type : null,
                leid: obj.leid,
                score: obj.score ? obj.score : null,
                checkScore: obj.checkScore ? obj.checkScore : null,
                foundScore: obj.foundScore ? obj.foundScore : null,
                declareScore: obj.declareScore ? obj.declareScore : null,
                context: obj.context ? obj.context : null,
                stage: obj.stage ? obj.stage : null,
                remarks: obj.remarks,
                status: obj.status,
                cash: obj.cash,
                posit: obj.posit,
                maxScore: obj.maxScore ? obj.maxScore : null
            }
            this.getLname(this.selectedType)
            this.editedProject = editItem;
            this.selectedDeptId = this.editedProject.posit;
            this.editedProject.posit = this.depts[this.depts.length - this.editedProject.posit].tname
        },
        // 根据修改路径也随之修改
        modifyGoalPath(trname) {
            // 不同类型，不同路径
            if (trname == '纵向科研') {
                this.goalPath = 'directionStandard';
            } else if (trname == '横向科研') {
                this.goalPath = 'transverseStandard';
            } else if (trname == '科技成果') {
                this.goalPath = 'achievementStandard';
            } else if (trname == '学术论文') {
                this.goalPath = 'paperStandard';
            } else if (trname == '学术专著') {
                this.goalPath = 'bookStandard';
            } else if (trname == '发明专利') {
                this.goalPath = 'inventStandard';
            } else if (trname == '科研平台') {
                this.goalPath = 'scientificStandard';
            } else if (trname == '科技荣誉') {
                this.goalPath = 'honorStandard';
            } else{
                this.goalPath = ''
                this.$message({message: "数据异常, 请联系管理员", type: 'error', showClose: true})
            }
        },
        // 修改标签页
        async handleClick(value) {
            this.selectedLeid = null;
            this.searchedContext = null
            // 修改标签页，页码归1
            if (this.selectedTrname != value.label){
                this.lastQuery.pageNum = 0;
                this.pageNum = 1;
                this.selectedTrname = this.trnames[this.selectedType - 1].trname;
            }
            // 级别更新
            this.getLname(this.selectedType)
            await this.modifyGoalPath(this.selectedTrname);
            this.loadTable();
        },
        // 修改状态
        modifyStatus(row) {
            axios.post("../../" + this.goalPath + "/project/" + row.id + "/" + parseInt(row.status)).then(res => {
                if (res.data.code == 1) {
                    this.$message({message: res.data.msg, type: "success", showClose: true});
                } else {
                    this.$message({message: res.data.msg, type: "error", showClose: true});
                }
            })
        },
        // 修改是否可以换现
        modifyCash(row){
            axios.post("../../" + this.goalPath + "/cash/" + row.id + "/" + parseInt(row.cash)).then(res => {
                if (res.data.code == 1){
                    this.$message({message: res.data.msg, type:"success", showClose: true});
                }else {
                    this.$message({message: res.data.msg, type:"error", showClose: true});
                }
            })
        },handleTitleLength(str){
            if(str.length > 20){
                return str.substr(0, 20)+"...";
            }else{
                return str;
            }
        }
    }
})