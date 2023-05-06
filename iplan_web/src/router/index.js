import Vue from 'vue'
import Router from 'vue-router'
//import Home from '@/components/Home.vue'

Vue.use(Router)

export default new Router(
    {
        routes:[
            {
                path:'/',
                redirect:'/login',
                component:()=>import('@/components/Login')//Routing Lazy Loading
            },
            {
                path:'/login',//Configuring Routing Paths and Components
                name:'login',
                component:()=>import('@/components/Login')//Routing Lazy Loading
            },
            {
                path:'/home',//Configuring Routing Paths and Components
                name:'home',
                //component: Home
                component:()=>import('@/components/Home')//Routing Lazy Loading
                //component:resolve => require(['@/components/Home'],resolve)//Asynchronous components
            },
            {
                path:"/register",
                name:"register",
                component:()=>import('@/components/Register')//Routing Lazy Loading
            },
        ],
        mode:'history'
    }
)