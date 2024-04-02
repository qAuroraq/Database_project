<template>
    <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #cccccc;display: flex">
        <div style="width:200px;padding-left: 50px;font-weight: bold;color: dodgerblue">12306购票小助手</div>
        <div style="flex: 1"></div>
        <div style="width:100px;padding-right:200px">
            <el-dropdown>
    <span class="el-dropdown-link">{{number}}
        <el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="this.$router.push('/MySpace')">个人信息</el-dropdown-item>
                        <el-dropdown-item @click="this.$router.push('/MyTicket')">订单查询</el-dropdown-item>
                        <el-dropdown-item divided @click="handleExit">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<!--官方文档有问题，不能加lang和setup，原理未知，并且要手动注册-->
<script>
    import {ArrowDown} from "@element-plus/icons-vue"
    export default {

        components:{
            ArrowDown
        },
        data() {
            return {
                number: sessionStorage.getItem("id")
            }
        },
        methods:{
            handleExit(){
                sessionStorage.setItem("id",null)
                this.$router.push("\login")
            }
        }
    }
</script>

<style scoped>

</style>