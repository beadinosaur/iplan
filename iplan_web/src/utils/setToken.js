//To log in to the actual interface address, you need to get the corresponding token and carry it to the request header
//Settings
export function setToken(tokenKey,token){
    return localStorage.setItem(tokenKey,token)
}
//Get
export function getToken(tokenKey){
    return localStorage.getItem(tokenKey)
}
//Delete
export function removetToken(tokenKey){
    return localStorage.removeItem(tokenKey)
}