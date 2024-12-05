<template>
  <div>

    <div style="margin-top: 10px">
      <!--      商品-->
      <el-row :gutter="10">
        <!--    图片-->
        <el-col :span="8">
          <el-card>
            <div>
              <el-image :src="goods.image"></el-image>
            </div>
          </el-card>
        </el-col>

        <!--    商品细节-->
        <el-col :span="16">
          <el-card>
            <div style="padding: 10px 0; font-size: 20px; font-weight: bold">{{ goods.name }}</div>
            <div style="padding: 10px 0; color: #666; font-size: 14px">{{ goods.description }}</div>
            <div style="padding: 10px 0; color: #999;">
              <span>上架时间</span>
              <span style="margin-left: 20px; color: #666">{{ goods.createTime }}</span>
            </div>

            <div style="padding: 10px 0">
              <span style="color: #999">积分</span>
              <span style="margin-left: 10px; color: orangered; font-size: 20px">{{ goods.point }}</span>
            </div>
            <div style="margin-top: 20px; padding: 10px 0">
              <el-button style="margin-left: 10px; background-color: orangered; color: white" @click="buyNow">立即兑换
              </el-button>
            </div>

          </el-card>
        </el-col>

      </el-row>
      <!--      介绍-->
      <el-row>
        <el-col :span="24">
          <div style="margin-top: 10px; margin-bottom: 80px">
            <el-card>
              <div slot="header" class="clearfix">礼品介绍</div>
              <div v-html="goods.content">
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>

      <!--      评论-->
      <el-row>
        <el-col :span="24">
          <div style="margin-top: 10px; margin-bottom: 80px">
            <el-card>
              <div
                  style="padding-bottom: 10px; margin-bottom: 20px; border-bottom: 2px solid orangered; font-size: 20px">
                服务评分
              </div>
              <div style="display: flex; padding: 20px" v-for="item in messages">
                <div style="text-align: center; flex: 1">
                  <el-image :src="item.image" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
                </div>
                <div style="padding: 0 10px; flex: 5">
                  <div><b style="font-size: 14px">{{ item.userName }}</b></div>
                  <div style="padding: 10px 0; color: #888">
                    {{ item.content }}
                  </div>
                  <div style="padding-bottom: 10px">
                    <div v-if="item.type === '1'" style="color: #888; font-size: 12px">
                      <span>好评</span>
                    </div>
                    <div v-else-if="item.type === '2'" style="color: #888; font-size: 12px">
                      <span>中评</span>
                    </div>
                    <div v-else-if="item.type === '3'" style="color: #888; font-size: 12px">
                      <span>差评</span>
                    </div>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
import API from "@/utils/request";
export default {
  name: "Goods",
  data() {
    return {
      messages: [],
      dialogFormVisible: false,
      entity: {},
      num: 1,
      id: 1,
      user: {},
      goods: {},
      praiseFlag: false
    };
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.id = this.$route.query.id
    this.load()
  },
  methods: {
    buyNow() {
      this.$router.replace("/front/preOrder/" + this.goods.id)
    },
    load() {
      API.get("/userWeb/getGoodsById?id=" + this.id).then(res => {
        this.goods = res.row
      })

      API.get("/userWeb/getMessage?id=" + this.id).then(res => {
        this.messages = res.list
      })
    },
  },
};
</script>

<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
