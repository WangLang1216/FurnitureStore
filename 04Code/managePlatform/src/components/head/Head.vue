<template>
  <!-- 头部 -->
  <div class="head">
    <div>
      <i style="font-size: 25px;margin-top: 2.6%;" class="el-icon-eleme"></i>
      <h3>&nbsp;家具店铺管理平台</h3>
    </div>
    <div>
      <el-dropdown placement="top" @command="logout">
        <span class="el-dropdown-link">{{ username }}</span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { Message } from 'element-ui';
import { logout } from '../../request/api';
export default {
  name: 'Head',
  data () {
    return {
      username: 'admin'
    }
  },
  methods: {
    // 退出登录
    async logout() {
      const res = await logout();
      if(res.code !== 200) return;
      localStorage.clear();
      console.log(localStorage.getItem("accessToken"));
      Message({
        message: '退出成功',
        type: 'success',
        duration: 2000
      });
      this.$router.push({name: 'login'});
    }
  }
}
</script>

<style scoped>
  .head {
    width: 100%;
    background: #ffffff;
    display: flex;
  }
  .head>div {
    flex: 1;
  }
  .head>div:first-child {
    padding-left: 0.5%;
    display: flex;
  }
  .head>div:last-child {
    text-align: right;
    padding-right: 5%;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>