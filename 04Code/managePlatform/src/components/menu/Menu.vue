<template>
  <!-- 右侧菜单 -->
  <div class="menu">
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" unique-opened="true" router="true">
      <el-menu-item index="/home/home-page">
        <i class="el-icon-s-data"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-s-shop"></i>
          <span slot="title">产品管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/home/add-product">新增/修改</el-menu-item>
          <el-menu-item index="1-2">查询信息</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="3">
        <i class="el-icon-s-goods"></i>
        <span slot="title">订单管理</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-s-tools"></i>
        <span slot="title">规则管理</span>
      </el-menu-item>
      <el-menu-item index="5">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Menu',
  data () {
    return {
      isAdmin: true,
    }
  }
}
</script>

<style scoped>
  .menu .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
</style>