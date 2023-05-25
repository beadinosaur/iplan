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
//add plan
export function addPlan(data){
    return service({
        method:'post',
        url:'/dailyPlan/saveOrUpdate',
        data:data
    })
}

export function selectPlanDataByTime(data){
    return service({
        method:'post',
        url:'/dailyPlan/getPlanDataByTime',
        data:data
    })
}

export function selectPlanDataByMonth(data){
    return service({
        method:'post',
        url:'/dailyPlan/listByMonthRange',
        data:data
    })
}

export function deleteByPlanId(data){
    return service({
        method:'post',
        url:'/dailyPlan/delete',
        data:data
    })
}

export function pullEmail(){
    return service({
        method:'post',
        url:'/conferenceData/transferEmail'
    })
}

export function pullConference(){
    return service({
        method:'post',
        url:'/conferenceData/transferConference'
    })
}

export function emailConfig(data){
    return service({
        method:'post',
        url:'/emailConfig/saveOrUpdate',
        data:data
    })
}

export function getHotWords(){
    return service({
        method:'post',
        url:'/dailyPlanTitle/getHotWord',
    })
}

export function searchByKeyWord(data){
    return service({
        method:'post',
        url:'/dailyPlan/getByHotWords',
        data:data
    })
}

export function selectByEmail(data){
    return service({
        method:'post',
        url:'/dailyPlan/getByEmail',
        data:data
    })
}


export function exportPlanList(data){
    return service({
        method:'post',
        headers: {
            'Content-Type': 'application/json'
        },
        responseType: 'blob',
        url:'/export/easyExcelReport',
        data:data
    })
}
