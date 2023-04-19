// 这这里进行router相关的代码配置
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

// 引入
import Home from '../views/Home.vue'
import User from '../views/User.vue'
import Main from '../views/Main.vue'
import Mall from '../views/Mall.vue'
import Page1 from '../views/Page1.vue'
import Page2 from '../views/Page2.vue'



//1.创建路由组件
//（我们一般把每个页面作为路由，在src下面的views里）

//2.将路由组件和我们的路由进行映射
// const routes = [
// { path: '/home', component: Home },
// { path: '/user', component: User }
// ]



const routes = [
    // 主路由
    { 
        path: '/', 
        component:Main,
        redirect:'/home' ,//重定向
        //子路由
        children:[
    { path: 'home', component: Home },//主页
    { path: 'user', component: User},//用户
    { path: 'mall', component: Mall },//日历
    { path: 'page1', component: Page1},//页面1
    { path: 'page2', component: Page2},//页面2
        ]
    }

    // { path: '/home', component: Home },
    // { path: '/user', component: User }
  ]

// 3. 创建 router 实例,然后传'routes'配置
const router = new VueRouter({
    routes // (缩写) 相当于 routes: routes
  })

//  对router实例向外进行一个出口
// 4.创建和挂载根实例
    export default router


