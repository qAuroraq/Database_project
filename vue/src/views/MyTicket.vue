<template xmlns:el-col="http://www.w3.org/1999/html">
    <div style="padding-left: 20px;padding-right: 20px">
        <div>
            <el-table :data="tableData" style="width: 100%" stripe>
                <el-table-column prop="ticketId" label="票编号"/>
                <el-table-column prop="checi" label="车次"/>
                <el-table-column prop="src" label="出发站"/>
                <el-table-column prop="srcTime" label="出发时间"/>
                <el-table-column prop="dst" label="目的站"/>
                <el-table-column prop="dstTime" label="抵达时间"/>
                <el-table-column prop="trainType" label="类型"/>
                <el-table-column prop="carriageNumber" label = "车厢号"/>
                <el-table-column prop="seatNum" label="座位号"/>
                <el-table-column prop="seatType" label="座位类型"/>
                <!--                <el-table-column prop="price" label="价格"/>-->
                <el-table-column fixed="right" label="操作">
                    <template #default="scope">
                        <el-button type="text" size="small" @click="handleClick(scope.row)">退票</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="demo-pagination-block">
            <el-pagination
                    v-model:currentPage="currentPage"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script>
    // @ is an alias to /src
    import request from "../utils/request";
    import {ElMessage} from 'element-plus'
    export default {
        name: 'Home',

        components: {
            ElMessage
        },
        created() {
            this.load()
        },
        data() {
            return {
                total: 0,
                price:0,
                tableData: [],
                ticketForm:{
                    seatType:'',
                    price: 0,
                },
                currentPage: 1,
                pageSize: 5,
                tempForm:{}
            }
        },
        methods: {
            load() {
                request.get("/ticketManager/myTicket", {
                    params:{
                        currentPage:this.currentPage,
                        pageSize:this.pageSize,
                        id:sessionStorage.getItem("id")
                    }
                }).then(res => {
                    if (res.code === '0') {
                        ElMessage({
                            message: "查询成功!",
                            type: 'success',
                        })
                        console.log(res)
                        this.tableData = res.data.records
                        this.total = res.data.total
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                        this.tableData = 0
                    }
                })
            },
            handleCurrentChange() {
                this.load()
            },
            handleSizeChange(pagesize) {
                this.pageSize = pagesize
                this.load()
            },
            handleSearch() {
                this.load()
            },
            handleClick(row){
                console.log(row)
                this.ticketForm = row
                request.post("/ticketManager/return", this.ticketForm).then(res => {
                    if (res.code === '0') {
                        ElMessage({
                            message: "退票成功!",
                            type: 'success',
                        })
                        this.load()
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
                })
            },
        }
    }
</script>
