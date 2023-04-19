import Vue from 'vue';
import ElementUI from 'element-ui';  //全局引入

// import {Row,Button} from 'element-ui'; //按需引入

import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

import router from './router' 
import store from './store' 
import axios from 'axios'
import VueAxios from 'vue-axios'



Vue.config.productionTip = false

Vue.use(ElementUI); //全局引入
Vue.use(VueAxios,axios)  //VueAxios,axios位置不能交换，否则会出现报错

// // 按需引入
//    Vue.use(Row)
//    Vue.use(Button)

new Vue({
  router,  //在当前实例上进行挂载,
  store,//在当前实例上进行挂载,
  render: h => h(App),
}).$mount('#app')
 