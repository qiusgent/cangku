import VueRouter from 'vue-router';
const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/zhuce',
        name:'zhuce',
        component:()=>import('../components/zhuce/Zhuce')
    },

    {
        path:'/index',
        name:'index',
        component:()=>import('../components/index'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/Home'),
            },
            {
                path: '/Info',
                name: 'info',
                meta: {title: '通知'},
                component: () => import('../components/info/InfoManage')
            },
            /*{
                path:'/Admin',
                name:'admin',
                meta:{
                    title:'管理员管理'
                },
                component:()=>import('../components/Admin/AdminManage'),
            },
            {
                path:'/User',
                name:'user',
                meta:{
                    title:'用户管理'
                },
                component:()=>import('../components/User/UserManage'),
            },*/
        ]
    }
]

const router = new VueRouter({
    mode:'history',  /*去掉路径中的#号*/
    routes
})

/*路由重复错误*/
export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}

/*路由跳转时可能出现的重复导航错误*/
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

export default router;