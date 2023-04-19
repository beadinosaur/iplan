import Vue from 'vue'
import Vuex from 'vuex'
import tab from './tab'

Vue.use(Vuex)
// 全局引入

// 创建vuex的实例
export default new Vuex.Store(
    {
          modules:{
             tab
          }
    }
 )
