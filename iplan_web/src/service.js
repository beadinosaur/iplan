import axios from 'axios'
import {getToken} from '@/utils/setToken.js'
import { Promise } from 'core-js'
import { Message } from 'element-ui'

const service = axios.create(
    {
        baseURL:'/api',//The baseURL is automatically added to the request address
        timeout:3000//Number of request timeouts
    }
)
//Adding a request interceptor
service.interceptors.request.use((config)=>
{
    //What to do before the request (get and set the token)
    config.headers['token']=getToken('token')
    return config
},(error)=>{
    return Promise.reject(error)
}
)
//Adding a response interceptor
service.interceptors.response.use((response)=>{
    //What to do with response data
    let {status,message}=response.data
    if(status !==200){
        Message({message:message || 'error',type:'warning'})
    }
    return response
},(error)=>{
    return Promise.reject(error)
}
)
export default service