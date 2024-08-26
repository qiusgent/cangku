<template>
  <div style=" background-color: #c0cad9;height: 100vh">
    <div class="header">
      <h2 class="logo">仓库管理系统</h2>
    </div>
      <div class="loginBody">
          <div class="loginDiv">
              <div class="login-content">
                  <h2 class="login-title">logon</h2>
                  <el-form :model="loginForm"
                           :rules="rules" ref="loginForm">          <!--prop传入 Form 组件的 model 中的字段-->
                      <el-form-item label="账号" prop="no">          <!--rules表单验证规则-->
                                                                    <!-- v-model 责监听用户的输入事件以更新数据-->
                          <el-input style="width: 200px" type="text" v-model="loginForm.no"
                                    autocomplete="off" size="small"></el-input>
                      </el-form-item>
                      <el-form-item label="密码" prop="password">
                          <el-input style="width: 200px" type="password" v-model="loginForm.password"
                                    show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
                      </el-form-item>                                  <!--@keyup.enter.native 回车-->
                      <el-form-item class="denglu">                   <!--disabled 禁用某个元素 一开始为禁止该按钮（false为可交互，true不可），点击时触法confirm方法-->
                          <el-button type="primary" @click="confirm" :disabled="confirm_disabled" style="margin-right:6%" >登 录</el-button>
                        <router-link to="/zhuce"><el-button >注 册</el-button></router-link>
                      </el-form-item>
                  </el-form>
              </div>
          </div>
      </div>
  </div>
</template>


<script>
    export default {
        name: "Login",
        data(){
            return{
                confirm_disabled:false,
                loginForm:{
                    no:'',
                    password:''
                },
                rules:{
                    no: [
                        /*如果该项为空，则会触发验证失败  定义了当验证失败时显示的错误提示信息
                        blur'意味着当用户离开输入框（失去焦点）时触发验证。'change'，表示在输入内容发生变化时触发验证*/
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输密码', trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{

            confirm(){
                this.confirm_disabled=true;
                /*validate 是Vue.js中的一些表单验证库（如Vuelidate或VeeValidate）提供的一种方法，用于触发表单或表单中特定字段的验证。*/
                this.$refs.loginForm.validate((valid) => {
                    if (valid) { //valid成功为true，失败为false
                        //去后台验证用户名密码
                        this.$axios.post(this.$httpUrl+'/user/login',this.loginForm).then(res=>res.data).then(res=>{
                            console.log(res)
                            if(res.code==200){
                                //存储
                              /*JSON.stringify() 方法将一个对象或值转换为一个 JSON 字符串*/
                                sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
                                this.$store.commit("setMenu",res.data.menu)

                                //跳转到主页 replace 方法会将当前路由替换为新的路由
                                this.$router.replace('/index');
                            }else{
                                this.confirm_disabled=false;
                                alert('校验失败，用户名或密码错误！');
                                return false;
                            }
                        });
                    } else {
                        this.confirm_disabled=false;
                        console.log('校验失败');
                        return false;
                    }
                });

            }
        }
    }
</script>

<style scoped>
    .header{
      z-index: 452;
      height: 104px;
      background: #b3c0d1;
      color: #ffffff;
      margin: 0;
      width: 100%;
      align-items: center;
      display: flex;
    }
    .logo {
      margin: 30px auto;
      font-size: 43px;
      text-align: center;
      font-family: 华文彩云;
    }
    .loginBody {

    }
    .loginDiv {
      margin: 100px auto;
      border-radius: 10px;
      box-shadow: 0 15px 25px 0 rgba(0, 0, 0, -6);
      width: 350px;
      height: 280px;
      background: #fff;
      padding: 40px;
      box-sizing: border-box;
    }
    .login-title {
      text-align: center;
      margin-bottom: 30px;
      font-family: 'Courier New', Courier, monospace;
      color: #939dab;
    }
    .login-content {

    }
    .denglu{
      margin-top: 10%;
      text-align: center;
    }

</style>