<template>
  <div style=" background-color: #c0cad9;height: 100vh">
    <div class="header">
      <h2 class="logo">仓库管理系统</h2>
    </div>
    <div class="loginBody">
      <div class="loginDiv">
        <div class="login-content">
          <h2 class="login-title">login</h2>
          <el-form
                    ref="loginForm">

            <el-form ref="form" :model="form" :rules="rules" label-width="51px">

              <el-form-item label="账号" prop="no">
                <el-col :span="20">
                  <el-input v-model="form.no"></el-input>
                </el-col>
              </el-form-item>
              <el-form-item label="名字" prop="name">
                <el-col :span="20">
                  <el-input v-model="form.name"></el-input>
                </el-col>
              </el-form-item>

              <el-form-item label="密码" prop="password">
                <el-col :span="20">
                  <el-input v-model="form.password"></el-input>
                </el-col>
              </el-form-item>

              <el-form-item label="年龄" prop="age">
                <el-col :span="20">
                  <el-input v-model="form.age"></el-input>
                </el-col>
              </el-form-item>

              <el-form-item label="性别">
                <el-radio-group v-model="form.sex">
                  <el-radio label="1">男</el-radio>
                  <el-radio label="0">女</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="电话" prop="phone">
                <el-col :span="20">
                  <el-input v-model="form.phone"></el-input>
                </el-col>
              </el-form-item>

            </el-form>


            <el-form-item class="denglu">
              <el-button  @click="save">注 册</el-button>
              <router-link to="/"><el-button type="primary" style="margin-left:6%" >登 录</el-button>
                </router-link>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Zhuce",
  data(){
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输⼊过⼤'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get("http://localhost:8090/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!==200){
          callback()
        }else{
          callback(new Error('账号已经存在'));
        }
      })
    };
    return{
      confirm_disabled:false,
      name:'',
      sex:'',
      sexs:[
        {
          value: '1',
          label: '男'
        },
        {
          value: '0',
          label: '女'
        }
      ],
      form:{
        no:'',
        name:'',
        password:'',
        age:'',
        phone:'',
        sex:'0',
        roleId:'2'
      },
      rules:{
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输⼊年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '⻓度在 1 到 3 个位', trigger: 'blur'},
            /*这个正则表达式匹配的是一个字符串，它由1-3个数字组成，第一个数字不能是0*/
          {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true, message: "⼿机号不能为空", trigger: "blur"},
          {
            /*这个正则表达式匹配的是一个字符串，它由11个数字组成，符合中国大陆手机号码的格式*/
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger:
                "blur"
          }
        ]
      }
    }
  },
  methods:{
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });

          this.loadPost()
        }
        else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/user/updata',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });

          this.loadPost()
        }
        else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          return false;
        }
      })
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          sex:this.sex
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }
        else {
          alert('获取数据失败')
        }
      })
    },
  },
  beforeMount() {
    // this.loadGet();
    this. loadPost();
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
      margin: 3% auto;
      border-radius: 10px;
      box-shadow: 0 15px 25px 0 rgba(0, 0, 0, -6);
      width: 350px;
      height: 480px;
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