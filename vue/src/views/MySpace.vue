<template>
    <div id="building">
        <div class="login-container" >
            <el-form
                    ref="form"
                    :model="form"
                    status-icon
                    label-position="top"
                    class="login-page"
            >
                <h3 class="title">实名信息查询/修改</h3>
                <el-form-item prop="number" label="电话" :rules="[
                {
                  required: true,
                  message: '!',
                  trigger: 'blur',
                }]">
                    <el-input v-model="form.number" placeholder="电话" readonly></el-input>
                </el-form-item>

                <el-form-item prop="name" label="姓名" :rules="[
        {
          required: true,
          message: '请输入姓名!',
          trigger: 'blur',
        }]">
                    <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item prop="age" label="年龄" :rules="[
        {
          required: true,
          message: '请输入年龄!',
          trigger: 'blur',
        }]">
                    <el-input v-model.number="form.age" label="年龄" placeholder="年龄"></el-input>
                </el-form-item>
                <el-form-item prop="sex" label="性别" :rules="[
        {
          required: true,
          message: '请选择性别!',
          trigger: 'blur',
        }]">
                    <el-radio v-model="form.sex" label="男">男</el-radio>
                    <el-radio v-model="form.sex" label="女">女</el-radio>
                </el-form-item>
                <el-button type="primary" @click="submitForm">提交</el-button>
                <el-row class="update">
                    <el-button  type="primary" @click="handleupdate">修改密码</el-button>
                </el-row>
            </el-form>
            <el-dialog v-model="dialogVisible" title="修改密码" width="30%">
                <el-form ref="form" >
                    <el-form-item prop="Oldpassword" :rules="[
                    {
                      required: true,
                      message: '请输入原密码!',
                      trigger: 'blur',
                    }]">
                    <el-input v-model="user.Oldpassword" placeholder="原密码"></el-input>
                </el-form-item>
                    <el-form-item prop="Newpassword" :rules="[
                    {
                      required: true,
                      message: '请输入新密码!',
                      trigger: 'blur',
                    }]">
                    <el-input v-model="user.Newpassword" placeholder="新密码"></el-input>
                </el-form-item>
                </el-form>
                <template #footer>
                <span class="dialog-footer">
                    <el-button @click="handleCancel">取消</el-button>
                    <el-button type="primary" @click="handleChange">修改</el-button>
                </span>
            </template>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";
    import {ElMessage} from "element-plus";
    import {Avatar} from '@element-plus/icons-vue'
    //这个的注册要在data return里不可以注册为组件

    export default {
        components:{

        },
        data() {
            return {
                Avatar,
                form:{
                    number:'',
                    name:'',
                    age:'',
                    sex:'',

                },
                dialogVisible:false,
                user:{
                    Oldpassword:'',
                    Newpassword:'',
                }
            }
        },
        created() {
            this.load()
        },
        methods: {
            load(){
                console.log(1)
                request.get("/user",{
                    params:{
                        num:sessionStorage.getItem("id")
                    }
                }).then(res => {
                    console.log(res)
                    if(res.code === '0'){
                        ElMessage({
                            message: "成功查询!",
                            type: 'success',
                        })
                        this.form = res.data
                    }
                    else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            },
           submitForm() {
                request.post("/user/update",this.form).then(res => {
                    if(res.code === '0'){
                        ElMessage({
                            message: "成功实名制!",
                            type: 'success',
                        })
                    }
                    else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            },
            handleupdate(){
                this.dialogVisible = true;
            },
            handleCancel(){
                this.user = {};
                this.dialogVisible = false;
            },
            handleChange(){
                console.log(this.user.Newpassword);
                console.log(this.form.number);
                request.get("/user/alterPassword", {
                    params:{
                        number:this.form.number,
                        password:this.user.Newpassword,
                    }
                }).then(res => {
                    if (res.code === '0') {
                        ElMessage({
                            message: "修改密码成功!",
                            type: 'success',
                        })
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            },
        },
    }
</script>

<style scoped>
    .login-container {
        width: 100%;
        height: 100%;
    }
    .login-page {
        -webkit-border-radius: 5px;
        border-radius: 5px;
        margin: 30px auto;
        width: 350px;
        padding: 35px 35px 15px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    .update{
        display: inline;
        float: left;
        margin-inline-end: 5%;
        justify-content: flex-end;
    }
    .el-button{
        margin-left: 30px;
    }
    #building{
        background:url("../image/register.gif");
        width:100%;
        height:100%;
        position:fixed;
        background-size:100% 100%;
    }
</style>
