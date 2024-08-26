<template>
  <div style="display: flex;line-height: 60px">
    <div style="margin-top: 0.25%;">
      <i :class="icon" style="font-size: 20px;cursor: pointer" @click="collapse" ></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 30px">
      <span style="font-family: STXinwei; font-size: 30px ">欢迎来到仓库管理系统</span>
    </div>
    <span>{{user.name}}</span>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
           <!--@click不生效时加上 native(@click.native 可以直接监听原生的点击事件，不需要经过组件的封装和处理)-->
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登入</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser'))  /*字符串用json.parse转换*/
    }
  },
  props:{
    icon:String
  },
  methods:{
    toUser(){
      console.log("to_user")
      this.$router.push("/Home");
    },
    logout(){
      console.log("logout")
            /*$confirm:element框架 弹窗*/
      this.$confirm('您确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',  //确认按钮的文字显示
        type: 'warning',
        center: true, //文字居中显示

      })
          .then(() => {
            this.$message({
              type:'success',
              message:'退出登录成功'
            })
            this.$router.push("/")
            sessionStorage.clear()
          })
          .catch(() => {
            this.$message({
              type:'info',
              message:'已取消退出登录'
            })
          })
    },
    collapse(){
      /*$emit(发出)传入的事件*/
      this.$emit('doCollapse')
    },

  }
}
</script>

<style scoped>

</style>