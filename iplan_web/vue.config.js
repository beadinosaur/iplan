module.exports = { 
     lintOnSave: false,
    devServer:{
        open:true,
        proxy:{//Cross-domain
            '/api':{
                target:'http://localhost:8081',//Destination address, root path
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
           
        }
    }
}
