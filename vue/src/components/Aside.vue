<template>
    <div>
        <el-menu
                default-active="home"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                style="min-height: 100vh;"
                router
        >
            <el-sub-menu index="1">
                <template #title>
                    <el-icon><location /></el-icon>
                    <span>票务中心</span>
                </template>
                <el-menu-item index="home">购票系统</el-menu-item>
                <el-menu-item index="myTicket">查看已购车票</el-menu-item>

            </el-sub-menu>
            <el-menu-item index="mySpace">
                <el-icon><icon-menu /></el-icon>
                <span>个人信息</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    import { defineComponent } from 'vue'
    import {
        Location,
        Document,
        Menu as IconMenu,
        Setting,
    } from '@element-plus/icons-vue'

    export default defineComponent({
        components: {
            Location,
            Document,
            Setting,
            IconMenu,
        },
        setup() {
            const handleOpen = (key, keyPath) => {
                console.log(key, keyPath)
            }
            const handleClose = (key, keyPath) => {
                console.log(key, keyPath)
            }
            return {
                handleOpen,
                handleClose,
            }
        },
    })
</script>

<style scoped>

</style>