<!-- <template>
    <h1>我是日历查询</h1> 
 </template>
 <script>
 export default{
     data(){
         return{
             
         }
     }
 }
 </script> -->

<template>
     <el-calendar v-model="value">
</el-calendar>
 </template>
<script>
export default {
  data() {
    return {
      value: new Date()
    }
  }
}
</script>
