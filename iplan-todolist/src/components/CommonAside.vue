<template>

    <el-menu default-active="1-4-1" 
    class="el-menu-vertical-demo" 
    @open="handleOpen"
    @close="handleClose" 
    :collapse="isCollapse"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b">


      <h2>{{isCollapse ?'iplan':'iplan项目管理'}}</h2>

      <el-menu-item @click="clickMenu(item)"  v-for="item in noChildren" :key="item.name"  :index="item.name">
        <!-- v-blind的简写： -->
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label}}</span>
        </el-menu-item>

        <el-submenu v-for ="item in hasChildren" :key="item.label" :index="item.label">
        <template slot="title">
            <i :class="`el-icon-${item.icon}`"></i>
            <span slot="title">{{ item.label}}</span>
        </template>
        <el-menu-item-group v-for="subItem in item.children" :key="subItem.path">
            <el-menu-item @click="clickMenu(subItem)" :index="subItem.path">{{subItem.label}}</el-menu-item>
        </el-menu-item-group>
        
     </el-submenu>
       
    </el-menu>

</template>

  <style lang="less" scoped>
  // scoped代表只作用当前页面
    .el-menu-vertical-demo:not(.el-menu--collapse) {
      width: 200px;
      min-height: 400px;
    }
    .el-menu{
      height:100vh;
      h2{
        color:#fff;
        text-align: center;
        line-height: 48px;
        font-size: 24px;
        font-weight: 700;
      }
    }
  </style>
  
  <script>
    export default {
      data() {
        return {
          // // isCollapse: true //默认收起
          // isCollapse: false ,  //展开
          // 新建的字段
          menuData:[
            {
              path: '/',
              name: 'home',
              label: '首页',
              icon: 's-home',
              url: 'Home/Home'
            },
            {
              path: '/mall',
              name: 'mall',
              label: '日历',
              icon: 'date',
              url: 'MallManage/MallManage'
            },
            {
              path: '/user',
              name: 'user',
              label: '今日待办',
              icon: 'user',
              url: 'UserManage/UserManage'
            },
            {
              label: '其他',
              icon: 'location',
              children: [
                {
                  path: '/page1',
                  name: 'page1',
                  label: '页面1',
                  icon: 'setting',
                  url: 'Other/PageOne'
                },
                {
                  path: '/page2',
                  name: 'page2',
                  label: '页面2',
                  icon: 'setting',
                  url: 'Other/PageTwo'
                }
              ]
            }
          ]
        };
      },
      methods: {
        handleOpen(key, keyPath) {
          console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
          console.log(key, keyPath);
        },
        // 点击菜单,进行路由跳转
clickMenu(item){

  // 防止当前页面与跳转页面一致会出现报错，及当页面的路由与跳转的路由不一致时才运行跳转
  if(this.$route.path !==item.path&&!(this.$route.path ==='/home'&&(item.path==='/'))) //route表示当前的路由
  {
    this.$router.push(item.path)  //router表示的是router实例
  }

  // this.$router.push(item.path)  //router表示的是router实例
      }


},
      computed:{
          // 没有子菜单
          noChildren(){
          return this.menuData.filter(item =>!item.children)
          },
          // 有子菜单
          hasChildren(){
            return this.menuData.filter(item =>item.children)
          },

          isCollapse(){
            return this.$store.state.tab.isCollapse
          }
      }

    }
  </script>
  <style lang="less" scoped>
  .el-menu{
    border-right: none;
  }

</style>