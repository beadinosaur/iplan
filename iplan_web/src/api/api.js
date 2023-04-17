// Most of the time in the project, the corresponding interface request is sealed as an api to call
import service from '../service.js'

//Login Interface
export function login(data){
    return service({
        method:'post',
        url:'/user/signIn',
        data:data
    })
}
//Registration Interface
export function register(data){
    return service({
        method:'post',
        url:'/user/signUp',
        data:data
    })
}