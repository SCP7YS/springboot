<template>
    <div class="div" id="div1">
      <div id="box2">
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
        <div class="block">
          <el-carousel  style="width: 300px; height:800px">
            <el-carousel-item v-for="i in this.table[0].imgUrl" :key="i">
              <el-image style="width: 300px; height:300px"
                        :src="i" ></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="info">
          <p style="font-size:25px">商品名称:{{this.table[0].name}}</p>
          <p style="font-size:25px">{{this.table[0].intro}}</p>
          <p style="color:red; font-size:25px">原价:¥{{this.table[0].price}}
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 现价:¥{{this.table[0].price*this.table[0].zhekou/10}}</p>
          <el-row :gutter="22">
            <el-col :span="7"><p>折扣:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].zhekou}}</p></el-col>
            <el-col :span="7"><p>几成新:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].newo}}</p></el-col>
          </el-row>
          <el-row :gutter="22">
            <el-col :span="7"><p>是否议价:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].yijia}}</p></el-col>
            <el-col :span="7"><p>商品规格:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].size}}</p></el-col>
          </el-row>
          <el-row :gutter="22">
            <el-col :span="7"><p>销量:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].shouchu}}</p></el-col>
            <el-col :span="7"><p>库存:</p></el-col>
            <el-col :span="4"><p>{{this.table[0].amount}}</p></el-col>
          </el-row>
          <el-row :gutter="22">
          <el-input-number v-model="count" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
            <el-button  style="margin-left: 20px;font-size:16px;background-color:greenyellow" icon="el-icon-shopping-cart-full" type="primary" @click="add"></el-button>
          </el-row>
        </div>
      </div>
      <div class="aaa">
          <span>评论区：</span>
            <el-table
              :data="tableData"
              style="width: 100%"
              :row-class-name="tableRowClassName">
              <el-table-column
                prop="id"
                label="用户名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="text"
                label="评论"
                width="300">
              </el-table-column>
              <el-table-column
                prop="rate"
                label="等级"
                width="300" >
                <template slot-scope="scope">
                  <el-rate v-model="scope.row.rate" show-text disabled style="margin-top:20px"></el-rate>
                </template>
              </el-table-column>
            </el-table>
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
      item: {
        id: '',
        text: '',
        rate: ''
      },
      table: [],
      str: [],
      count: 1,
      u: '',
      val: '',
      max: ''
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
    add () {
      if (sessionStorage.getItem('userid') === '' || sessionStorage.getItem('userid') === null) {
        this.$router.push('/login')
      } else {
        let formData = new FormData()
        formData.append('id', sessionStorage.getItem('userid'))
        formData.append('word', sessionStorage.getItem('userword'))
        formData.append('shopid', sessionStorage.getItem('shopid'))
        formData.append('goodid', sessionStorage.getItem('goodid'))
        formData.append('name', this.table[0].name)
        formData.append('price', this.table[0].price * this.table[0].zhekou / 10)
        formData.append('intro', this.table[0].intro)
        formData.append('newo', this.table[0].newo)
        formData.append('fenlei', this.table[0].fenlei)
        formData.append('size', this.table[0].size)
        formData.append('yijia', this.table[0].yijia)
        formData.append('count', this.count)
        formData.append('amount', this.table[0].amount)
        formData.append('imgUrl', this.u)
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
        this.$axios.post('http://localhost:8088/addcar', formData, config)
          .then(res => {
            alert('添加成功！')
          })
      }
    },
    getData () {
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/goodinfo', formData, config)
        .then(res => {
          this.u = res.data[0].imgUrl
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              this.table[k].imgUrl[j] = require('E:/大三学业/大三下/软件项目实践/pic/goods/' + this.table[k].imgUrl[j])
            }
          }
          this.max = this.table[0].amount
          this.getData1()
        })
    },
    getData1 () {
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/comment', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.item = {
              id: res.data[i].id,
              text: res.data[i].text,
              rate: res.data[i].rate
            }
            this.tableData.push(this.item)
          }
        })
    },
    handleChange (currentValue, oldValue) {
      this.$nextTick(() => {
        // alert(this.tableData[index].goodid)
        this.val = oldValue
        if (currentValue > this.table[0].amount) {
          this.count = this.val
          alert('数量超出范围~')
        }
      })
    }
  }
}
</script>
<style scoped>

#box ul{
  padding:10px;
}
#box li{
  list-style: none;
  padding: auto;
  padding:0px;
}
.block {
  margin-top: 50px;
  margin-left: 300px;
  float: left;
  width: 300px;
  height: 500px;
}
#box2 ul{
  float: left;
  width: 120px;
  height: 950px;
}
.info {
  margin-top: 30px;
  margin-left: 50px;
  float: left;
}
  .el-header {
    background-color: #e7bf94;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .aaa{
    margin-top:500px;

 position:absolute;

 left:30%
  }
  #box{
    width: 120px;
    height: 950px;
  }
  #div1{
    background-color: #f5d9c2;
  }
</style>
