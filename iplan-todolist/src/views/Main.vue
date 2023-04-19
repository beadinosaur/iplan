<template>
    <div>
     
        
        <el-container>
        <el-aside width="auto">
                <!--这个用相应的模式  -->
            <common-aside/>

        </el-aside>
        <el-container>
                <!-- 表示的是内容区域 -->
            <el-header>
                <Common-header/>
            </el-header>
            <el-main>
                <!-- 路由出口 -->
                <!-- 路由匹配到的组件将渲染在这里 -->
                <router-view></router-view>
            </el-main>

        </el-container>
        </el-container>
    </div>
    
 </template>
 <script>
//  组件化思想，其实也可以直接写在这里，主页的导航
 import CommonAside from '../components/CommonAside.vue'
 import CommonHeader from '../components/CommonHeader.vue'
 
 export default{
     data(){
         return{}
     },
    //  主页导航的引入
         components:{
            CommonAside,
            CommonHeader
         }

     }
 
 </script>
 <style scoped>
 .el-header{
    padding: 0;
 }
</style>