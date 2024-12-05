<template>
  <div class="app-container">
    <header>
      垃圾分类后台管理系统
      <el-dropdown style="float: right;" @command="handleCommand">
        <span class="el-dropdown-link" style="cursor: pointer">
          欢迎你！{{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
<!--          <el-dropdown-item command="person">个人信息</el-dropdown-item>-->
          <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </header>
    <div class="main-content">
      <el-menu class="side-menu" :default-active="$route.path" @select="handleMenuSelect">
        <el-menu-item :index="item.path" v-for="item in permissions" :key="item.path">
          <template slot="title">
            <i :class="['el-icon-' + item.icon]"></i>
            <span>{{ item.name }}</span>
          </template>
        </el-menu-item>
      </el-menu>

      <router-view class="main-container" :key="Math.random()" @call="getMenu"/>
    </div>
  </div>
</template>

<script>


export default {
  name: 'Manage',
  components: {},
  data() {
    return {
      user: {},
      permissions: []
    };
  },
  created(){
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.getMenu()
  },
  methods: {
    getMenu() {
      console.log("===============")
      let permission = [
        {
          "id": 10,
          "name": "用户管理",
          "path": "/user",
          "description": "用户管理",
          "icon": "user-solid"
        },
        {
          "id": 5,
          "name": "公告管理",
          "path": "/notice",
          "description": "公告管理",
          "icon": "data-board"
        },
        {
          "id": 4,
          "name": "轮播图管理",
          "path": "/banner",
          "description": "轮播图管理",
          "icon": "picture"
        },
        {
          "id": 12,
          "name": "订单信息",
          "path": "/order",
          "description": "订单信息",
          "icon": "s-data"
        },
        {
          "id": 2,
          "name": "垃圾分类知识信息",
          "path": "/knowledge",
          "description": "垃圾分类知识信息",
          "icon": "s-data"
        },
        {
          "id": 3,
          "name": "垃圾分类信息",
          "path": "/garbage",
          "description": "垃圾分类信息",
          "icon": "s-data"
        },
        {
          "id": 9,
          "name": "礼品管理",
          "path": "/goods",
          "description": "礼品管理",
          "icon": "s-goods"
        }
      ]
      permission.forEach(item => {
        item.path = "/manage" + item.path
      })
      this.permissions = permission;
    },
    handleCommand(command) {
      if (command === 'person') {
        this.$router.push('/manage/person');
      }
      if (command === 'loginOut') {
        sessionStorage.removeItem("user")
        this.$router.replace('/login');

      }
    },
    handleMenuSelect(index) {
      console.log(this.$router.getRoutes())
      this.$router.push(index);
    },
  },
  computed: {},
}
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

header {
  margin-bottom: 3px;
  line-height: 50px;
  padding: 0 16px;
  font-size: 18px;
  font-weight: bold;
  background-color: #fff;
  box-shadow: 0 0 4px 4px #e6e6e6;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: row;
}

.main-container {
  margin-left: 3px;
  padding: 10px;
  flex: 1;
  overflow-y: auto;
  background-color: #fff;
}

.side-menu {
  width: 200px;
  height: 100%;

}

.el-menu-item a {
  text-decoration: none;
}

.el-menu-item.is-active a {
  color: #409EFF;
}
</style>
