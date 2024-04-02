<template>
<div style="padding-left: 20px;padding-right: 20px">
    <div style="margin:10px 0">
        <el-form :model="form" label-position="left" style="width: 80%">
            <el-row>
                <el-col style="padding-right: 30px" span="8">
                    <el-form-item prop="src" label="出发地" :rules="[
                    {
                      required: true,
                      message: '请输入出发地!',
                      trigger: 'blur',
                    }]">
                        <el-input type="text" v-model="form.src" auto-complete="off" placeholder="出发地"></el-input>
                    </el-form-item>
                </el-col>
                <el-col style="padding-right: 30px" span="8">
                    <el-form-item prop="dst" label="目的地" :rules="[
                    {
                      required: true,
                      message: '请输入目的地!',
                      trigger: 'blur',
                    }]">
                        <el-input type="text" v-model="form.dst" auto-complete="off" placeholder="目的地"></el-input>
                    </el-form-item>
                </el-col>
                <el-col span="8">
                    <el-form-item prop="srcTime" :rules="[
                    {
                      required: true,
                      message: '请输入出发时间!',
                      trigger: 'blur',
                    }]">
                        <el-date-picker v-model="form.srcTime" type="date" placeholder="Pick a date" style="width: 100%" value-format="YYYY-MM-DD"></el-date-picker>
                    </el-form-item>

                </el-col>
            </el-row>

            <el-form-item style="width:50%;">
                <el-button type="primary" style="width:100%;" @click="handleSearch">查询</el-button>
            </el-form-item>
        </el-form>
    </div>
    <el-divider></el-divider>
    <div>
        <el-table :data="tableData" style="width: 100%" stripe>
            <el-table-column prop="checi" label="车次" />
            <el-table-column prop="src" label="出发站" />
            <el-table-column prop="srcTime" label="出发时间" />
            <el-table-column prop="dst" label="目的站" />
            <el-table-column prop="dstTime" label="抵达时间" />
            <el-table-column prop="trainType" label="类型" />
            <!--                <el-table-column prop="seatNumber" label="剩余座位数量"/>-->
            <!--                <el-table-column prop="price" label="价格"/>-->
            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <el-button type="text" size="small" @click="handleClick(scope.row)">购票</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog v-model="dialogVisible" title="购票信息填写" width="30%">

            <el-form ref="form" :model="ticketForm" status-icon label-position="top">
                <el-form-item prop="seatType" label="座位类型" :rules="[
                    {
                      required: true,
                      message: '请选择座位类型!',
                      trigger: 'blur',
                    }]">
                    <el-radio-group size="large" v-model="radio1" @change="getRestBySeatType">
                        <el-radio-button label="一等座"></el-radio-button>
                        <el-radio-button label="二等座"></el-radio-button>
                        <el-radio-button label="商务座"></el-radio-button>
                        <el-radio-button label="软卧"></el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="carriageNumber" :rules="[
                    {
                      required: true,
                      message: '请输入车厢号!',
                      trigger: 'blur',
                    }]">
                    <el-input v-model.number="ticketForm.carriageNumber" placeholder="车厢号"></el-input>
                </el-form-item>
                <el-form-item prop="seatNum" :rules="[
                    {
                      required: true,
                      message: '请输入座位号!',
                      trigger: 'blur',
                    }]">
                    <el-input v-model="ticketForm.seatNum" placeholder="座位号"></el-input>
                </el-form-item>
                <el-form-item prop="seatType" label="票类型" :rules="[
                    {
                      required: true,
                      message: '请选择座位类型!',
                      trigger: 'blur',
                    }]">
                    <el-radio-group v-model="radio2" size="large" @change="getPrice">
                        <el-radio-button label="全票"></el-radio-button>
                        <el-radio-button label="学生票"></el-radio-button>
                        <el-radio-button label="儿童票"></el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <div style="padding-top: 15px">
                余票数:
                <el-input readonly v-model.number="restNumber"></el-input>
            </div>
            <div style="padding-top: 15px">
                票价:
                <el-input v-model.number="price" readonly></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="handleCancel">取消</el-button>
                    <el-button type="primary" @click="handleBuy" :disabled="noTicket">购买</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
    <div class="demo-pagination-block">
        <el-pagination v-model:currentPage="form.currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="form.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>
    </div>
</div>
</template>

<script>
// @ is an alias to /src
import request from "../utils/request";
import {
    ElMessage
} from 'element-plus'

export default{
    name: 'Home',
    components: {
        ElMessage
    },
    created() {
        this.intercep()
    },
    data() {

        return {
            radio1: null,
            radio2: "全票",
            price1: 0,
            restNumber: 0,
            noTicket: true,
            dialogVisible: false,
            total: 0,
            price: 0,
            tableData: [],
            ticketForm: {
                seatType: '',
                price: 0,

            },
            form: {
                currentPage: 1,
                pageSize: 5,
            },
            tempForm: {}
        }
    },
    methods: {
        intercep() {
            request.get("/ticketManager/interceptor")
        },
        load() {
            request.post("/ticketManager", this.form).then(res => {
                console.log(this.form)
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
            this.form.pageSize = pagesize
            this.load()
        },
        handleSearch() {
            this.load()
        },
        handleClick(row) {
            console.log(this.tableData)
            this.dialogVisible = true
            console.log(row)
            this.ticketForm = row
            console.log(this.ticketForm)
        },
        getRestBySeatType() {
            console.log(this.form.checi)
            console.log(this.radio1)
            this.ticketForm.seatType = this.radio1
            request.post("/ticketManager/getRestBySeatType", this.ticketForm).then(res => {
                if (res.code === '0') {
                    ElMessage({
                        message: "成功",
                        type: 'success',
                    })
                    this.restNumber = res.data.seatNumber
                    this.price = res.data.price
                    this.price1 = this.price / 2
                    this.noTicket = false
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'error',
                    })
                    this.restNumber = 0
                    this.price = 0
                    this.price1 = 0
                    this.noTicket = true
                }
                console.log(res)

            })
        },
        getPrice() {
            if (this.radio2 === "学生票" || this.radio2 === "儿童票") {
                this.price = this.price1
            } else {
                this.price = this.price1 * 2
            }
        },
        handleBuy() {
            this.ticketForm.seatType = this.radio1
            this.ticketForm.ticketType = this.radio2
            console.log(this.ticketForm)
            this.ticketForm.idCard = sessionStorage.getItem("id")
            request.post("/ticketManager/buy", this.ticketForm).then(res => {
                if (res.code === '0') {
                    ElMessage({
                        message: "成功",
                        type: 'success',
                    })
                    this.ticketForm = {}
                    this.dialogVisible = false
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'error',
                    })
                }
                console.log(res)

            })
        },
        handleCancel() {
            this.ticketForm = {}
            this.dialogVisible = false
        }
    }
}
</script>
