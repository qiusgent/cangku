<template>
<!--  :collapse 带边框风格的折叠面板（bordered），类型：boolean，默认 true;-->
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      default-active="/Home"
      :collapse="isCollapse"
      :collapse-transition="false"
      :default-active="$route.path"
      style="height: 100%"
      router
  >
     <el-menu-item index="/Home">
       <i class="el-icon-s-home"></i>
       <span slot="title">首页</span>
     </el-menu-item>

<!-- v-for 遍历 menu 数组,并为每个元素渲染一个 el-menu-item 组件。
    (item,i) 是一个解构赋值,其中 item 代表当前遍历的菜单项对象,i 代表当前项的索引。-->
<!-- :index 将菜单项的 menuclick 属性值与一个斜杠 (/) 连接起来-->
    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
<!-- slot插槽：渲染组件的标题位置-->
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>

    <el-menu-item index="/Info" @click="loadGet">
      <i class="el-icon-s-comment"></i>
      <span slot="title">通知
        <el-badge v-if="info_num" :value="info_num" class="item" style="margin-bottom: 5px">
        </el-badge>
      </span>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      info_num: null
      // isCollapse:false

      /*menu:[
        {
          menuClick:'Admin',
          menuName:'管理管理员',
          menuIcon:'el-icon-s-custom'
        },
        {
          menuClick:'User',
          menuName:'用户管理',
          menuIcon:'el-icon-user-solid'
        },
      ]*/
    }
  },
  computed:{
    "menu":{
      get(){
        /*取出menu*/
        return this.$store.state.menu
      }
    }
  },
  props:{
    isCollapse:Boolean
  },

  methods: {
    loadGet() { // 从后端获取数据
      this.$axios.get(this.$httpUrl+"/info/list").then(res => res.data).then(res => {
        this.info_num = res
      })
    },
  },
  beforeMount() { // 页面加载前
    this.loadGet();
  }
}
</script>

<style scoped>
/*.aside {*/
/*  height: 100%;*/
/*}*/
</style>