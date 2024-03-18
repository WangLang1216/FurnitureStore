<template>
  <!-- 用户信息 -->
  <div class="user">
    <h4>用户信息</h4>
    <!-- 筛选 -->
    <div class="screen">
      <el-row :gutter="10">
        <el-col :span="14">
          <span>用户名</span>
          <el-input size="small" v-model="nickname" placeholder="根据用户名模糊查询顾客信息" :clearable="true"></el-input>
          <span style="margin-left: 2%;">手机号</span>
          <el-input size="small" v-model="phone" placeholder="根据手机号模糊查询顾客信息" :clearable="true"></el-input>
          <el-button size="small" @click="queryUserInfo()" type="primary">查询</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 新增、禁用、删除 -->
    <el-row :gutter="70" style="margin-top: 0.3%;">
      <el-col :span="1" v-if="isAdmin"><el-button type="success" @click="dialogVisible = true" size="mini" plain>新增</el-button></el-col>
      <el-col :span="1"><el-button type="danger" @click="deleteUser()" size="mini" plain>删除</el-button></el-col>
      <el-col :span="10"  v-if="isAdmin">
        <el-switch style="display: block;margin-top: 1%;" v-model="switchUser" active-color="#13ce66" inactive-color="#ff4949" active-text="管理用户信息" inactive-text="顾客信息"></el-switch>
      </el-col>
    </el-row>
    <!-- 数据 -->
    <div class="data">
      <el-table
        :data="switchUser ? adminInfo : userInfo"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
        max-height="500">
        <el-table-column
          type="selection"
          width="55">
          </el-table-column>
        <el-table-column
          prop="name"
          :label="switchUser ? '账号' : '昵称'"
          width="200">
        </el-table-column>
        <el-table-column v-if="switchUser"
          prop="role"
          label="角色"
          width="180">
          <template slot-scope="scope">
            {{ scope.row.role == 0 ? '普通用户' : '超级管理员' }}
          </template>
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="state"
          label="状态"
          width="180"
          :filters="[{ text: '正常', value: true }, { text: '异常', value: false }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.state ? 'success' : 'danger'"
              disable-transitions>{{scope.row.state ? '正常' : '异常'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建日期"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.state" v-if="scope.row.role != 1" @change="disableUser(scope.row.userId, scope.row.state)" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 新增管理用户 -->
    <el-dialog
      :append-to-body="true"
      title="新增管理用户"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-row>
        <el-col :span="3">账号</el-col>
        <el-col :span="21" style="display: flex;">
          <el-input size="small" v-model="addAdminUser.account" placeholder="请输入账号" :clearable="true"></el-input>
        </el-col>
        <el-col style="margin-top: 1%;" :span="3">密码</el-col>
        <el-col style="margin-top: 1%;" :span="21">
         <el-input size="small" v-model="addAdminUser.password" placeholder="请输入密码" :clearable="true"></el-input>
        </el-col>
        <el-col style="margin-top: 1%;" :span="3">手机号</el-col>
        <el-col style="margin-top: 1%;" :span="21">
          <el-input size="small" v-model="addAdminUser.phone" placeholder="请输入手机号" :clearable="true"></el-input>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="addAdminUseInfo()">确 定</el-button>
      </span>
    </el-dialog>
    <span style="position: fixed;bottom: 2%;margin-left: 18%;color: #43b597;font-family: Arial;font-size: 12px;letter-spacing: 2px;">Design and Implementation of a Furniture Store System Based on WeChat Mini Program.</span>
  </div>
</template>

<script>
import { Message } from 'element-ui';
import { getUserInfo, addAdmin, delUser, updateUserState } from '../../request/api';
export default {
  name: 'User',
  data () {
    return {
      // 用户手机号
      phone: '',
      // 用户名称
      nickname: '',
      // 用户ID集合
      userIds: [],
      // 新增管理用户
      addAdminUser: {
        account: '',
        password: '',
        phone: '',
      },
      // 是否为管理员
      isAdmin: true,
      // 用户切换
      switchUser: false,
      // 新增切换
      dialogVisible: false,
      userInfo: [],
      adminInfo: []
    }
  },
  async mounted () {
    // 默认查询
    let queryVO = {};
    const res = await getUserInfo(0, queryVO);
    if(res.code === 200) {
      res.data.forEach(element => {
        if(element.sysType === 1) {
          this.adminInfo = element.userInfoBOS;
        } else {
          this.userInfo = element.userInfoBOS;
        }
      })
    }
  },
  methods: {
    // 查询
    async queryUserInfo() {
      let res;
      // 查询全部
      if(this.nickname == '' && this.phone == '') {
        Message({
          message: '当前为查询全部',
          type: 'info',
          duration: 2000
        });
        // 默认查询
        let queryVO = {};
        res = await getUserInfo(0, queryVO);
      }
      else if(this.nickname != '') {
        Message({
          message: '当前为用户昵称查询',
          type: 'info',
          duration: 2000
        });
        let queryVO = {};
        queryVO.filed = this.switchUser ? 'account' : 'nickname';
        queryVO.value = this.nickname;
        res = await getUserInfo(this.switchUser ? 1 : 2, queryVO);
      } else {
        Message({
          message: '当前为用户手机号查询',
          type: 'info',
          duration: 2000
        });
        let queryVO = {};
        queryVO.filed = 'phone';
        queryVO.value = this.phone;
        res = await getUserInfo(this.switchUser ? 1 : 2, queryVO);
      }
      if(res.code === 200) {
        res.data.forEach(element => {
          if(element.sysType === 1) {
            this.adminInfo = element.userInfoBOS;
          } else {
            this.userInfo = element.userInfoBOS;
          }
        })
      } else {
        Message({
          message: res.msg,
          type: 'error',
          duration: 2000
        });
      }
    },
    // 删除用户
    async deleteUser() {
      if(!this.switchUser) {
        return Message({
          message: '不可删除顾客信息',
          type: 'error',
          duration: 2000
        });
      }
      if(this.userIds.length == 0) {
        return Message({
          message: '未选择用户',
          type: 'error',
          duration: 2000
        });
      }
      const res = await delUser(this.userIds);
      if(res.code !== 200) {
        return Message({
          message: res.msg,
          type: 'error',
          duration: 2000
        });
      }
      Message({
        message: '删除成功',
        type: 'success',
        duration: 2000
      });
      // 默认查询
      let queryVO = {};
      const user = await getUserInfo(0, queryVO);
      if(user.code === 200) {
        user.data.forEach(element => {
          if(element.sysType === 1) {
            this.adminInfo = element.userInfoBOS;
          } else {
            this.userInfo = element.userInfoBOS;
          }
        })
      }
    },

    // 状态修改
    async disableUser(userId, state) {
      const res = await updateUserState([userId, state, this.switchUser]);
      if(res.code !== 200) {
        return Message({
          message: res.msg,
          type: 'error',
          duration: 2000
        });
      }
      Message({
        message: '修改成功',
        type: 'success',
        duration: 2000
      });
      // 默认查询
      let queryVO = {};
      const user = await getUserInfo(0, queryVO);
      if(user.code === 200) {
        user.data.forEach(element => {
          if(element.sysType === 1) {
            this.adminInfo = element.userInfoBOS;
          } else {
            this.userInfo = element.userInfoBOS;
          }
        })
      }
    },
    // 新增管理用户
    async addAdminUseInfo() {
      if(this.addAdminUser.account == '' || this.addAdminUser.password == '' || this.addAdminUser.phone == '') {
        return Message({
          message: '输入信息错误或缺失',
          type: 'error',
          duration: 2000
        });
      }
      const res = await addAdmin(this.addAdminUser);
      if(res.code !== 200) {
        return Message({
          message: res.msg,
          type: 'error',
          duration: 2000
        });
      }
      Message({
        message: '添加成功',
        type: 'success',
        duration: 2000
      });
      this.dialogVisible = false;
      // 默认查询
      let queryVO = {};
      const user = await getUserInfo(0, queryVO);
      if(user.code === 200) {
        user.data.forEach(element => {
          if(element.sysType === 1) {
            this.adminInfo = element.userInfoBOS;
          } else {
            this.userInfo = element.userInfoBOS;
          }
        })
      }
    },
    // 多选
    handleSelectionChange(val) {
      this.userIds = [];
      val.forEach(element => {
        this.userIds.push(element.userId)
      });
    },
    filterTag(value, row) {
      return row.state === value;
    },
  }
}
</script>

<style scoped>
  .user {
    width: 94%;
    margin: 1%;
    padding: 1% 2% 2% 2%;
    border-radius: 3px;
    background: white;
  }
  .screen>div>div {
    display: flex;
    line-height: 50px;
  }
  .screen>div>div .el-input, .screen>div>div .el-select {
    width: 30%;
    margin-left: 2%;
  }
  .screen>div>div .el-button {
    width: 70px;
    height: 31px;
    margin-left: 1%;
    margin-top: 1.2%;
  }
</style>