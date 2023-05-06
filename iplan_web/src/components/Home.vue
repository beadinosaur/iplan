<template>
  <basic-block title="MY PLAN">
    <el-row type="flex" justify="space-between">
      <el-col :span="20">
        <div class="calendar-box">
          <full-calendar ref="fullCalendar" style="height: 100%" :options="calendarOptions">
          </full-calendar>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="back-tip">
          <el-button class="back-home" type="primary" @click="backhome()">退出登录</el-button>
        </div>
        <div class="tag-tip">
          <el-tag size="small" effect="dark" style="height: 50px; width:100px; background-color: orangered;">进行中</el-tag>
        </div>
        <div class="tag-tip">
          <el-tag type="warning" effect="dark" style="height: 50px; width:100px; background-color: green;">已完成</el-tag>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="back-tip">
          <el-button class="pull-data" type="primary" @click="pullData()">同步邮箱</el-button>
        </div>
        <div class="tag-tip">
          <el-button class="bconfig-email1" type="primary" @click="configemail()">邮箱配置</el-button>
        </div>
      </el-col>
    </el-row>
    <!--add plan start-->
    <el-dialog title="会议新增" :visible.sync="dialogVisible" :before-close="close" width="45%">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px" size="small" class="demo-ruleForm">
        <el-form-item label="会议主题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="会议地点" prop="position">
          <el-input v-model="form.position"></el-input>
        </el-form-item>
        <el-form-item label="会议开始时间" required>
          <el-col :span="11">
            <el-form-item prop="startDate" style="margin-bottom: 0">
              <el-date-picker type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"
                v-model="form.startDate" style="width: 100%;">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="startTime" style="margin-bottom: 0">
              <el-time-select placeholder="选择时间" v-model="form.startTime" :picker-options="{
                start: '00:00',
                step: '00:30',
                end: '23:30 ',
              }" style="width: 100%;">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="会议结束时间" required>
          <el-col :span="11">
            <el-form-item prop="endDate" style="margin-bottom: 0">
              <el-date-picker type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"
                v-model="form.endDate" style="width: 100%;">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime" style="margin-bottom: 0">
              <el-time-select placeholder="选择时间" v-model="form.endTime" :picker-options="{
                start: '00:00',
                step: '00:30',
                end: '23:30 ',
                minTime: form.startTime
              }" style="width: 100%;">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="详情" prop="desc">
          <el-input type="textarea" v-model="form.content"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm('form')">取消</el-button>
          <el-button type="primary" @click="submitForm('form')">提交</el-button>
          <el-button class="delete-form" type="primary" @click="deleteForm('form')">删除</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--add plan end-->

    <!--email config start-->
    <el-dialog title="配置邮箱信息" :visible.sync="dialogVisible2" :before-close="close2" width="35%">
      <el-form :model="formEmail" :rules="rules1" ref="form" label-width="170px" size="small" class="demo-ruleForm">
        <el-form-item label="配置邮箱" prop="email">
          <el-input v-model="formEmail.email"></el-input>
        </el-form-item>
        <el-form-item label="邮箱密码" prop="password">
          <el-input v-model="formEmail.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="同步开始时间" required>
          <el-col :span="11">
            <el-form-item prop="startTime" style="margin-bottom: 0">
              <el-date-picker type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"
                v-model="formEmail.startTime" style="width: 100%;" :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="过滤系统关键词" prop="keyWordT">
          <el-select v-model="formEmail.keyWordT" style="width:100%" clearable filterable placeholder="请选择过滤关键词">
            <el-option 
            v-for="item in WordTList" 
            :key="item.keyWordT" 
            :label="item.name" 
            :value="item.keyWordT">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="过滤自定义关键词" prop="keyWordS">
          <el-input v-model="formEmail.keyWordS"></el-input>
        </el-form-item>
        <el-form-item label="过滤邮箱" prop="keyEmail">
          <el-input v-model="formEmail.keyEmail"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-input v-model="formEmail.department"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="station">
          <el-input v-model="formEmail.station"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm2('form')">取消</el-button>
          <el-button type="primary" @click="submitForm2('form')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--email config end-->
  </basic-block>
