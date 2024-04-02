<template>
    <div id="building">
        <div class="login-container">
        <el-form
                ref="ruleForm"
                :model="ruleForm"
                status-icon
                :rules="rules"
                label-position="top"
                class="login-page">
            <h3 class="title">用户注册</h3>
            <el-form-item prop="number" label="电话号码" :rules="[
        {
          required: true,
          message: '请输入电话号码!',
          trigger: 'blur',
        }]">
                <el-input v-model="ruleForm.number" placeholder="电话号码" :prefix-icon="Avatar"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input
                        v-model="ruleForm.password"
                        type="password"
                        autocomplete="off"
                        placeholder="密码"
                        :prefix-icon="Lock"
                ></el-input>
            </el-form-item>
            <el-form-item prop="checkPass" label="再次输入密码">
                <el-input
                        v-model="ruleForm.checkPass"
                        type="password"
                        autocomplete="off"
                        placeholder="再次输入密码"
                        :prefix-icon="Lock"
                ></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')"
                >确定</el-button
                >
                <el-button @click="resetForm('ruleForm')">返回登陆页</el-button>
            </el-form-item>
        </el-form>

        <el-dialog
                v-model="dialogVisible"
                title="实名制信息填写"
                width="30%"
        >

            <el-form
                    ref="form"
                    :model="form"
                    status-icon
                    :rules="rules"
                    label-position="top"
            >
                <el-form-item prop="name" label="姓名" :rules="[
        {
          required: true,
          message: '请输入姓名!',
          trigger: 'blur',
        }]">
                    <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item prop="idCard" label="身份证号" :rules="[
        {
          required: true,
          message: '请输入身份证号!',
          trigger: 'blur',
        }]">
                    <el-input v-model="form.idCard" placeholder="身份证号"></el-input>
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

            </el-form>
            <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit"
        >提交</el-button
        >
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
    import {Lock} from "@element-plus/icons-vue"
    //注册要在data return里不可以注册为组件

    export default {

        data() {
            //官方给出的一种判断方法，不如required:true
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码！'))
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass')
                    }
                    callback()
                }
            }
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码！'))
                } else if (value !== this.ruleForm.password) {
                    callback(new Error('两次密码输入不匹配！'))
                } else {
                    callback()
                }
            }
            return {
                Lock,
                Avatar,
                form:{},
                dialogVisible:false,
                checkPass: '',
                ruleForm: {
                    number: '',
                    password: '',
                },
                rules: {
                    password: [{ validator: validatePass, trigger: 'blur' }],
                    checkPass: [{ validator: validatePass2, trigger: 'blur' }],
                },
            }
        },
        methods: {
            handleSubmit() {
                this.form.number = this.ruleForm.number
                request.post("/user/realName",this.form).then(res => {
                    if(res.code === '0'){
                        ElMessage({
                            message: "成功实名制!",
                            type: 'success',
                        })
                        this.dialogVisible=false
                        this.$router.push("/home")
                    }
                    else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        request.post("/user/register",this.ruleForm).then(res => {
                            if(res.code === '0'){
                                ElMessage({
                                    message: "成功注册!",
                                    type: 'success',
                                })
                                sessionStorage.setItem("id",this.ruleForm.number)
                                this.dialogVisible=true
                            }
                            else {
                                ElMessage({
                                    message: res.msg,
                                    type: 'error',
                                })
                            }
                        })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            },
            resetForm() {
                this.$router.push('/login')
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
        margin: 180px auto;
        width: 350px;
        padding: 35px 35px 15px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    #building{
        background:url("../image/register.gif");
        width:100%;
        height:100%;
        position:fixed;
        background-size:100% 100%;
    }
</style>
