import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/Login.vue'
import Home from '../components/home/Home.vue'
import HomePage from '../components/home/HomePage.vue'
import AddProduct from '../components/product/AddProduct.vue'
import ProductInfo from '../components/product/ProductInfo.vue'
import Order from '../components/order/Order.vue'
import User from '../components/user/User.vue'
import RuleData from '../components/rule/RuleData.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      children: [
        {
          path: '/home/home-page',
          name: 'home-page',
          component: HomePage,
        },
        {
          path: '/home/add-product',
          name: 'add-product',
          component: AddProduct,
        },
        {
          path: '/home/product-info',
          name: 'product-info',
          component: ProductInfo,
        },
        {
          path: '/home/order',
          name: 'order',
          component: Order,
        },
        {
          path: '/home/user',
          name: 'user',
          component: User,
        },
        {
          path: '/home/rule-data',
          name: 'rule-data',
          component: RuleData,
        },
      ],
      redirect: '/home/home-page'
    }
  ]
})
