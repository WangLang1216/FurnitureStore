<!--
 * @Author: 王狼 address
 * @Date: 2024-03-08 10:21:05
 * @LastEditors: 王狼 address
 * @LastEditTime: 2024-03-08 10:23:12
 * @FilePath: \managePlatform\src\components\order\Order.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <!-- 订单信息 -->
  <div class="order">
    <h4>订单信息</h4>
    <!-- 筛选 -->
    <div class="screen">
      <el-row :gutter="10">
        <el-col :span="14">
          <span>订单号</span>
          <el-input size="small" v-model="orderId" placeholder="根据订单号模糊查询"></el-input>
          <el-button size="small" @click="queryOrderInfo()" type="primary">查询</el-button>
          <span style="margin-left: 2%;">状态</span>
          <el-select v-model="state" size="small" placeholder="请选择状态">
            <el-option v-for="item in orderState" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
          <el-button size="small" @click="updateOrderState()" type="warning">修改</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 数据 -->
    <div class="data">
      <el-table
        :data="orderInfo"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        max-height="500">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="日期"
          sortable
          :show-overflow-tooltip="true"
          width="120">
        </el-table-column>
        <el-table-column
          prop="username"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="联系电话"
          width="120">
        </el-table-column>
        <el-table-column
          prop="productName"
          label="产品名称"
          :show-overflow-tooltip="true"
          width="150">
        </el-table-column>
        <el-table-column
          prop="productSize"
          label="产品尺寸"
          :show-overflow-tooltip="true"
          width="150">
        </el-table-column>
        <el-table-column
          prop="productColour"
          label="产品颜色"
          width="100">
        </el-table-column>
        <el-table-column
          prop="materialType"
          label="产品材质"
          width="100">
        </el-table-column>
        <el-table-column
          prop="price"
          label="单价"
          sortable
          width="80">
          <template slot-scope="scope">
            {{ scope.row.price }}元
          </template>
        </el-table-column>
        <el-table-column
          prop="quantity"
          label="数量"
          sortable
          width="80">
          <template slot-scope="scope">
            {{ scope.row.quantity }}件
          </template>
        </el-table-column>
        <el-table-column
          label="总价"
          sortable
          width="100">
          <template slot-scope="scope">
            {{ scope.row.price * scope.row.quantity }}元
          </template>
        </el-table-column>
        <el-table-column
          prop="state"
          label="状态"
          width="80"
          :filters="[{text: '已下单', value: 1}, {text: '已确定', value: 2}, {text: '已发货', value: 3}, {text: '已完成', value: 4}, {text: '售后中', value: -1} ]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="tagType(scope.row.state)"
              disable-transitions>{{ tagText(scope.row.state) }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        layout="prev, pager, next"
        @prev-click="prevInfo"
        @next-click="nextInfo"
        :total="total">
      </el-pagination>
    </div>
    <span style="position: fixed;bottom: 2%;margin-left: 18%;color: #43b597;font-family: Arial;font-size: 12px;letter-spacing: 2px;">Design and Implementation of a Furniture Store System Based on WeChat Mini Program.</span>
  </div>
</template>

<script>
import { Message } from 'element-ui';
export default {
  name: 'Order',
  data () {
    return {
      // 订单号
      orderId: '',
      // 状态
      state: '',
      // 选择订单信息
      orderIds: [],
      // 总数
      total: 20,
      // 订单信息
      orderInfo: [
        {orderId: "123", userId: '1231', username: '王狼', phone: '17345449129', productName: '123qsqsq', productSize: '1111w331',
         productColour: '1213131', materialType: '121313', quantity: 12, price: 1221, state: -1, createTime: '2020-12-16'},
        {orderId: "123", userId: '1231', username: '王狼', phone: '17345449129', productName: '123qsqsq', productSize: '1111w331',
         productColour: '1213131', materialType: '121313', quantity: 12, price: 1221, state: 1, createTime: '2020-12-16'},
        {orderId: "123", userId: '1231', username: '王狼', phone: '17345449129', productName: '123qsqsq', productSize: '1111w331',
         productColour: '1213131', materialType: '121313', quantity: 12, price: 1221, state: 2, createTime: '2020-12-16'},
        {orderId: "123", userId: '1231', username: '王狼', phone: '17345449129', productName: '123qsqsq', productSize: '1111w331',
         productColour: '1213131', materialType: '121313', quantity: 12, price: 1221, state: 3, createTime: '2020-12-16'},
        {orderId: "123", userId: '1231', username: '王狼', phone: '17345449129', productName: '123qsqsq', productSize: '1111w331',
         productColour: '1213131', materialType: '121313', quantity: 12, price: 1221, state: 4, createTime: '2020-12-16'},
      ],
      // 订单状态
      orderState: [
        {label: '已确定', value: 2}, {label: '已发货', value: 3}, {label: '已完成', value: 4} 
      ]
    }
  },
  methods: {
    // 查询
    queryOrderInfo() {

    },
    // 修改订单状态
    updateOrderState() {

    },
    // 上一页
    prevInfo(index) {
      console.log(index);
    },
    // 下一页
    nextInfo(index) {
      console.log(index);
    },
    // 多选
    handleSelectionChange(val) {
      this.orderIds = [];
      val.forEach(element => {
        this.orderIds.push(element.orderId)
      });
    },
    filterTag(value, row) {
      return row.state === value;
    },
    tagType(state) {
      if(state == 4) {
        return 'success'
      } else if(state == 1) {
        return 'primary'
      } else if(state == 2) {
        return 'info'
      } else if(state == 3) {
        return 'warning'
      }
      return 'danger'
    },
    tagText(state) {
      if(state == 4) {
        return '已完成'
      } else if(state == 1) {
        return '已下单'
      } else if(state == 2) {
        return '已确认'
      } else if(state == 3) {
        return '已发货'
      }
      return '售后中'
    }
  }
}
</script>

<style scoped>
  .order {
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
  .data {
    margin-top: 1%;
  }
  .el-pagination {
    text-align: right;
    margin-top: 0.5%;
  }
</style>