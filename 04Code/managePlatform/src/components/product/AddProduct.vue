<template>
  <!-- 新增/修改产品页面 -->
  <div class="addProduct">
    <el-row>
      <el-col :span="12">
        <h4>新增/修改产品信息</h4>
      </el-col>
      <el-col :span="12" style="text-align: right;padding-right: 3%;">
        <el-button style="width: 20%;" @click="saveProduct()" type="success">提交</el-button>
      </el-col>
    </el-row>
    <!-- 数据 -->    
    <div class="info">
      <el-row :gutter="10">
        <el-col :span="6">
          <span>名称</span>
          <el-input v-model="product.name" placeholder="请输入产品名称"></el-input>
        </el-col>
        <el-col :span="6">
          <span>编号</span>
          <el-input v-model="product.identifier" placeholder="请输入产品编号"></el-input>
        </el-col>
        <el-col :span="6">
          <span>产地</span>
          <el-input v-model="product.producer" placeholder="请输入产品产地"></el-input>
        </el-col>
        <el-col :span="6">
          <span>材质</span>
          <el-autocomplete class="inline-input" v-model="product.materialQuality" :fetch-suggestions="materialQualitySearch" placeholder="请选择产品材质"></el-autocomplete>
        </el-col>
        <el-col :span="6">
          <span>风格</span>
          <el-autocomplete class="inline-input" v-model="product.style" :fetch-suggestions="styleSearch" placeholder="请选择产品风格"></el-autocomplete>
        </el-col>
        <el-col :span="6">
          <span>品类</span>
          <el-autocomplete class="inline-input" v-model="product.category" :fetch-suggestions="categorySearch" placeholder="请选择产品品类"></el-autocomplete>
        </el-col>
        <el-col :span="6">
          <span>空间</span>
          <el-autocomplete class="inline-input" v-model="product.space" :fetch-suggestions="spaceSearch" placeholder="请选择产品空间"></el-autocomplete>
        </el-col>
        <el-col :span="6">
          <span>填充物</span>
          <el-autocomplete class="inline-input" v-model="product.filler" :fetch-suggestions="fillerSearch" placeholder="请输入填充物"></el-autocomplete>
        </el-col>
        <el-col :span="6">
          <span>单价</span>
          <el-input v-model="product.price" placeholder="请输入产品单价"></el-input>
        </el-col>
        <el-col :span="6">
          <span>库存</span>
          <el-input-number v-model="product.inventory" :min="1" label="请输入产品库存"></el-input-number>
        </el-col>
        <el-col :span="6">
          <span>尺寸</span>
          <el-input v-model="product.size" placeholder="多个尺寸用空格分隔"></el-input>
        </el-col>
        <el-col :span="6">
          <span>颜色</span>
          <el-input v-model="product.colour" placeholder="多个颜色用空格分隔"></el-input>
        </el-col>
        <el-col :span="6">
          <span>类型</span>
          <el-input v-model="product.materialType" placeholder="多个类型用空格分隔"></el-input>
        </el-col>
        <el-col :span="18">
          <span>介绍</span>
          <el-input style="width: 87%;margin-left: 0.7%;" type="textarea" :rows="2" v-model="product.introduce" placeholder="请输入产品介绍"></el-input>
        </el-col>
        <el-col :span="6">
          <span>图片</span>
          <el-upload
            class="upload-demo"
            action="#"
            ref="upload"
            :multiple="true"
            :on-change="imageChange"
            :file-list="product.images"
            :auto-upload="false">
            <el-button slot="trigger" @click="openMessage('需与尺寸数量对应')" size="max" type="primary">选取文件<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-col>
        <el-col :span="6">
          <span>轮播图</span>
          <el-upload
            class="upload-demo"
            action="#"
            ref="upload"
            :multiple="true"
            :on-change="carouselImagesChange"
            :file-list="product.carouselImages"
            :auto-upload="false">
            <el-button slot="trigger" @click="openMessage('需与尺寸数量对应')" size="max" type="warning">选取文件<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-col>
        <el-col :span="6">
          <span>实物图</span>
          <el-upload
            class="upload-demo"
            action="#"
            ref="upload"
            :multiple="true"
            :on-change="physicalImagesChange"
            :file-list="product.physicalImages"
            :auto-upload="false">
            <el-button slot="trigger" @click="openMessage('建议不超过5张')" size="max" type="info">选取文件<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-col>
        <el-col :span="6">
          <span>详情图</span>
          <el-upload
            class="upload-demo"
            action="#"
            ref="upload"
            :multiple="true"
            :on-change="detailsImagesChange"
            :file-list="product.detailsImages"
            :auto-upload="false">
            <el-button slot="trigger" @click="openMessage('建议不超过10张')" size="max" plain type="info">选取文件<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-col>
      </el-row>
    </div>
    <span style="position: fixed;bottom: 2%;margin-left: 18%;color: #43b597;font-family: Arial;font-size: 12px;letter-spacing: 2px;">Design and Implementation of a Furniture Store System Based on WeChat Mini Program.</span>
  </div>
