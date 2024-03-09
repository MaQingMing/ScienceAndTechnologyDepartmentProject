let arApp= new Vue({
    el: "#arApp",
    data: {
        publicHeight: document.documentElement.clientHeight - 115,
        dialogVisibleArTitle:"添加科技成果附则",
        dialogVisibleAr:false,//添加/修改框
        searchInput:'',//搜索框
        tableData:[],//表格数据
        pageNum: 1,
        pageSize: 10,
        total: 0,
        updateStatus:false, //修改状态
        selected:'',//选中的 科技成果类型
        trnames:[],//科技成果类型
        lnames:[],//科技项目
        activeName:'0',// el-tabs 选项
        activeNameupdate:'0',
        updateID:0,      //要修改的Id
        additionalData:[{context1:'',score:undefined,context2:''},
            {context1:'',score:undefined,context2:''},
            {context1:'',childid:'',score:undefined,context2:''},{context1:''}],
    }, methods: {
        getTrname() {
            // 获取类型的种类
            axios.get("../../techResultsType/get/trname").then(res => {
                if (res.data.code == 1) {
                    this.trnames = res.data.data;
                } else {
                    this.$message({message: res.data.msg, showClose: true});
                }
            })
        },number_change(e){
            //输入分值和百分比
            let _this = this;
            _this.additionalData[_this.activeName].score = e.target.value;
        },
        select(){
            this.pageNum=1;
            this.queryRoles();
        },
        //查询细则
        queryRoles(){
            var params = new URLSearchParams();
            params.append("currentPage",this.pageNum)
            params.append("currentSize",this.pageSize)
            params.append("context",this.searchInput)
            axios.post("../../additional_rules/queryRoles",params).then(res=>{
                this.tableData=res.data.data
            })
            this.queryRolesTotal();
        },
        save(){
            if (this.selected==''){
                this.$message("请选择科技成果类型");
                return;
            }
            if (this.activeName==0){
                this.saveBy0();
            }else if (this.activeName==1){
                this.saveBy1();
            }else if (this.activeName==2){
                this.saveBy2();
            }else {
                this.saveBy3();
            }
        },
        saveBy0(){
            if (this.additionalData[0].context1==''){
                this.$message("请输入必要的内容");
                return;
            }
            if (this.additionalData[0].score==undefined){
                this.$message("请输入必要的分值");
                return;
            }
            var params = new URLSearchParams();
            params.append("context1",this.additionalData[0].context1+"#1"+this.additionalData[0].context2)
            params.append("score",this.additionalData[0].score)
            params.append("selected",this.selected)
            params.append("updateStatus",this.updateStatus)
            params.append("id",this.updateID)
            params.append("type",this.activeName)
            axios.post("../../additional_rules/addRules",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("操作成功");
                    this.dialogVisibleAr = false
                    this.queryRoles();
                    this.queryRolesTotal();
                }
            })
        },
        //分页变化
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.queryRoles();
        },
        //分页变化
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.queryRoles();
        },
        queryRolesTotal(){
            var params = new URLSearchParams();
            params.append("context",this.searchInput)
            axios.post("../../additional_rules/queryRolesTotal",params).then(res=>{
                this.total=res.data.data
            })
        },
        hahah(id){
            this.$confirm('此操作将永久删除该细则, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var params = new URLSearchParams();
                params.append("id",id)
                axios.post("../../additional_rules/deleteById",params).then(res=>{
                    if (res.data.code==1){
                        this.$message.success("删除成功!");
                        this.queryRoles();
                    }else {
                        this.$message("出错了，请稍后重试!");
                    }
                })
            }).catch(() => {
            });
        },
        //点击修改
        update(row){
            this.updateStatus=true;
            this.updateID=row.rid;
            this.dialogVisibleAr=true;
            this.activeName=row.type+"";
            this.selected=row.trtid
            if (row.type==0){
                var split = row.content1.split("#1");
                this.additionalData[row.type].context1=split[0]
                this.additionalData[row.type].score=row.score
                this.additionalData[row.type].context2=split[1]
            }else if (row.type==1){
                var split = row.content1.split("#2");
                this.additionalData[row.type].context1=split[0]
                this.additionalData[row.type].score=(row.ratio).replace("%",'')
                this.additionalData[row.type].context2=split[1]
            }else if (row.type==2){
                var split = row.content1.split("#3#2");
                this.changeType();
                this.additionalData[row.type].context1=split[0]
                this.additionalData[row.type].score=(row.ratio).replace("%",'');
                this.additionalData[row.type].childid=row.lname
                this.additionalData[row.type].context2=split[1]
            }else if (row.type==3){
                this.additionalData[row.type].context1=row.content
            }
            this.$forceUpdate();
        },
        updateValue(){
            this.updateStatus=false;
            this.additionalData=[{context1:'',score:undefined,context2:''},
                {context1:'',score:undefined,context2:''},
                {context1:'',childid:'',score:undefined,context2:''},{context1:''}];
            this.selected='';
        },
        saveBy1(){
            if (this.additionalData[1].context1==''){
                this.$message("请输入必要的内容");
                return;
            }
            if (this.additionalData[1].score==undefined){
                this.$message("请输入必要的分值");
                return;
            }
            var params = new URLSearchParams();
            params.append("context1",this.additionalData[1].context1+"#2"+this.additionalData[1].context2)
            params.append("score",this.additionalData[1].score)
            params.append("selected",this.selected)
            params.append("updateStatus",this.updateStatus)
            params.append("id",this.updateID)
            params.append("type",this.activeName)
            axios.post("../../additional_rules/addRulesRatio",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("操作成功");
                    this.dialogVisibleAr = false
                    this.queryRoles();
                    this.queryRolesTotal();
                }
            })
        },
        saveBy2(){
            if (this.additionalData[2].context1==''){
                this.$message("请输入必要的内容");
                return;
            }
            if (this.additionalData[2].childid==''){
                this.$message("请选择科技项目")
                return;
            }
            if (this.additionalData[2].score==undefined){
                this.$message("请输入必要的分值");
                return;
            }
            var params = new URLSearchParams();
            params.append("context1",this.additionalData[2].context1+"#3"+"#2"+this.additionalData[2].context2)
            params.append("score",this.additionalData[2].score)
            params.append("selected",this.selected)
            params.append("leid",this.additionalData[2].childid)
            params.append("updateStatus",this.updateStatus)
            params.append("id",this.updateID)
            params.append("type",this.activeName)
            axios.post("../../additional_rules/addRulesLeid",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("操作成功");
                    this.dialogVisibleAr = false
                    this.queryRoles();
                    this.queryRolesTotal();
                }
            })
        },
        saveBy3(){
            if (this.additionalData[3].context1==''){
                this.$message("请输入必要的内容");
                return;
            }
            var params = new URLSearchParams();
            params.append("context1",this.additionalData[3].context1)
            params.append("selected",this.selected)
            params.append("updateStatus",this.updateStatus)
            params.append("id",this.updateID)
            params.append("type",this.activeName)
            axios.post("../../additional_rules/addRulesOther",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("操作成功");
                    this.dialogVisibleAr = false
                    this.queryRoles();
                    this.queryRolesTotal();
                }
            })
        },
        //成果类型变化事件
        changeType(){
            if (this.selected==''){
                return;
            }
            if (this.activeName==2){
                var params = new URLSearchParams();
                params.append("trid",this.selected)
                axios.post("../../additional_rules/queryLeidByTrid",params).then(res=>{
                    this.lnames = res.data.data
                })
            }
        },
        modifyStatus(row){
            var params = new URLSearchParams();
            params.append("status",row.status)
            params.append("id",row.rid);
            axios.post("../../additional_rules/updateStatus",params).then(res=>{
                if (res.data.code==1){
                    this.$message.success("修改状态成功");
                }
            })
        },
        //得到lname
        getLabelValue() {
            const selectedId = this.additionalData[this.activeName].childid;
            const selectedItem = this.lnames.find(item => item.leid === selectedId);
            const labelValue = selectedItem ? selectedItem.lname : '';
            return labelValue ? `[${labelValue}]` : '';
        },
    },mounted() {
        this.getTrname();
    },
    created(){
        this.queryRoles();
    }
})