<!--
 * @Author: 王狼 address
 * @Date: 2024-03-07 11:33:57
 * @LastEditors: 王狼 address
 * @LastEditTime: 2024-03-07 20:18:56
 * @FilePath: \managePlatform\src\components\product\ProductInfo.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <!-- 产品信息 -->
  <div class="productInfo">
    <h4>产品信息</h4>
    <!-- 筛选 -->
    <div class="screen">
      <el-row :gutter="10">
        <el-col :span="14">
          <span>编号</span>
          <el-input size="small" v-model="identifier" placeholder="根据编号模糊查询"></el-input>
          <span style="margin-left: 2%;">名称</span>
          <el-input size="small" v-model="name" placeholder="根据名称模糊查询"></el-input>
          <el-button size="small" @click="queryProduct()" type="primary">查询</el-button>
          <el-button size="small" @click="deleteProduct()" type="danger">删除</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 数据 -->
    <div class="data">
      <el-table
        :data="productInfo"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        max-height="500">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          fixed
          prop="image"
          label="图片"
          width="160">
          <template slot-scope="scope">
            <!-- <img :src="scope.row.image" /> -->
            <img src="../../assets/images/sofa_1.jpg" style="width: 60%;" />
          </template>
        </el-table-column>
        <el-table-column
          prop="identifier"
          label="编号"
          width="170">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          :show-overflow-tooltip="true"
          width="170">
        </el-table-column>
        <el-table-column
          prop="category"
          label="品类"
          :filters="categoryRes"
          :filter-method="categoryResFilterHandler"
          filter-placement="bottom-end"
          width="80">
        </el-table-column>
        <el-table-column
          prop="space"
          label="空间"
          :filters="spaceRes"
          :filter-method="spaceResFilterHandler"
          filter-placement="bottom-end"
          width="80">
        </el-table-column>
        <el-table-column
          prop="style"
          label="风格"
          :filters="styleRes"
          :filter-method="styleResFilterHandler"
          filter-placement="bottom-end"
          width="100">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          sortable
          width="110">
          <template slot-scope="scope">
            {{ scope.row.price }}元
          </template>
        </el-table-column>
        <el-table-column
          prop="sold"
          label="已售"
          sortable
          width="100">
          <template slot-scope="scope">
            {{ scope.row.sold }}件
          </template>
        </el-table-column>
        <el-table-column
          prop="inventory"
          label="库存"
          sortable
          width="100">
          <template slot-scope="scope">
            {{ scope.row.inventory }}件
          </template>
        </el-table-column>
        <el-table-column
          prop="materialQuality"
          label="材质"
          width="100">
        </el-table-column>
        <el-table-column
          prop="filler"
          label="填充物"
          width="100">
        </el-table-column>
        <el-table-column
          prop="score"
          label="综合得分"
          sortable
          width="100">
          <template slot-scope="scope">
            {{ scope.row.score }}分
          </template>
        </el-table-column>
        <el-table-column
          prop="creationTime"
          label="创建日期"
          sortable
          :show-overflow-tooltip="true"
          width="100">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="110">
          <template slot-scope="scope">
            <el-button @click.native.prevent="updateProduct(scope.$index, productInfo)" type="text" size="small">编辑</el-button>
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
  name: 'ProductInfo',
  data () {
    return {
      // 编号
      identifier: '',
      // 名称
      name: '',
      // 总条数
      total: 30,
      // 当前页数
      page: 1,
      // 产品ID信息
      productId: [],
      // 产品数据
      productInfo: [
        {productId: '123', name: '现代意式网红北欧客厅转角简约组合直排纳米科技布沙发现代小户型奢华', introduce: '介绍', identifier: 'J140-X04-109-2072#',
        producer: '广东佛山', materialQuality: '实木框架', filler: '其他', piece: '1件', technology: '其他', installationMethod: '组装',
        style: '现代简约', category: '沙发', space: '客厅', term: '15天', service: '包送、包安装', price: 6730, sold: 310, heat: 520,
        collect: '5人', inventory: 30, score: 4.7, image: '../../assets/images/sofa_1.jpg', creationTime: '2022-12-16'},
        {productId: '123', name: '现代意式网红北欧客厅转角简约组合直排纳米科技布沙发现代小户型奢华', introduce: '介绍', identifier: 'J140-X04-109-2072#',
        producer: '广东佛山', materialQuality: '实木框架', filler: '其他', piece: '1件', technology: '其他', installationMethod: '组装',
        style: '现代简约', category: '沙发', space: '客厅', term: '15天', service: '包送、包安装', price: 6770, sold: 340, heat: 510,
        collect: '5人', inventory: 302, score: 2.7, image: '../../assets/images/sofa_1.jpg', creationTime: '2022-12-16'},
        {productId: '123', name: '现代意式网红北欧客厅转角简约组合直排纳米科技布沙发现代小户型奢华', introduce: '介绍', identifier: 'J140-X04-109-2072#',
        producer: '广东佛山', materialQuality: '实木框架', filler: '其他', piece: '1件', technology: '其他', installationMethod: '组装',
        style: '现代简约', category: '沙发', space: '客厅', term: '15天', service: '包送、包安装', price: 6710, sold: 360, heat: 500,
        collect: '5人', inventory: 301, score: 5.9, image: '../../images/sofa_1.jpg', creationTime: '2022-12-16'},
        {productId: '123', name: '现代意式网红北欧客厅转角简约组合直排纳米科技布沙发现代小户型奢华', introduce: '介绍', identifier: 'J140-X04-109-2072#',
        producer: '广东佛山', materialQuality: '实木框架', filler: '其他', piece: '1件', technology: '其他', installationMethod: '组装',
        style: '现代简约', category: '沙发', space: '客厅', term: '15天', service: '包送、包安装', price: 6700, sold: 300, heat: 550,
        collect: '5人', inventory: 307, score: 5.7, image: '../../images/sofa_1.jpg', creationTime: '2022-12-16'},
      ],
      // 风格快捷选择
      styleRes: [{ text: "轻奢主义", value: "轻奢主义" },{text: "现代简约",  value: "现代简约" },{ text: "自然北欧", value: "自然北欧" },{ text: "现代中式", value: "现代中式" },{ text: "精品实木", value: "精品实木" },{ text: "经典美式", value: "经典美式" },{ text: "奢华欧法", value: "奢华欧法" },{ text: "软装配饰", value: "软装配饰" },{ text: "佗寂风/中古风", value: "佗寂风/中古风" },{ text: "轻奢风", value: "轻奢风" }],
      // 品类快捷选择
      categoryRes: [{ text: "沙发", value: "沙发" },{ text: "茶几", value: "茶几" },{ text: "电视柜", value: "电视柜" },{ text: "鞋柜", value: "鞋柜" },{ text: "餐椅", value: "餐椅" },{ text: "餐边柜", value: "餐边柜" },{ text: "床", value: "床" },{ text: "床头柜", value: "床头柜" },{ text: "床垫", value: "床垫" },{ text: "餐桌", value: "餐桌" },{ text: "休闲椅", value: "休闲椅" }],
      // 空间快捷选择
      spaceRes: [{ text: "客厅", value: "客厅" },{ text: "餐厅", value: "餐厅" },{ text: "卧室", value: "卧室" },{ text: "书房", value: "书房" },{ text: "饰品", value: "饰品" },{ text: "厨房", value: "厨房" },{ text: "阳台", value: "阳台" }],

    }
  },
  methods: {
    // 查询
    queryProduct() {
      // 查询全部
      if(this.identifier == '' && this.name == '') {
        Message({
          message: '当前为查询全部',
          type: 'info',
          duration: 2000
        });

        return;
      }
      if(this.identifier != '') {
        Message({
          message: '当前为编号查询',
          type: 'info',
          duration: 2000
        });

        return;
      }
      Message({
        message: '当前为名称查询',
        type: 'info',
        duration: 2000
      });
    },
    // 删除
    deleteProduct() {
      if(this.productId.length == 0) {
        return Message({
          message: '未勾选产品',
          type: 'error',
          duration: 2000
        });
      }


    },
    // 上一页
    prevInfo(index) {
      console.log(index);
    },
    // 下一页
    nextInfo(index) {
      console.log(index);
    },
    // 编辑
    updateProduct(index, productInfo) {
      const productId = productInfo[index].productId;
      this.$router.push({
        name: 'add-product',
        query: {
          id: productId
        }
      })
    },
    // 多选
    handleSelectionChange(val) {
      this.productId = [];
      val.forEach(element => {
        this.productId.push(element.productId)
      });
    },

    styleResFilterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    categoryResFilterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    spaceResFilterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    }
  }
}
</script>

<style scoped>
  .productInfo {
    width: 94%;
    border-radius: 3px;
    margin: 1%;
    padding: 1% 2% 2% 2%;
    background: white;
  }
  .screen>div>div {
    display: flex;
    line-height: 50px;
  }
  .screen>div>div .el-input {
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