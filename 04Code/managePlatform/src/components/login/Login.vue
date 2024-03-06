<template>
  <!-- 登录页面 -->
  <div class="login">
    <div>
      <h3 class="title">家具店铺管理平台</h3>
      <!-- 账号和密码登录 -->
      <div class="account" v-show="loginType">
        <el-input placeholder="请输入账号" prefix-icon="el-icon-user-solid" v-model="account" clearable></el-input>
        <el-input placeholder="请输入密码" prefix-icon="el-icon-lock" v-model="password" @keyup.enter.native="login()" show-password clearable></el-input>
      </div>
      <!-- 手机号验证码登录 -->
      <div class="phone" v-show="loginType == false">
        <el-row :gutter="5">
          <el-col :span="24">
            <el-input placeholder="请输入手机号" prefix-icon="el-icon-user-solid" v-model="phone" clearable></el-input>
          </el-col>
          <el-col :span="17">
            <el-input placeholder="请输入验证码" prefix-icon="el-icon-mobile" v-model="code" @keyup.enter.native="login()" clearable></el-input>
          </el-col>
          <el-col :span="7">
            <el-button type="warning" plain @click="getPhoneCode()">获取验证码</el-button>
          </el-col>
        </el-row>
      </div>
      <a href="#" @click="loginType = !loginType">{{ loginType ? '使用手机号登录' : '使用账号登录' }}</a>
      <el-button type="primary" @click="login()">登录</el-button>
    </div>
    <span>Design and Implementation of a Furniture Store System Based on WeChat Mini Program</span>
  </div>
</template>

<script>
import { Message } from 'element-ui';
export default {
  name: 'Login',
  data () {
    return {
      account: '',
      password: '',
      phone: '',
      code: '',
      loginType: false,
    }
  },
  methods: {
    // 获取验证码
    getPhoneCode() {
      if(this.phone == '') {
        return Message({
          message: '请输入手机号',
          type: 'warning',
          duration: 2000
        });
      }


    },
    // 登录
    login() {
      if(this.loginType) {
        if(this.account == '' || this.password == '') {
          return Message({
            message: '请输入账号或密码',
            type: 'warning',
            duration: 2000
          });
        }
      } else {
        if(this.code == '') {
          return Message({
            message: '请输入验证码',
            type: 'warning',
            duration: 2000
          });
        }
      }
      this.$router.push({name: 'home'});
    }
    
  }
}
</script>

<style scoped>
  .login {
    position: fixed;  top: 0;  left: 0;  bottom: 0;  right: 0;  overflow: auto;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    background-image: url(https://vue.ruoyi.vip/static/img/login-background.f9f49138.jpg);
    background-size: cover;
  }
  .login>div {
    width: 400px;
    background: #fff;
    border-radius: 6px;
    padding: 2%;
  }
  .title {
    margin: 0 auto 30px auto;
    text-align: center;
    color: #707070;
    font-family: inherit;
    font-weight: 500;
    line-height: 1.1;
  }
  .account div:not(:first-child), .phone>div>div:not(:first-child) {
    margin-top: 5%;
  }
  .login>div a{
    display: block;
    color: blue;
    margin-top: 2%;
    text-decoration: underline;
  }
  .login>div>button {
    width: 100%;
    height: 35px;
    margin-top: 5%;
    line-height: 10px;
    letter-spacing: 2px;
    color: #fff;
    background-color: #1890ff;
    border-color: #1890ff;
  }
  .login>div>button:hover {
    background: #46a6ff;
    border-color: #46a6ff;
    color: #fff;
  }
  .login span {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 2px;
  }

</style>