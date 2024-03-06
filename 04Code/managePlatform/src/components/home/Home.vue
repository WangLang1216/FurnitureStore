<template>
  <div class="home">
    <el-containers>
      <el-header>
        <Head/>
      </el-header>
      <el-container>
        <el-aside width="201px">
          <Menu/>
        </el-aside>
        <el-container>
          <el-main>
            <router-view/>
          </el-main>
        </el-container>
      </el-container>
    </el-containers>
  </div>
</template>

<script>
import Menu from '../menu/Menu.vue'
import Head from '../head/Head.vue'

export default {
  name: 'Home',
  components: {
    Menu,
    Head
  },
}
</script>

<style scoped>
  *{
    margin : 0;
    padding : 0;
  }
  .home {
    position: fixed;
    width: 100%;
    height: 100%;
    background: red;
  }
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: white;
    line-height: 260px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  .el-container {
    height: 100%;
  }
  

</style>