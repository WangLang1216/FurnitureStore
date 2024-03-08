<template>
  <!-- 规则信息 -->
  <div class="rule">
    <h4>参数设置</h4>
    <!-- 修改Token -->
    <div class="screen">
      <el-row :gutter="10">
        <el-col :span="24">
          <span>accessToken</span>
          <el-input size="small" v-model="accessToken" placeholder="请输入accessToken过期时间"></el-input>
          <span style="margin-left: 2%;">refreshToken</span>
          <el-input size="small" v-model="refreshToken" placeholder="请输入refreshToken过期时间"></el-input>
          <el-button size="small" @click="saveToken()" type="primary">保存</el-button>
        </el-col>
      </el-row>
    </div>
    <h4>小程序主页数据列表</h4>
    <!-- 新增 -->
    <div class="screen">
      <el-row :gutter="10">
        <el-col :span="19">
          <span>标题</span>
          <el-input size="small" v-model="info.title" placeholder="请输入标题"></el-input>
          <span style="margin-left: 2%;">品类</span>
          <el-autocomplete style="margin-left: 1%;margin-top: -0.2%;" class="inline-input" size="small" v-model="info.category" :fetch-suggestions="categorySearch" placeholder="请选择产品品类"></el-autocomplete>
          <span style="margin-left: 2%;">排序</span>
          <el-select v-model="info.showType" size="small" placeholder="请选择排序方式">
            <el-option v-for="item in [{label: '热度', value: true}, {label: '时间', value: false},]" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
          <el-button size="small" @click="saveInfo()" type="success">保存</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 数据 -->
    <div class="data">
      <el-table
        :data="indexData"
        style="width: 100%;"
        max-height="500">
        <el-table-column
          prop="title"
          label="标题"
          width="200">
        </el-table-column>
        <el-table-column
          prop="category"
          label="品类"
          width="180">
        </el-table-column>
        <el-table-column
          prop="showType"
          label="排序方式"
          width="200"
          :filters="[{ text: '热度', value: true }, { text: '时间', value: false }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.showType ? 'success' : 'warning'"
              disable-transitions>{{scope.row.showType ? '热度' : '时间'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <span style="position: fixed;bottom: 2%;margin-left: 18%;color: #43b597;font-family: Arial;font-size: 12px;letter-spacing: 2px;">Design and Implementation of a Furniture Store System Based on WeChat Mini Program.</span>
  </div>
</template>

<script>
import { Message } from 'element-ui';
export default {
  name: 'RuleData',
  data () {
    return {
      accessToken: '',
      refreshToken: '',
      // 品类快捷选择
      info: {title: '', category: '', showType: ''},
      categoryRes: [{ value: "沙发" },{ value: "茶几" },{ value: "电视柜" },{ value: "鞋柜" },{ value: "餐椅" },{ value: "餐边柜" },{ value: "床" },{ value: "床头柜" },{ value: "床垫" },{ value: "餐桌" },{ value: "休闲椅" }],
      indexData: [
        {title: '天华爆款沙发', category: '沙发', showType: true},
        {title: '天华爆款沙发', category: '沙发', showType: false},
        {title: '天华爆款沙发', category: '沙发', showType: true},
      ],
    }
  },
  methods: {
    // 保存Token信息
    saveToken() {
      if(this.accessToken == '' || this.refreshToken == '') {
        return Message({
          message: '输入信息错误或缺失',
          type: 'error',
          duration: 2000
        });
      }
    },
    // 新增信息
    saveInfo() {
      if(this.info.title == '' || this.info.category == '' || this.info.showType == '') {
        return Message({
          message: '输入信息错误或缺失',
          type: 'error',
          duration: 2000
        });
      }
    },
    // 删除
    handleDelete(index, row) {

    },
    filterTag(value, row) {
      return row.showType === value;
    },
    categorySearch(queryString, cb) {
      var restaurants = this.categoryRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
  }
}
</script>

<style scoped>
  .rule {
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
    width: 20%;
    margin-left: 2%;
  }
  .screen>div>div .el-button {
    width: 70px;
    height: 31px;
    margin-left: 1%;
    margin-top: 1%;
  }
</style>