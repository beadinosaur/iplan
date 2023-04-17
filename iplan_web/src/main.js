import Vue from 'vue'
import App from './App.vue'
// import '../plugins/element.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
//import axios from 'axios'
import router from './router'
import service from './service.js'

Vue.use(ElementUI)
//Vue.prototype.axios=axios//Mounted to the prototype, it can be used globally
Vue.prototype.service=service//Mounted to the prototype, it can be used globally
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
