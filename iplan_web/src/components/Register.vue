<template>
    <div class="register">
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
                    <el-form-item label="确认密码" prop="checkpass">
                    <el-input type="password" v-model="form.checkpass" autocomplete="off"></el-input>
                </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item>
                        <el-col :span="8">
                            <el-button class="b1" type="primary" @click="registion('form')">注册</el-button>
                        </el-col>
                        <el-col :span="8">
                            <el-button class="b2" type="primary" @click="reset('form')">重置</el-button>
                        </el-col>
                        <el-col :span="8">
                           <el-link type="primary" @click="login('form')">返回登录</el-link>
                        </el-col>
                    </el-form-item>
                </el-row>
            </el-form>
        </el-card>
    </div>
</template>

<script>
//To modularize, you need to import the corresponding methods
import {register} from '@/api/api.js'
import {setToken} from '../utils/setToken.js'
export default{
    data(){
        //Login Verification
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
        //Password verification
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
        //Password verification
        var passRule2=(rule, value, callback) =>{
             //Please enter a 6-12 digit password containing upper and lower case letters and numbers
               let pass2=/^S*(?=\S{6,12})(?=\S+\d)(?=\S*[A-Z])(?=\S*[a-z])\S*$/;
                if(value===""){
                     callback(new Error("请再次输入密码"));
                }else if(!pass2.test(value)){
                     callback(new Error("6-12位密码需要包含大小写字母和数字"));
                }else if(!Object.is(value,this.form.password)){
                     callback(new Error("两次输入密码不一致"));
                }else{
                        callback();
                }
        };
        return{
            form:{
                userName:"",
                password:"",
                checkpass:"",
            },
            rules:{
                userName:[{validator:nameRule,trigger:'blur'}],
                password:[{validator:passRule,trigger:'blur'}],
                checkpass:[{validator:passRule2,trigger:'blur'}]
            }
        }
    },
    methods:{
        registion(form){
            this.$refs[form].validate((valid)=>{
                if(valid){
                    // console.log(this.form)
                //    alert("提交成功!");
                   register(this.form).then(res=>{
                        if(res.status===200){
                            //When the backend returns the code that the login was successful, we save the token
                            setToken('userName',res.data.userName)
                            setToken('password',res.data.password)
                            setToken('token',res.data.token)
                            //this.$message({message:res.data.message,type:'success'})
                            alert("提交成功!");
                            this.$router.push('/login')
                        }else{
                            alert("提交失败!");  // ui pop-up tips
                        }
                    })
                }else{
                    // console.log("提交失败!!");
                    // return false;
                    console.error(this.form)
                }
            });
        },
        login(from){
            this.$router.push('/login')
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
.el-row {
    &:last-child {
      margin-bottom: 0;
    }
  }
.el-col {
    border-radius: 4px;
  }
.register{
    width: 100%;
    height: 100%;
    position:absolute;
    background: url('../assets/1.png');
    //background:#fff;
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