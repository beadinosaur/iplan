<template>
    <div class="login">
       <el-card class="box-card">
          <div slot="header" class="clearfix">
             <span>iplan</span>
            </div>
            <el-form 
                label-width="80px" 
                :model="form"
                ref="form" :rules="rules">
                <el-row>
                    <el-form-item label="用户名" prop="userName">
                       <el-input v-model="form.userName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="密码" prop="password">
                       <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item>
                        <el-col :span="8">
                            <el-button class="b1" type="primary" @click="login('form')">登录</el-button>
                        </el-col>
                        <el-col :span="8">
                            <el-button class="b2" type="primary" @click="reset('form')">重置</el-button>
                        </el-col>
                        <el-col :span="8">
                           <el-link type="primary" @click="register('form')">注册账号</el-link>
                        </el-col>
                    </el-form-item>
                </el-row>
               
                
            </el-form>
        </el-card>
    </div>
</template>
<script>
//To modularize, you need to import the corresponding methods
import {login} from '@/api/api.js'
import {setToken} from '../utils/setToken.js'
export default{
    data(){
        var nameRule=(rule, value, callback) =>{
            //Please enter 4-10 as nickname
            let reg=/(^[a-zA-Z0-9]{4,10})/;
            if(value===""){
                 callback(new Error("请输入用户名"));
            }else if(!reg.test(value)){
                   callback(new Error("请输入4-10位用户名"));
                }else{
                    callback();
                }
        };
        var passRule=(rule, value, callback) =>{
             //Please enter a 6-12 digit password containing upper and lower case letters and numbers
                let pass=/^S*(?=\S{6,12})(?=\S+\d)(?=\S*[A-Z])(?=\S*[a-z])\S*$/;
              if(value===""){
                    callback(new Error("请输入密码"));
                }else if(!pass.test(value)){
                    callback(new Error("6-12位密码需要包含大小写字母和数字"));
                }else {
                    callback();
                }
        };
        return{
            form:{
                userName:'',
                password:''
            },
            rules:{
                userName:[{validator:nameRule,trigger:'blur'}],
                password:[{validator:passRule,trigger:'blur'}]
            }
        }
    },
    methods:{
        login(form){
            this.$refs[form].validate((valid)=>{
                if(valid){
                   console.log(this.form)
                   //alert("提交成功!");
                   //Wrap the login method into an api to call
                    // this.service.post('/user/signIn',this.form)
                    // .then((res)=>{
                        // if(res.data.status===200){
                        //     setToken('userName',res.data.userName)
                        //     setToken('token',res.data.token)
                        //     this.$message({message:res.data.message,type:'success'})
                        //     this.$router.push('/home')
                    //     }
                    //     console.log(res)
                    // })
                    // //Encapsulate into api and call
                    login(this.form).then(res=>{
                        
                        if(res.status===200){
                            //When the backend returns the code that the login was successful, we save the token
                            setToken('userName',res.data.userName)
                            setToken('token',res.data.token)
                            //this.$message({message:res.data.message,type:'success'})
                            alert("提交成功!");
                            this.$router.push('/home')
                        }else{
                            alert("提交失敗!");
                        }
                    }).catch((err)=>{
                        console.log(err);
                    });
                }else{
                    console.error(this.form)
                }
            });
        },
        register(from){
            this.$router.push('/register')
        },
        reset(form){
            this.$refs[form].resetFields();
        }
    },
};
</script>
<style lang="scss">
.el-card{
    background: #65768557;
}
.login{
    width: 100%;
    height: 100%;
    position:absolute;
    background: url('../assets/1.png');
    .box-card{
        width: 450px;
        margin: 200px auto;
        color:#fff;
        .el-form-item__label{
            width: 100%;
            color: #fff;
        }
        .el-card__header{
            font-size:34px;
        }
        .el-button.b1{
            background: #65768557;
        }      
        .el-button.b2{
            background: #65768557;
        } 
    }
}

</style>