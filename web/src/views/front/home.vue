<template>
  <div>
    <div style="margin-top: 10px">
      <el-carousel height="400px">
        <el-carousel-item v-for="item in bannerList" :key="item.id">
          <img style="width: 100%" :src='item.image' alt="">
        </el-carousel-item>
      </el-carousel>
    </div>

    <!--    其他-->
    <div style="margin-top: 10px">
      <el-card>
        <el-row :gutter="10">
          <!--          分类-->
          <el-col :span="4">
            <div style="border: 1px dashed #ccc; text-align: center; border-bottom: none">
              <div class="category-item" @click="getGoodsByCategory(item.id)" v-for="item in typeList" :key="item.id"
                   :class="{ active: (activeIndex === item.id)}">{{item.name }}
              </div>
            </div>
          </el-col>

          <el-col :span="20">
            <div style="min-height: 150px">
              <el-row :gutter="10">
                <el-col :span="6" v-for="item in garbageList" :key="item.id">
                  <div style="margin-bottom: 10px; cursor: pointer"  @click="garbageDetail(item.id)">
                    <div style="padding: 5px"><el-image :src="item.image" style="width: 100%; height: 100px" fit="contain"></el-image></div>
                    <div style="padding: 5px; height: 20px; text-align: center; font-size: 12px; overflow: hidden">
                      <el-tooltip :content="item.title" placement="bottom" effect="light">
                        <span class="item-title">{{ item.title }}</span>
                      </el-tooltip>
                    </div>
                    <div style="padding: 5px; text-align: center; color: red; font-size: 12px">
                      <div v-if="item.type === '0'">可回收垃圾</div>
                      <div v-else-if="item.type === '1'">干垃圾</div>
                      <div v-else-if="item.type === '2'">湿垃圾</div>
                      <div v-else-if="item.type === '3'">有害垃圾</div>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!--      分页-->
            <div style="margin-top: 20px">
              <el-pagination
                  small
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-size="pageSize"
                  :page-sizes="[4, 8, 12]"
                  layout=" prev, pager, next"
                  :total="total"
              >
              </el-pagination>
            </div>

          </el-col>
        </el-row>
      </el-card>

    </div>


    <div style="margin-top: 10px">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-card>
            <div style="padding: 10px 0; border-bottom: 2px solid orangered; font-size: 20px; color: orangered">公告信息</div>
            <div style="padding: 10px 0">
              <el-row :gutter="10">
                <el-col :span="6" v-for="item in noticeList" :key="item.id" style="margin-bottom: 10px">
                  <div style="border: 1px solid #ccc; cursor: pointer; padding: 10px"  @click="noticeDetail(item.id)">
                    <el-image :src="item.image" style="width: 100%; height: 100px" fit="contain"></el-image>
                    <div style="padding: 5px; height: 20px; text-align: center; font-size: 12px; overflow: hidden">
                      <el-tooltip :content="item.title" placement="bottom" effect="light">
                        <span class="item-title">{{ item.title }}</span>
                      </el-tooltip>
                    </div>
                    <div style="padding: 5px; text-align: center; color: red; font-size: 12px">
                      {{ item.createTime }}
                    </div>
                  </div>

                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>

        <!--        销售排行-->
        <el-col :span="6">
          <el-card>
            <div style="padding: 10px 0; border-bottom: 2px solid dodgerblue; font-size: 20px; color: dodgerblue">垃圾分类排名</div>

            <div style="padding: 10px 0">
              <el-row v-for="(item, index) in userList" :key="item.id">
                <div style="padding: 10px 0; cursor: pointer;">
                  <el-col :span="2" style="padding-top: 5px">
                    <span v-if="index === 0" style="color: gold">{{ index + 1 }}</span>
                    <span v-if="index === 1" style="color: silver">{{ index + 1 }}</span>
                    <span v-if="index === 2" style="color: coral">{{ index + 1 }}</span>
                    <span v-if="index > 2">{{ index + 1 }}</span>
                  </el-col>
                  <el-col :span="10"><el-image :src="item.image" style="width: 100%; height: 80px" fit="contain"></el-image></el-col>
                  <el-col :span="12">
                    <div style="overflow: hidden; height: 80px; font-size: 12px">
                      {{ item.name }}（积分{{item.point}}）
                    </div>

                  </el-col>
                </div>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
import API from "@/utils/request";

const url = "/api/video/"

export default {
  name: "Home",
  data() {
    return {
      activeIndex: '0',
      user: {},
      garbageList:[],
      bannerList:[],
      userList:[],
      typeList: [
        {id:"0", name:"可回收垃圾"},
        {id:"1", name:"干垃圾"},
        {id:"2", name:"湿垃圾"},
        {id:"3", name:"有害垃圾"}
      ],
      noticeList:[],
      pageNum: 1,
      pageSize: 8,
      total: 0
    };
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.load()
  },
  methods: {
    load() {
      this.getBanner();
      this.getGarbage('0');
      this.getNoticeLimit8();
      this.getRanking();
    },

    // 轮播图信息查询
    getBanner() {
      API.get("/userWeb/getBanner").then(res => {
        this.bannerList = res.list
      })
    },

    getGoodsByCategory(id) {
      this.activeIndex = id
      this.getGarbage(id)
    },

    // 垃圾分类信息查询
    getGarbage(type) {
      API.get("/userWeb/getGarbage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          type: type,
          status:'1'
        }
      }).then(res => {
        this.garbageList = res.list.records
        this.total = res.list.total
      })
    },

    // 垃圾分类公告信息
    getNoticeLimit8() {
      API.get("/userWeb/getNoticeLimit8").then(res => {
        this.noticeList = res.list
      })
    },

    // 排行查询
    getRanking() {
      API.get("/userWeb/getRanking").then(res => {
        this.userList = res.list
      })
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.loadTable(this.activeIndex)
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.loadTable(this.activeIndex)
    },
    garbageDetail(id) {
      this.$router.replace({path: '/front/garbageDetail', query: {id: id}})
    },
    noticeDetail(id) {
      this.$router.replace({path: '/front/noticeDetail', query: {id: id}})
    },

  },
};
</script>

<style scoped>
.active {
  color: red !important;
}

.category-item {
  padding: 5px 0;
  border-bottom: 1px dashed #ccc;
  color: #666;
  cursor: pointer;
}

.line1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-title {
  cursor: pointer;
}
.item-title:hover {
  color: orangered;
}
</style>
