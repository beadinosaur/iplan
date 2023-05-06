<template>
    <div class="basic-block">
      <el-card>
        <div slot="header" class="title">
          <span>{{ title }}</span>
        </div>
        <div>
          <slot></slot>
        </div>
      </el-card>
    </div>
  </template>
  
  <script>
  export default {
    name: "header-bar",
    props: {
      title: {
        type: String,
        default: '标题',
      },
    }
  }
  </script>
  
  <style lang="scss" rel="stylesheet/scss">
  .basic-block {
    margin-top: 0;
    padding: 0px 10px;
    box-sizing: border-box;
  
    .el-card {
      width: 100%;
    }
  
    &:first-child {
      padding-top: 0;
      margin-top: 10px;
      margin-bottom: 10px;
    }
  
    .el-card__header {
      padding: 0;
    }
  
    .title {
      font-size: 14px;
      padding: 10px 15px;
      color: #333;
      font-weight: bold;
    }
  }
  </style>