</template>

<script>
import { Message } from 'element-ui';
import { addProductInfo, getProductById } from '../../request/api';
export default {
  name: 'AddProduct',
  data () {
    return {
      // 产品ID
      productId: '',
      // 产品图片数量
      imageSize: -1,
      // 产品信息
      product: {
        name: '',
        introduce: '',
        identifier: '',
        producer: '',
        materialQuality: '',
        filler: '',
        style: '',
        category: '',
        space: '',
        price: '',
        inventory: '',
        size: '',
        colour: '',
        materialType: '',
        images: [],
        carouselImages: [],
        physicalImages: [],
        detailsImages: [],
      },
      // 材质快捷选择
      materialQualityRes: [{ value: "实木框架" },{ value: "松木" },{ value: "白蜡木" },{ value: "岩板" },{ value: "其他" }],
      // 风格快捷选择
      styleRes: [{ value: "轻奢主义" },{ value: "现代极简" },{ value: "自然北欧" },{ value: "现代中式" },{ value: "精品实木" },{ value: "经典美式" },{ value: "奢华欧法" },{ value: "软装配饰" },{ value: "佗寂风/中古风" },{ value: "轻奢风" }],
      // 品类快捷选择
      categoryRes: [{ value: "沙发" },{ value: "茶几" },{ value: "电视柜" },{ value: "鞋柜" },{ value: "餐椅" },{ value: "餐边柜" },{ value: "床" },{ value: "床头柜" },{ value: "床垫" },{ value: "餐桌" },{ value: "休闲椅" }],
      // 空间快捷选择
      spaceRes: [{ value: "客厅" },{ value: "餐厅" },{ value: "卧室" },{ value: "书房" },{ value: "饰品" },{ value: "厨房" },{ value: "阳台" }],
      // 填充物快捷选择
      fillerRes: [{ value: "海绵" },{ value: "海绵+羽绒" },{ value: "高密度海绵" },{ value: "其他" }],
    }
  },
  async mounted () {
    // 获取url中的产品ID
    this.productId = this.$route.query.id;
    if(typeof this.productId != 'undefined') {
      const res = await getProductById([this.productId]);
      if (res.code != 200) {
        this.productId = '';
        return Message({
          message: res.message,
          type: 'error',
          duration: 2000
        });
      }
      this.product = res.data;
      this.product.images = [];
      this.product.carouselImages = [];
      this.product.physicalImages = [];
      this.product.detailsImages = [];
      this.imageSize = this.product.size.split(" ").length;
    }
  },
  methods: {
    // 新增/修改
    async saveProduct() {
      if (this.productId != '') {
        if(this.imageSize != this.product.size.split(" ").length) {
          if(this.product.images.length != this.product.size.split(" ").length) {
            return Message({
              message: '修改尺寸后选购图片数量需一致 请重新选择图片',
              type: 'error',
              duration: 3000
            });
          }
        }
      }
      let formData = new FormData();
      for (const file of this.product.images) {  //多个文件全部都放到files
        if(file) {
          formData.append('images', file);
        }
      }
      for (const file of this.product.carouselImages) {  //多个文件全部都放到files
        if(file) {
          formData.append('carouselImages', file);
        }
      }
      for (const file of this.product.physicalImages) {  //多个文件全部都放到files
        if(file) {
          formData.append('physicalImages', file);
        }
      }
      for (const file of this.product.detailsImages) {  //多个文件全部都放到files
        if(file) {
          formData.append('detailsImages', file);
        }
      }
      formData.append('productId', this.productId);
      formData.append('name', this.product.name);
      formData.append('introduce', this.product.introduce);
      formData.append('identifier', this.product.identifier);
      formData.append('producer', this.product.producer);
      formData.append('materialQuality', this.product.materialQuality);
      formData.append('filler', this.product.filler);
      formData.append('style', this.product.style);
      formData.append('category', this.product.category);
      formData.append('space', this.product.space);
      formData.append('price', this.product.price);
      formData.append('inventory', this.product.inventory);
      formData.append('size', this.product.size);
      formData.append('colour', this.product.colour);
      formData.append('materialType', this.product.materialType);
      formData.append('opt', this.productId === '' ? true : false);

      const res = await addProductInfo(formData);
      if(res.code == 200) {
        this.product = { name: '', introduce: '', identifier: '', producer: '', materialQuality: '', filler: '', style: '', category: '',
          space: '', price: '', inventory: '', size: '', colour: '', materialType: '', images: [], carouselImages: [], physicalImages: [],detailsImages: []};
        Message({
          message: '上传成功',
          type: 'success',
          duration: 2000
        });
        if (this.productId != '') {
          return this.$router.push({name: 'product-info'})
        }
      }
    },
    // 图片选择
    imageChange(file, fileList) {
      this.product.images = [];
      fileList.forEach(element => {
        this.product.images.push(element.raw)
      });
    },
    carouselImagesChange(file, fileList) {
      this.product.carouselImages = [];
      fileList.forEach(element => {
        this.product.carouselImages.push(element.raw)
      });
    },
    physicalImagesChange(file, fileList) {
      this.product.physicalImages = [];
      fileList.forEach(element => {
        this.product.physicalImages.push(element.raw)
      });
    },
    detailsImagesChange(file, fileList) {
      this.product.detailsImages = [];
      fileList.forEach(element => {
        this.product.detailsImages.push(element.raw)
      });
    },
    openMessage(message) {
      this.$message({
        type: 'warning',
        showClose: true,
        duration: 10000,
        message: message,
      });
    },
    materialQualitySearch(queryString, cb) {
      var restaurants = this.materialQualityRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    styleSearch(queryString, cb) {
      var restaurants = this.styleRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    categorySearch(queryString, cb) {
      var restaurants = this.categoryRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    spaceSearch(queryString, cb) {
      var restaurants = this.spaceRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    fillerSearch(queryString, cb) {
      var restaurants = this.fillerRes;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    }
  }
}
</script>

<style scoped>
  .addProduct {
    width: 94%;
    margin: 1%;
    padding: 1% 2% 2% 2%;
    border-radius: 3px;
    background: white;
  }
  .info {
    padding: 1% 0 5% 0;
  }
  .info>div>div {
    display: flex;
    line-height: 60px;
    margin-top: 1%;
  }
  .info>div>div .el-input, .info>div>div .el-autocomplete, .info>div>div .el-input-number {
    width: 60%;
    margin-left: 2%;
  }
  .info>div>div .el-input-number {
    margin-top: 3%;
  }
  .info>div>div .el-button {
    width: 160%;
    margin-left: 7%;
  }
</style>