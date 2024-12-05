<template>
  <div>

    <div style="margin-top: 10px">
      <el-row>
        <el-col :span="24">
          <el-card>

            <div style="padding: 10px 0">
              <div style="margin-bottom: 20px">
                <el-input placeholder="输入搜索的垃圾分类信息名称" style="width: 30%" v-model="searchText"></el-input>
                <el-button style="margin-left: 5px" @click="loadTable">搜索</el-button>
              </div>
              <el-row :gutter="10">
                <el-col :span="6" v-for="item in tableData" :key="item.id" style="margin-bottom: 10px">
                  <div style="border: 1px solid #ccc; padding: 10px; cursor: pointer" @click="garbageDetail(item.id)">
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

          </el-card>
        </el-col>
      </el-row>

    </div>

  </div>
</template>

<script>
import API from "@/utils/request";

export default {
  name: "knowledge",
  data() {
    return {
      searchText: '',
      tableData: [],
      user: {},
      pageNum: 1,
      pageSize: 8,
      total: 0,

    };
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.searchText = this.$route.query.searchText || ''
    this.loadTable()
  },
  methods: {
    garbageDetail(id) {
      this.$router.replace({path: '/front/garbageDetail', query: {id: id}})
    },
    loadTable() {
      API.get("/userWeb/getGarbage", {
        params: {
          title: this.searchText,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.list.records
        this.total = res.list.total
      })
    },
    handleSizeChange(pageNum) {
      this.pageNum = pageNum;
      this.loadTable()
    },
    handleCurrentChange(pageSize) {
      this.pageSize = pageSize;
      this.loadTable()
    }
  },
};
</script>

<style scoped>

</style>
