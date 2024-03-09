let app = new Vue({
    el: "#app",
    data: {
        user: {
            username: "",
            password: "",
            isAdmin:false
        },
        // 页面加载效果
        loading: false,
        identity: 0, // 表示身份的状态值， 0为学生，1为老师
        yanzhen: "",// 输入框双向绑定的值
        true_code: "",// 保存正确的验证码
        yanzhen_arr:[],// 只用于传参，并且数组长度不能「多于」下面验证码遍历的次数，不然最终得到的true_code会有问题,比如下面是4个验证码，可以是[1,2,3,4]及以下，但是不能是[1，2，3，4，5]， 因为5无法被替换
    },
    mounted() {
        if("true" == localStorage.getItem("isAmdinStatus")){
            this.user.isAdmin = true;
        }
        sessionStorage.removeItem("user");
        this.draw(this.yanzhen_arr, this.identity);
        if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|ios|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
            this.$message({message: "系统检测当前设备是手机,可能会影响正常使用,推荐使用电脑进入,如不是请忽略!", type: "warning"});
        }
    },
    methods: {
        //显示验证码区域内容信息
        draw(show_num, identity) {
            var canvas_width;
            var canvas_height;
            var canvas;
            if (identity!==0){
                canvas_width =  document.querySelector("#tcanvas").clientWidth;
                canvas_height = document.querySelector("#tcanvas").clientHeight;
                canvas = document.getElementById("tcanvas"); //获取到canvas
            } else {
                canvas_width =  document.querySelector("#scanvas").clientWidth;
                canvas_height = document.querySelector("#scanvas").clientHeight;
                canvas = document.getElementById("scanvas"); //获取到canvas
            }
            var context = canvas.getContext("2d"); //获取到canvas画图
            canvas.width = canvas_width;
            canvas.height = canvas_height;
            var sCode =
                "a,b,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
            var aCode = sCode.split(",");
            var aLength = aCode.length; //获取到数组的长度
            //4个验证码数
            for (var i = 0; i <= 3; i++) {
                var j = Math.floor(Math.random() * aLength); //获取到随机的索引值
                var deg = (Math.random() * 30 * Math.PI) / 180; //产生0~30之间的随机弧度
                var txt = aCode[j]; //得到随机的一个内容
                show_num[i] = txt.toLowerCase();// 依次把取得的内容放到数组里面
                var x = 3 + i * 17; //文字在canvas上的x坐标
                var y = 20 + Math.random() * 8; //文字在canvas上的y坐标
                context.font = "bold 23px 微软雅黑";
                context.translate(x, y);
                context.rotate(deg);
                context.fillStyle = this.randomColor();
                context.fillText(txt, 0, 0);
                context.rotate(-deg);
                context.translate(-x, -y);
            }
            //验证码上显示6条线条
            for (var i = 0; i <= 4; i++) {
                context.strokeStyle = this.randomColor();
                context.beginPath();
                context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
                context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
                context.stroke();
            }
            //验证码上显示31个小点
            for (var i = 0; i <= 25; i++) {
                context.strokeStyle = this.randomColor();
                context.beginPath();
                var x = Math.random() * canvas_width;
                var y = Math.random() * canvas_height;
                context.moveTo(x, y);
                context.lineTo(x + 1, y + 1);
                context.stroke();
            }
            //最后把取得的验证码数组存起来，方式不唯一
            var num = show_num.join("");
            this.true_code = num
        },
        //得到随机的颜色值
        randomColor() {
            var r = Math.floor(Math.random() * 256);
            var g = Math.floor(Math.random() * 256);
            var b = Math.floor(Math.random() * 256);
            return "rgb(" + r + "," + g + "," + b + ")";
        },
        //canvas点击刷新
        handleCanvas(){
            this.draw(this.yanzhen_arr, this.$data.identity);
        },
        tclick() {
            this.$data.identity=1;
            let form_box = document.getElementsByClassName('form-box')[0];
            let register_box = document.getElementsByClassName('tea-login-box')[0];
            let login_box = document.getElementsByClassName('stu-login-box')[0];
            form_box.style.transform = 'translateX(80%)';
            login_box.classList.add('hidden');
            register_box.classList.remove('hidden');
            this.handleCanvas();
        },
        sclick() {
            this.$data.identity=0;
            let form_box = document.getElementsByClassName('form-box')[0];
            let register_box = document.getElementsByClassName('tea-login-box')[0];
            let login_box = document.getElementsByClassName('stu-login-box')[0];
            form_box.style.transform = 'translateX(0%)';
            register_box.classList.add('hidden');
            login_box.classList.remove('hidden');
            this.handleCanvas();
        },
        login() {
            const _this = this;
            this.$data.loading = true;
            if (!this.user.username) {
                this.$message({message: "请输入用户名", type: "error"});
                this.$data.loading = false;
                return;
            }
            if (!this.user.password) {
                this.$message({message: "请输入密码", type: "error"});
                this.$data.loading = false;
                return;
            }
            if (!this.$data.yanzhen) {
                this.$message({message: "请输入验证码", type: "error"});
                this.$data.loading = false;
                return;
            }
            if (!(this.$data.yanzhen.toLowerCase()===this.$data.true_code.toLowerCase())) {
                this.$message({message: "验证码错误", type: "error"});
                this.$data.loading = false;
                this.$data.yanzhen = "";
                this.handleCanvas();
                return;
            }
            let params = new URLSearchParams();
            params.append("username", this.user.username);
            params.append("password", this.user.password);
            params.append("identity", this.identity);
            params.append("isAdmin",this.user.isAdmin)
            this.handleCanvas();
            this.yanzhen='';
            let url = "login";
            return axios({method:'POST', baseUrl: '',url, params, header:{}, timeout:30000}).then(res => {
                if (res.data.code == 1) {
                    this.$data.loading = false;
                    this.$message({message: "登录成功", type: "success"});
                    sessionStorage.setItem("user", JSON.stringify(res.data.data));
                    if (res.data.code== 1){
                        if(res.data.data.identity == 1){
                            location.href = "admin_back/frame";
                        }else if(res.data.data.identity == 0){
                            location.href = "t_back/t_frame";
                        }else{
                            this.handleResetting();
                            this.$message({message: "账号识别失败,请联系管理员!", type: "error"});
                        }
                    }else{
                        this.handleResetting();
                        this.$message({message: "登录失败,请稍后再试!", type: "error"});
                    }
                } else {
                    if(typeof (res.data.msg) == "undefined" || res.data.msg == null){
                        this.handleResetting();
                        this.$message({message:"当前网络环境不稳定请稍后再试!", type: "error"});
                    }else{
                        this.handleResetting();
                        this.$message({message: res.data.msg, type: "error"});
                    }
                }
            });
        },handleResetting(){
            this.$data.loading = false;
            this.$data.user.password = "";
            this.$data.yanzhen = "";
            this.handleCanvas();
        },handleIsAdmin(){
            //记录 登录状态
            localStorage.setItem("isAmdinStatus", this.user.isAdmin);
        }
    }
});