</template>

<script>

import { addPlan } from '@/api/api.js';
import { selectPlanDataByTime } from '@/api/api.js';
import { deleteByPlanId } from '@/api/api.js';
import { pullEmail } from '@/api/api.js';
import { pullConference } from '@/api/api.js';
import { emailConfig } from '@/api/api.js';
import _ from 'lodash';
// import calendar
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { getToken } from '../utils/setToken.js'
// import js data
import array from '@/utils/home'

export default {
  name: 'Home',
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  data() {
    return {
      activeNum: 0,
      dialogVisible: false,
      meetingRoomList: [],
      subList: [],
      WordTList: [
        {
          keyWordT: 1,
          name: '特卖'
        }, {
          keyWordT: 2,
          name: '招商'
        }, {
          keyWordT: 3,
          name: '广告'
        }
      ],
      form: {
        title: '',
        userName: '',
        sender: '',
        receiver: '',
        position: '',
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        content: '',
        planId: ''
      },
      formsearchday: {
        userName: '',
        time: ''
      },
      formDelete: {
        userName: '',
        planId: ''
      },

      formEmail: {
        userName: '',
        email: "",
        password: "",
        keyWordS: "",
        keyWordT: "",
        keyEmail: "",
        startTime: "",
        department: "",
        station: ""
      },
      rules: {
        title: [
          { required: true, message: '请输入会议主题', trigger: 'blur' }
        ],
        position: [
          { required: true, message: '请填写会议地址', trigger: 'change' }
        ],
        startDate: [
          { required: true, message: '请选择开始日期', trigger: 'change' }
        ],
        startTime: [
          { required: true, message: '请选择开始时间', trigger: 'change' }
        ],
        endDate: [
          { required: true, message: '请选择结束日期', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择结束时间', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请填写会议详情', trigger: 'blur' }
        ],
      },
      rules1: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入邮箱密码', trigger: 'blur' }
        ],
      },

      //Get the date before today
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      //
      dialogVisible2: false,

      calendarOptions: {
        //   timeGridPlugin
        height: 700,
        plugins: [dayGridPlugin, interactionPlugin],
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,dayGridWeek,dayGrid",
          // right: 'agendaWeek,dayGridWeek,dayGrid'
        },
        buttonText: {
          // set button
          today: "今天",
          month: "月",
          week: "周",
          dayGrid: "天",
        },
        // allDaySlot: false,
        editable: true,
        selectable: true,
        navLinks: true,
        // displayEventEnd: true
        initialView: "dayGridMonth",
        dateClick: this.handleDateClick,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        select: this.handleDateSelect,
        // timezone
        // set plan
        events: [

        ],
        eventColor: "#f08f00",
        locale: "zh-cn",
        weekNumberCalculation: "ISO",
        customButtons: {
          prev: { // this overrides the prev button
            text: "PREV",
            click: () => {
              this.prev();
            }
          },
          next: { // this overrides the next button
            text: "NEXT",
            click: () => {
              this.next();
            }
          },
          today: {
            text: "今天",
            click: () => {
              this.today();
            }
          }
        }
      },
    }
  },
  mounted() {
    // get user information
    this.calendarApi = this.$refs.fullCalendar.getApi();
    this.getReservationList();
  },
  methods: {

    //email config
    configemail() {
      this.dialogVisible2 = true;
    },
    close2() {
      this.dialogVisible2 = false;
      this.$refs['form'].resetFields();
    },
    // submit email infomation
    submitForm2(formEmail) {
      this.formEmail.userName = getToken('userName');
      this.formEmail.startTime = `${this.formEmail.startTime} 00:00:00`;
      this.$refs[formEmail].validate((valid) => {

        emailConfig(this.formEmail).then(res => {

          if (res.status === 200) {
            alert("提交成功!");
            this.dialogVisible2 = false;
          } else {
            return false;
          }
        });

      });
    },


    resetForm2(formEmail) {
      this.dialogVisible2 = false;
      this.$refs[formEmail].resetFields();
    },


    // submit plan data
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.form.startTime = `${this.form.startDate} ${this.form.startTime}:00`;
          this.form.endTime = `${this.form.endDate} ${this.form.endTime}:00`;
          this.dialogVisible = false;
          this.form.userName = getToken('userName');
          addPlan(this.form).then(res => {
            if (res.status === 200) {
              this.getReservationList();
              alert("提交成功!");
            } else {
              alert("提交失败!");
            }
          }).catch((err) => {
            console.log(err);
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //delete plan
    deleteForm(form) {
      this.formDelete.planId = this.form.planId;
      this.formDelete.userName = this.form.userName;
      deleteByPlanId(this.formDelete).then((item) => {
        this.getReservationList();
        alert("删除成功!");
      });
    },

    //pull data
    pullData() {
      pullEmail();
      pullConference();
    },

    //search plandata
    getReservationList() {
      let newArr = [];
      let dateStr = new Date().toLocaleDateString().split('/').join('-');
      this.formsearchday.userName = getToken('userName');
      this.formsearchday.time = `${dateStr}`
      selectPlanDataByTime(this.formsearchday).then((item) => {
        this.subList = item.data.data;
        item.data.data.forEach((res) => {
          newArr.push({
            start: this.dealWithTime(res.startTime),
            end: this.addDate(this.dealWithTime(res.endTime), 1),
            color: "green",
            id: res.planId,
            title: `${this.getTitle(res.startTime, res.endTime)} ${res.title}`,
          })
        });
      });
      this.calendarOptions.events = newArr;
    },

    addDate(date, days) {
      var d = new Date(date);
      d.setDate(d.getDate() + days);
      var mon = (d.getMonth() + 1) < 10 ? "0" + (d.getMonth() + 1) : d.getMonth() + 1;
      let endD = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
      return `${d.getFullYear()}-${mon}-${endD}`;
    },
    // get title
    getTitle(date1, date2) {
      let start = date1.substring(11, 16);
      let end = date2.substring(11, 16);
      return `${start}~${end}`;
    },
    getShowTime(startTime, endTime) {
      this.form.startDate = this.dealWithTime(startTime);
      this.form.startTime = this.getHoursMin(startTime);
      this.form.endDate = this.dealWithTime(endTime);
      this.form.endTime = this.getHoursMin(endTime);
    },

    getHoursMin(value) {
      return value.substring(11, 16);
    },

    dealWithTime(date) {
      let newDate = /\d{4}-\d{1,2}-\d{1,2}/g.exec(date)[0];
      return newDate;
    },

    handleDateClick: function (arg) {
      this.$forceUpdate();
      this.dialogVisible = true;
    },

    handleEventClick(calEvent) {
      this.dialogVisible = true;
      let id = calEvent.event.id;
      let info = this.subList.filter((item) => {
        return item.planId == id
      });
      this.$nextTick(() => {
        this.form = _.cloneDeep(info[0]);

        this.getShowTime(this.form.startTime, this.form.endTime);
      });
    },
    handleEvents(events) {
      console.log(events);
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
    },

    prev() {
      this.calendarApi.prev();
    },

    next() {
      this.calendarApi.next();
    },

    today() {
      this.calendarApi.today();
    },

    resetForm(form) {
      this.dialogVisible = false;
      this.$refs[form].resetFields();
    },
    backhome() {
      this.$router.push('/login')
    },

    close() {
      this.dialogVisible = false;
      this.$refs['form'].resetFields();
    },
  }
}
</script>
<style lang="scss" scoped>
@import "src/assets/css/home";
</style>
