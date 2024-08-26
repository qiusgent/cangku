<template>
    <div style="text-align: center;background-color: #f8f8ff;height: 100%;padding: 0px;margin: 0px;">
      <div class="huanying"><span class="huan_span" >{{'欢迎你！'+user.name}}</span></div>

        <el-descriptions  title="个人中心" :column="2" size="40" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    账号
                </template>
                {{user.no}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    电话
                </template>
                {{user.phone}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    性别
                </template>
                <el-tag
                        :type="user.sex === '1' ? 'primary' : 'danger'"
                        disable-transitions><i :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>{{user.sex==1?"男":"女"}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                    角色
                </template>
                <el-tag
                    :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'primary' : 'success')"
                        disable-transitions>{{user.roleId==0?"超级管理员":(user.roleId==1?"管理员":"用户")}}</el-tag>

            </el-descriptions-item>
        </el-descriptions>

        <DateUtils></DateUtils>
    </div>
</template>

<script>
    import DateUtils from "./DateUtils";
    export default {
        name: "Home",
        components: {DateUtils},
        data() {

            return {
                user:{}
            }
        },
        computed:{

        },
        methods:{
            init(){
              /*JSON.parse 将json字符串 解析成对应的对象*/
                this.user = JSON.parse(sessionStorage.getItem('CurUser'))
            }
        },
      /*created（）：当一个 Vue 实例被创建后，它会立即进入一系列的初始化过程*/
        created(){
            this.init()
        }
    }
</script>

<style scoped>
    .el-descriptions{
        width:90%;

        margin: 0 auto;
        text-align: center;
    }
    .huanying {
      padding: 2% 0;
      margin-left: -2%;
    }
    .huan_span{
      font-size: 50px;
      font-family: STKaiti;
    }
</style>