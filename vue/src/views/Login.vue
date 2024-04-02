<template>
    <div id='building'>
        <div class="login-container">
        <el-form :model="form"
                 status-icon
                 ref="ruleForm2"
                 label-position="top"
                 class="login-page">
            <h3 class="title">系统登录</h3>
            <el-form-item prop="number" label="用户名" :rules="[
        {
          required: true,
          message: '请输入电话号码!',
          trigger: 'blur',
        }]">
                <el-input type="text"
                          v-model="form.number"
                          auto-complete="off"
                          placeholder="用户名"
                          :prefix-icon="Avatar"
                ></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码" :rules="[
        {
          required: true,
          message: '请输入密码!',
          trigger: 'blur',
        }]">
                <el-input type="password"
                          v-model="form.password"
                          auto-complete="off"
                          placeholder="密码"
                          :prefix-icon="Lock"
                          show-password

                ></el-input>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;" @click="handleSubmit">登录</el-button>
            </el-form-item>
            <el-row style="text-align: center; margin-top: -10px;;">
                <router-link to="/Register">
                    <el-link type="primary">注册</el-link>
                </router-link>
            </el-row>
        </el-form>
    </div>
    </div>
</template>

<script>
    import request from "../utils/request";
    import {ElMessage} from 'element-plus'
    import {Avatar} from "@element-plus/icons-vue"
    import {Lock} from "@element-plus/icons-vue"

    export default {
        components: {
            ElMessage
        },
        name: 'Login',
        data() {
            return {
                Avatar,
                Lock,
                logining: false,
                form: {},
                checked: false
            }
        },
        methods: {
            handleSubmit() {
                request.post("/user/login", this.form).then(res => {
                    if (res.code === '0') {
                        ElMessage({
                            message: "成功登陆!",
                            type: 'success',
                        })
                        sessionStorage.setItem("id", this.form.number)
                        console.log(this.form.number);
                        if(this.form.number === "admin"){
                            this.$router.push('/manager')
                        }
                        else{
                            this.$router.push('/home')
                        }
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            }
        }
    };
</script>

<style scoped>
    .login-container {
        width: 100%;
        height: 100%;
    }

    .login-page {
        -webkit-border-radius: 5px;
        border-radius: 5px;
        margin: 180px auto;
        width: 350px;
        padding: 35px 35px 15px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    #building{
        background:url("../image/train.gif");
        width:100%;
        height:100%;
        position:fixed;
        background-size:100% 100%;
    }
</style>
