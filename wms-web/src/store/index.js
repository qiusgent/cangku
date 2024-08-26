import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "../router";
import createPersistedState from  'vuex-persistedstate'
vue.use(Vuex)

function addNewRoute(menuList) {

    console.log(menuList)
    /*路由列表*/
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if(routeItem.path==="/index"){
            menuList.forEach(menu=>{
                let childRoute =  {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }

                /*添加到路由里*/
                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    /*添加到router里的routes数组，vue中路由权限一般用addRoutes实现*/
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state,menuList) {
            state.menu = menuList

            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu
        }
    },
    plugins:[createPersistedState()]
})