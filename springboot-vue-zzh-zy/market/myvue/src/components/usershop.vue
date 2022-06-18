<template>
<div>
  <div>
    <el-menu
      id="nav"
      defalt-active="2"
      class="el-menu-vertical-demo"
      background-color="#1E9D9D"
      text-color="#FFFFFF"
      active-text-color="#FFFFFF"
    >
      <el-menu-item index="1" @click="main">
        <i class="el-icon-user-solid"  style="color:white;"></i>
        <span  slot="title">首 页</span>
      </el-menu-item>
      <el-menu-item v-if="this.userid === ''" index="2" @click="denglu">
        <i class="el-icon-user-solid"  style="color:white;"></i>
        <span  slot="title">登 录</span>
      </el-menu-item>
      <el-menu-item v-if="this.userid != ''" index="2" @click="user">
        <i class="el-icon-user-solid"  style="color:white;"></i>
        <span  slot="title">{{this.userid}}</span>
      </el-menu-item>
      <el-menu-item index="3" @click="car">
        <i class="el-icon-s-order" style="color:white;"></i>
        <span slot="title">购物车</span>
      </el-menu-item>
      <el-menu-item index="4" @click="chong">
        <i class="el-icon-message-solid" style="color:white;"></i>
        <span slot="title">充 值</span>
      </el-menu-item>
      <el-menu-item index="5" id="zhuxiao" @click="tui">
        <i class="el-icon-switch-button" style="color:white;"></i>
        <span slot="title">退 出</span>
      </el-menu-item>
    </el-menu>
    </div>
  <div class="all">
    <div>
      <el-container>
        <el-header>{{this.shopid}}的店铺</el-header>
      </el-container>
    </div>
    <div class="main" id="box" >
    <ul v-for="item in table" :key="item">
      <div class="block">
      <li>
          <el-carousel height="230px" width="150px" >
            <el-carousel-item v-for="i in item.imgUrl" :key="i">
              <el-image style="width: 200px; height: 250px"
                        :src="i" ></el-image>
            </el-carousel-item>
          </el-carousel>
      </li>
      </div>
      <div class="t">
      <li>
        <p style="color:red; font-size:25px">¥{{item.price}}</p>
      </li>
      <li>
        <p @click="info(item)" style="text-decoration:underline">{{item.intro}}</p>
      </li>
      </div>
    </ul>
  </div>
  </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
      userword: '',
      table: [],
      str: [],
      shopid: ''
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
      }
    },
    main () {
      this.$router.push('/')
    },
    denglu () {
      this.$router.push('/login')
    },
    chong () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/chongzhi')
      }
    },
    user () {
      this.$router.push('/personalinfo')
    },
    tui () {
      sessionStorage.setItem('userid', '')
      sessionStorage.setItem('userword', '')
      this.$router.push('/')
    },
    info (item) {
      sessionStorage.setItem('goodid', item.goodid)
      this.$router.push('/goodinfo')
    },
    getData () { // 后台获取数据
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      this.shopid = sessionStorage.getItem('shopid')
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('shopid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/shopgood', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          // alert(this.str.length)
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              this.table[k].imgUrl[j] = require('E:/大三学业/大三下/软件项目实践/pic/goods/' + this.table[k].imgUrl[j])
            }
          }
          // alert(this.table[0].goodid)
        })
    }
  }
}
</script>
<style>

.all {
  background-color: rgb(244, 248, 250);
}
.t {
  background-color: rgb(236, 248, 255);
}
#box ul{
  float: left;
  width: 200px;
  height: 500px;
}
#box li{
  list-style: none;
  padding: auto;
}
.el-carousel__item:nth-child(2n) {
  background-color: #bce4ff;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #cdebff;
}
.el-header {
    background-color: #d7e9ff;
    color: rgb(9, 34, 49);
    text-align: center;
    line-height: 60px;
  }
</style>
