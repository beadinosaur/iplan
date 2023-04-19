const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false //关闭eslint校验  //（eslint就是引入代码风格方式的插件，如果没有按照所定义的规范来进行编写，都会引起报错）
})
