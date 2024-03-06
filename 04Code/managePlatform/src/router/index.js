import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/Login.vue'
import Home from '../components/home/Home.vue'
import HomePage from '../components/home/HomePage.vue'
import AddProduct from '../components/product/AddProduct.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
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
      ]
    }
  ]
})
