<template >
    <el-container class="homecontainer">
      <el-header >
          <div class="div1">12306购票助手管理系统</div>
          <div class="div2">
            <div class="dropdown rightbar">
                <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    下拉菜单
                </button>
                <ul class="dropdown-menu">
                <li class="dropdown-item" @click="this.$router.push('/MySpace')">个人信息</li>
                <li class="dropdown-item" @click="handlelogout">退出登录</li>
                </ul>
            </div>
          </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
            <el-menu default-active="1" class="el-menu-vertical-demo">
              <el-menu-item index="1" @click="Changepage">
                <el-icon><icon-menu /></el-icon>
                <span slot="title">列车管理</span>
              </el-menu-item>
              <el-menu-item index="2" @click="GetUser">
                <el-icon><document /></el-icon>
                <span slot="title">用户管理</span>
              </el-menu-item>
              <el-menu-item index="3" @click="restore">
                <el-icon><setting /></el-icon>
                <span slot="title">故障恢复</span>
              </el-menu-item>
              <el-menu-item index="3" @click="backup">
                <el-icon><setting /></el-icon>
                <span slot="title">备份</span>
              </el-menu-item>
            </el-menu>
        </el-aside>
        <el-container>
          <el-main  height="300px" >
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentName }}</el-breadcrumb-item>
            </el-breadcrumb>
            <br>
            <div v-if="seen">
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
                <el-button type="primary" style="width:100%;" @click="GetTrain">查询</el-button>
                <br>
                <el-button type="primary" class="btn-manage" style="width:100%;" @click="HandleInsert">添加</el-button>
            </el-form-item>
        </el-form>
    </div>
    <el-divider></el-divider>
            <div>
              <el-table :data="tableData" style="width: 100%" stripe>
              <el-table-column prop="trainNumber" label="列车编号" />
              <el-table-column prop="checi" label="车次" />
              <el-table-column prop="src" label="出发站" />
              <el-table-column prop="srcTime" label="出发时间" />
              <el-table-column prop="dst" label="目的站" />
              <el-table-column prop="dstTime" label="抵达时间" />
              <el-table-column prop="seatType" label="座位类型" />
              <el-table-column prop="trainType" label="列车类型" />
              <el-table-column prop="seatNumber" label="座位余量"/>
              <el-table-column prop="price" label="价格"/>
              <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <el-button type="text" size="small" @click="handleClick(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        </div>
        <el-dialog v-model="dialogVisible" title="列车信息添加" width="30%">
          <el-form ref="form" :model="trainForm" status-icon label-position="top">
            <el-form-item prop="trainNumber" :rules="[
              {
                required: true,
                message: '请选择列车编号!',
                trigger: 'blur',
              }]">
            <el-input v-model="trainForm.trainNumber" placeholder="列车编号"></el-input>
          </el-form-item>
          <el-form-item prop="checi" :rules="[
              {
                required: true,
                message: '请输入车次编号!',
                trigger: 'blur',
              }]">
            <el-input v-model="trainForm.checi" placeholder="车次编号"></el-input>
          </el-form-item>
          <el-form-item prop="src" :rules="[
            {
              required: true,
              message: '请输入出发站!',
              trigger: 'blur',
            }]">
            <el-input v-model="trainForm.src" placeholder="出发站"></el-input>
          </el-form-item>
          <el-form-item prop="srcTime" :rules="[
              {
                required: true,
                message: '请输入出发时间!',
                trigger: 'blur',
              }]">
              <el-date-picker v-model="trainForm.srcTime" type="datetime" placeholder="出发时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
          </el-form-item>
          <el-form-item prop="dst" :rules="[
            {
              required: true,
              message: '请输入到达站!',
              trigger: 'blur',
            }]">
            <el-input v-model="trainForm.dst" placeholder="到达站"></el-input>
          </el-form-item>
          <el-form-item prop="dstTime" :rules="[
              {
                required: true,
                message: '请输入到达时间!',
                trigger: 'blur',
              }]">
              <el-date-picker v-model="trainForm.dstTime" type="datetime" placeholder="到达时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
          </el-form-item>
          <el-form-item prop="seatType" label="座位类型" :rules="[
            {
              required: true,
              message: '请选择座位类型!',
              trigger: 'blur',
            }]">
            <el-radio-group v-model="radio1" size="large" @change="getPrice">
              <el-radio-button label="一等座"></el-radio-button>
              <el-radio-button label="二等座"></el-radio-button>
              <el-radio-button label="商务座"></el-radio-button>
              <el-radio-button label="软卧"></el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="trainType" label="列车类型" :rules="[
            {
              required: true,
              message: '请选择列车类型!',
              trigger: 'blur',
            }]">
            <el-radio-group v-model="radio2" size="large" @change="getPrice">
              <el-radio-button label="高铁"></el-radio-button>
              <el-radio-button label="动车"></el-radio-button>
              <el-radio-button label="直达"></el-radio-button>
              <el-radio-button label="特快"></el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="seatNumber" :rules="[
            {
              required: true,
              message: '请输入总票数!',
              trigger: 'blur',
            }]">
            <el-input v-model="trainForm.seatNumber" placeholder="总票数"></el-input>
          </el-form-item>
          <el-form-item prop="price" :rules="[
            {
              required: true,
              message: '请输入票价!',
              trigger: 'blur',
            }]">
            <el-input v-model="trainForm.price" placeholder="票价"></el-input>
          </el-form-item>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="primary" @click="Insert" :disabled="noTicket">添加</el-button>
      </span>
    </template>
  </el-dialog>
        <div class="demo-pagination-block">
          <el-pagination v-model:currentPage="form.currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="form.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
          </el-pagination>
        </div>
            </div>
            <div v-else>
              <el-divider></el-divider>
            <div>
              <el-table :data="UserData" style="width: 100%" stripe>
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="idCard" label="身份证号" />
              <el-table-column prop="age" label="年龄" />
              <el-table-column prop="sex" label="性别" />
              <el-table-column prop="number" label="电话号码" />
              <el-table-column prop="password" label="密码" />
        </el-table>
        </div>
            </div>
          </el-main>
          <el-footer height="60px">2023年数据库系统原理课程设计</el-footer>
        </el-container>
      </el-container>
    </el-container>
    </template>
     
    <script>
    import {Document,Menu as IconMenu,Setting,} from "@element-plus/icons";
    import request from "../utils/request";
    import {ElMessage} from 'element-plus'
      export default {
        name:'Manager',
        components:{
          Document,
          IconMenu,
          Setting,
          ElMessage,
        },
        methods: {
          Insert(){
            this.trainForm.seatType = this.radio1
            this.trainForm.trainType = this.radio2
            console.log(this.trainForm)
            request.post("/ticketManager/insert",this.trainForm).then(res => {
                if(res.code === '0'){
                      ElMessage({
                        message: "成功添加!",
                        type: 'success',
                })
                  this.trainForm = {}
                  this.dialogVisible = false
              }else {
                  ElMessage({
                  message: res.msg,
                  type: 'error',
                })
              }
            });
          },
          handleClick(row) {
            console.log(row);
          },
          GetTrain(){
            request.post("/ticketManager/manageTrain", this.form).then(res => {
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
          HandleInsert(){
            this.dialogVisible = true;
          },
          GetUser(){
            this.seen = false;
          },
          Changepage(){
            this.seen = true;
          },
          handlelogout(){
            sessionStorage.removeItem("id");
            sessionStorage.clear();
            this.$router.push('/login')
          },
          backup(){
            request.get("/ticketManager/backup").then(res =>{
              if(res.code === '0'){
                  ElMessage({
                        message: "备份成功!",
                        type: 'success',
                    })
              }else{
                ElMessage({
                        message: res.msg,
                        type: 'error',
                    })
              }
            });
          },
          restore(){
            request.get("/ticketManager/restore").then(res =>{
              if(res.code === '0'){
                  ElMessage({
                        message: "恢复成功!",
                        type: 'success',
                    })
              }else{
                ElMessage({
                        message: res.msg,
                        type: 'error',
                    })
              }
            });
          },
          handleClick(row) {
            console.log(this.tableData)
            console.log(row)
            request.get("/ticketManager/delete",{
              params:{
                Checi:row.checi,
                TrainNumber:row.trainNumber,
              },
            }).then(res =>{
              if (res.code === '0') {
                        ElMessage({
                            message: "删除成功!",
                            type: 'success',
                        })
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error',
                        })
                    }
            });
          },
          handleCancel() {
            this.ticketForm = {}
            this.dialogVisible = false
          },
        },
        data() { 
          return {
            radio1: null,
            radio2: null,
            price1: 0,
            Chei:"",
            TrainNumber:"",
            currentName : "123",
            form: {
                currentPage: 1,
                pageSize: 5,
            },
            total: 0,
            tableData:[],
            seen:true,
            UserData:[],
            dialogVisible:false,
            trainForm: {
                trainType:'',
                seatType: '',
                price: 0,
            },
          }
        }
      }
    </script>
     
    <style scoped>
      .homecontainer{
        height: 763px;
        width: 100%;
        
      }
      .head{
        display: flex;
        flex-direction: row;
      }
      .div1{
        text-align: left;
        margin-top: 25px;
        width: 50%;
        float: left;
      }
      .div2{
        width: 50%;
        float: right; 
        font-size: 12px;
      }
      .rightbar{
        margin-top: 10px;
        float: right;
      }
      .el-header{
        background-color: #1b7cfbbb;
        color: #333;
        text-align: center;
        line-height: 10px;
      } 
      .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
      }
      
      .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
      }
      
      .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 10px;
      }
      .el-dropdown-link {
        cursor: pointer;
        color: #061423;
      }
      .el-icon-arrow-down {
        font-size: 12px;
      }
      .btn-manage{
        margin-top: 15px;
        font-size: 10px;
        float: left;
      }
    </style>