<template>
  <div style="margin-top: 10px">
    <div style="background-color: white; padding: 10px">
      <!--        商品确认-->
      <el-table :data="carts" stripe s tyle="width: 100%" :show-header="false">
        <el-table-column label="礼品图片" width="150">
          <template slot-scope="scope">
            <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="礼品名称"></el-table-column>
      </el-table>

      <div style="margin-top: 10px">
        <div style="background-color: white; padding: 10px">
          <div style="color: red; text-align: right">
            <div>
              <span>总价：</span>
              <span>{{ totalPoint }}</span>
            </div>
            <div style="padding: 10px 0">
              <el-button style="background-color: red; color: white; width: 100px" @click="submitOrder">提交订单</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import API from "@/utils/request";

export default {
  name: "cart",
  data() {
    return {
      user: {},
      carts: [],
      totalPoint: 0,
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    if (!this.user) {
      this.$message({
        type: "warning",
        message: '请登录！'
      })
      return
    }
    this.load()
  },
  methods: {
    submitOrder() {
      // 提交订单
      API.post("/userWeb/sendGoods", {
        goodsId: this.carts[0].id,
        totalPoint: this.totalPoint,
        status: '0',
        userId: this.user.id
      }).then(res => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '提交成功！'
          })
          this.$router.replace("/front/order")
        } else {
          this.$message({
            type: 'error',
            message: res.message
          })
        }
      })
    },
    load() {
      let id = this.$route.params.id
      API.get("/userWeb/getGoodsById?id=" +id).then(res => {
        this.goods = res.row
        this.carts = [this.goods];
        this.totalPoint = this.goods.point;
      })
    },
  }
}
</script>

<style scoped>

</style>
