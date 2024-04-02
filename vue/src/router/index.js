import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home'
import Login from '../views/Login'
import Layout from '../layout/Layout'
import Register from '../views/Register'
import myTicket from '../views/MyTicket'
import mySpace from '../views/MySpace'
import Manager from '../views/Manager'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/login',
    children:[
      {
        path:'home',
        name:'Home',
        component:Home
      },
      {
        path: 'myTicket',
        name: 'myTicket',
        component: myTicket
      },
      {
        path:'mySpace',
        name:'mySpace',
        component: mySpace
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/Register',
    name: 'register',
    component: Register
  },
  {
    path:'/manager',
    name:'Manager',
    component:Manager
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
