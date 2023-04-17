module.exports = { 
     lintOnSave: false,
    devServer:{
        open:true,
        proxy:{//Cross-domain
            '/api':{
                target:'http://10.66.124.200:8081',//Destination address, root path
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    }
}
