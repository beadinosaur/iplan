<template>
    <el-row>
    <el-col :span="8">
     <el-card class="box-card">
        <div class="user">
            <img src="../assets/images/user.png" alt="">
            <div class="userinfo">
                <p class="name">Admin</p>
                <p class="access">242</p>
            </div>
        </div>
        <div class="login-info">
            <p>上次登录的时间：<span>2023-4-19</span></p>
            <p>上次登录的地点：<span>上海</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px; height: 460px; width: 900px;">
            <el-table
            :data="tableData"
            style="width: 100%">
                <!-- <el-table-column
                    prop="name"
                    label="课程"
                    >
                </el-table-column>
                <el-table-column
                    prop="todayBuy"
                    label="今日购买"
                    >
                </el-table-column>
                <el-table-column
                    prop="monthBuy"
                    label="本周购买"
                    >
                </el-table-column>
                <el-table-column
                    prop="totalBuy"
                    label="本月购买"
                    >
                </el-table-column> -->
                <el-table-column v-for="(val,key) in tableLabel" :key="key" :prop="key" :label="val" />
                <!-- 这个是上面的优化 -->
                
            </el-table>
      </el-card>
    </el-col>
  <el-col :span="16"><div class="grid-content bg-purple-light"></div></el-col>
   </el-row>
</template>
<script>
export default{
    data(){
        return{
            tableData:[
            {
            name: '前端',
            todayBuy: 'vue知识点复习',
            weekBuy: '总结本周项目的收获',
            monthBuy: '尽量完成iplan项目'
          },
          {
            name: '量子论文',
            todayBuy: '3小时论文学习',
            weekBuy: '总结本周论文收获',
            monthBuy: '读过8篇论文+'
          },
          {
            name: '生活琐事',
            todayBuy: '今日快递',
            weekBuy: '这周末去吃烧烤',
            monthBuy: '月底出去玩'
          },
          {
            name: '锻炼',
            todayBuy: '今日3公里跑步',
            weekBuy: '这周休息',
            monthBuy: '享受生活，拒绝内耗'
          },
        //   {
        //     name: '三星',
        //     todayBuy: 100,
        //     weekBuy: 300,
        //     monthBuy: 800
        //   },
        //   {
        //     name: 'kk',
        //     todayBuy: 100,
        //     weekBuy: 300,
        //     monthBuy: 800
        //   }
         
            ],
            tableLabel:{
                name:'学习科目',
                todayBuy:'今日待办',
                weekBuy:'本周待办',
                monthBuy:'本月待办',
// 这个对应上面的优化
            }
        }
    }
}
</script>
<style lang="less" scoped>
.user{
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom:1px;
    display: flex;
    align-items: center;
    img{
        width: 150px;
        height: 150px;
        border-radius: 50%;
        margin-right: 40px;
    }
    .userinfo{
           
        .name{
            font-size: 32px;
            margin-bottom:10px;
       } 
          .access{
            color: #999999;
          }
    } 
}
 .login-info{
    p{
                line-height: 28px;
                font-size: 14px;
                color: #999999;
                span{
                    color: #666666;
                    margin-left: 70px;
                }
            }
 }
</style>