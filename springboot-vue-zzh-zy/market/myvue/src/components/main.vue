<template>
  <div class="all" id="box" >
    <div id="box1">
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
    <div>
      <div>
        <el-col :span="3">
          <el-input style="margin-left: 600px" width="120" v-model="search" v-on:input ="inputFunc"
                    clearable="true" placeholder="输入关键字搜索"/>
        </el-col>
        <el-button  style="margin-left: 610px; background-color:#1e9d9d; border-color: #1e9d9d" type="primary"  @click="sheng">价格升序</el-button>
        <el-button type="primary" style="background-color:#1e9d9d;" @click="jiang">价格降序</el-button>
      </div>
      <div class="ttt">
        <ul v-for="item in table" :key="item">
          <li>
            <div class="block">
              <el-carousel height="250px" width="500px" >
                <el-carousel-item v-for="i in item.imgUrl" :key="i">
                  <el-image style="width: 250px; height: 250px"
                            :src="i" ></el-image>
                </el-carousel-item>
              </el-carousel>
            </div>
          </li>
          <div style="border:1px solid #BBFFBB" class="t">
            <li>
              <b style="font-size:25px">商品价格:</b><p style="color:red; font-size:25px">¥{{item.price}}</p>
            </li>
            <li>
              <b>商品名称:</b><p @click="info(item)" style="color:blue;text-decoration:underline">{{item.intro}}</p>
            </li>
            <li>
              <b>店铺名称:</b><a @click="shop(item)" style="color:blue;text-decoration:underline">{{item.id}}</a>
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
      tableData: [],
      table: [],
      str: [],
      search: ''
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
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
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
      }
    },
    user () {
      this.$router.push('/personalinfo')
    },
    tui () {
      sessionStorage.setItem('userid', '')
      sessionStorage.setItem('userword', '')
      this.$router.push('/')
      this.$router.go(0)
    },
    shop (item) {
      sessionStorage.setItem('shopid', item.id)
      this.$router.push('/usershop')
    },
    info (item) {
      sessionStorage.setItem('shopid', item.id)
      sessionStorage.setItem('goodid', item.goodid)
      this.$router.push('/goodinfo')
    },
    inputFunc () { // 动态搜索
      this.getList()
    },
    getList () { // 根据id搜索结果集
      this.table = this.tableData.filter((item, index) =>
        item.intro.includes(this.search)
      )
    },
    clear () { // 搜索转全部
      this.search = ''
      this.getList()
    },
    sheng () {
      this.table.sort((a, b) => {
        return a.price - b.price
      })
    },
    jiang () {
      function sortData (a, b) {
        return b.price - a.price
      }
      this.table.sort(sortData)
    },
    getData () { // 后台获取数据
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/getAllgood', formData, config)
        .then(res => {
          // alert(res.data[0].imgUrl)
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
          this.tableData = this.table
        })
    }
  }
}
</script>
<style>
.all {
  background-color: rgb(238, 249, 255);
}
.t {
  background-color: #DFFFDF;
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
#box1 ul{
  float: left;
  width: 120px;
  height: 950px;
}
.el-carousel__item:nth-child(2n) {
  background-color: #cdeafd;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d0ecff;
}
.el-button--primary{
  background-color:#1e9d9d;
  border-color: #1e9d9d;
}
</style>
