<template>
  <!-- 首页 -->
  <div class="homePage">
    <!-- 头部数据 -->
    <div class="headData">
      <el-row :gutter="20">
        <el-col :span="6">
          <div>
            <el-statistic title="已下单">
              <template slot="formatter">
                <span>{{ orderInfo.ordered == null ? 0 : orderInfo.ordered }}</span>/{{ orderInfo.total }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="已确认">
              <template slot="formatter">
                <span>{{ orderInfo.determined == null ? 0 : orderInfo.determined }}</span>/{{ orderInfo.total }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="待完成">
              <template slot="formatter">
                <span>{{ orderInfo.completed == null ? 0 : orderInfo.completed}}</span>/{{ orderInfo.total }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="待售后">
              <template slot="formatter">
                <span>{{ orderInfo.afterSales == null ? 0 : orderInfo.afterSales}}</span>/{{ orderInfo.total }}
              </template>
            </el-statistic>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 柱形图 -->
    <div class="chart">
      <el-row :gutter="20">
        <el-col :span="12">
          <div>
              <div v-show="switchHeatOrSales == false" ref="echartsHeat" style="width: 630px; height: 430px;"></div>
              <div v-show="switchHeatOrSales" ref="echartsProductSale"  style="width: 630px; height: 430px;"></div>
            <el-row>
              <el-col :span="17">&nbsp;</el-col>
              <el-col :span="7" style="display: flex;">
                <span style="font-size: 12px;line-height: 20px;">{{ this.switchHeatOrSales ? '切换产品热度榜' : '切换产品销量榜' }}</span>&nbsp;&nbsp;
                <el-switch v-model="switchHeatOrSales" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <div ref="echartsSale" class="echarts"></div>
          </div>
        </el-col>
      </el-row>
    </div>
    <span style="position: fixed;bottom: 2%;margin-left: 18%;color: #43b597;font-family: Arial;font-size: 12px;letter-spacing: 2px;">Design and Implementation of a Furniture Store System Based on WeChat Mini Program.</span>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getOrderState, getProductHeatOrSales, getShopSales } from "../../request/api";
export default {
  name: 'HomePage',
  data () {
    return {
      // 订单信息
      orderInfo: {},
      // 热度信息
      productHeat: {},
      productSale: {},
      // 销售信息
      shopSale: [],
      chartHeat: null,
      chartProductSale: null,
      switchHeatOrSales: false
    }
  },
  async mounted () {
    // 查询订单信息
    const resOrderSate = await getOrderState();
    if(resOrderSate.code == 200) {
      this.orderInfo = resOrderSate.data;
    }
    // 查询热度和销量榜信息
    const resProductHeat = await getProductHeatOrSales(["heat"]);
    if(resProductHeat.code == 200) {
      this.productHeat = resProductHeat.data;
      this.echartsHeat();
    }
    const resProductSales = await getProductHeatOrSales(["sold"]);
    if(resProductSales.code == 200) {
      this.productSale = resProductSales.data;
      this.echartsProductSale();
    }
    // 查询销售信息
    const resShopSale = await getShopSales();
    if(resShopSale.code == 200) {
      this.shopSale = resShopSale.data;
      this.echartsSale();
    }
    
  },
  methods: {
    echartsHeat() {
      // 获取容器元素
      const container = this.$refs.echartsHeat;
      // 创建图表实例
      this.chartHeat = echarts.init(container);
      // 配置图表选项
      const options = {
        title: {
        text: "产品热度榜",
      },
      tooltip: {},
      legend: {
        data: ["热度"],
      },
      xAxis: {
        axisLabel: {
          interval: 0,
          rotate: 20
        },
        data: this.productHeat.name
      },
      yAxis: {},
      series: [
        {
          name: "热度",
          type: "bar",
          data: this.productHeat.value
        },
      ],
      };
      // 使用配置项显示图表
      this.chartHeat.setOption(options);
    },

    echartsProductSale() {
      // 获取容器元素
      const container = this.$refs.echartsProductSale;
      // 创建图表实例
      this.chartProductSale = echarts.init(container);
      // 配置图表选项
      const options = {
        title: {
        text: "产品销量榜",
      },
      tooltip: {},
      legend: {
        data: ["销量"],
      },
      xAxis: {
        axisLabel: {
          interval: 0,
          rotate: 20
        },
        data: this.productSale.name
      },
      yAxis: {},
      series: [
        {
          name: "销量",
          type: "bar",
          data: this.productSale.value
        },
      ],
      };
      // 使用配置项显示图表
      this.chartProductSale.setOption(options);
    },

    echartsSale() {
      this.$nextTick(() =>{
        // 基于准备好的dom，初始化echarts实例
        let chartSale = echarts.init(this.$refs.echartsSale);
        // 指定图表的配置项和数据
        let option = {
          // 全局颜色配置
          // color: ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#6e7074',],
          // echarts标题
          title: {
            text: '店铺销量图'
          },
          tooltip: {},
          legend: {
            data: ['销量']
          },
          xAxis: {
            splitNumber: 4,
            // X轴所在位置
            // position: 'top',
            // 轴设置
            axisLine: {
              width: 90,
              // 设置箭头
              symbol: ['none', 'arrow'],
            },
            // 刻度标签
            axisLabel: {
              interval: '0',
              // 默认显示,值为true
              // show: false,
              // 哭刻度线与标签的距离
              margin: 10,
              // 标签角度，默认逆时针为0,默认顺时针为360，
              // rotate: -90,
              // 字体风格，默认为normal
              // fontStyle: 'italic',
              // 字体大小
              // fontSize: 10,
              // 字符串模板
              formatter: '{value}月',
              // 文字位置
              // align: 'left'
              // verticalAlign: 'bottom',
              // 配置了width，overflow才会生效
              width: 10,
              overflow: 'break',
            },
            // 刻度设置
            axisTick: {
              interval: '0',
              alignWithLabel: true,
              // 刻度线朝内，默认朝外
              inside: true,
              length: 6,
              // 刻度线样式设置
              lineStyle: {
                type: 'dashed',
                // ...
              }
            },
            data: this.shopSale.map(item => item.name),
            type: 'category',
            name: '时间',
            nameLocation: 'middle',
            nameTextStyle: {
              padding: 40,
            },
          },
          yAxis: {
            type: 'value',
          },
          series: [
            {
              // 为true则是光滑曲线，默认为false，即折线
              smooth: false,
              name: '销量',
              // 此系列自己的调色盘。
              color: [
                '#dd6b66',
              ],
              type: 'line',
              data: this.shopSale.map(item => item.value)
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        chartSale.setOption(option);
      })
    }
  },
  beforeDestroy() {
    // 销毁图表实例
    if (this.chartHeat) {
      this.chartHeat.dispose();
      this.chartHeat = null;
    }
  },
}
</script>

<style scoped>
  .homePage {
    width: 98%;
    height: 90%;
    margin: 1%;
    border-radius: 3px;
    /* background: white; */
  }
  .headData {
    line-height: 50px;
    padding-bottom: 2%;
    border-radius: 3px;
    background: white;
  }
  /* .headData>div>div>div {
    background: white;
  } */
  .headData span {
    font-weight: 600;
  }
  .chart {
    width: 100%;
    margin-top: 1%;
  }
  .chart>div>div>div{
    background: white;
    border-radius: 3px;
    padding: 4%;
  }
  .echarts {
    width: 100%;
    height: 450px;
    overflow: hidden;
  }
</style>