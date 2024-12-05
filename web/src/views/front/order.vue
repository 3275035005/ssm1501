<template>
  <div style="margin-top: 10px">
    <!--    全部-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部订单" name="all">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="礼品名称" width="300">
            <template slot-scope="scope">
              <div  style="display: flex">
                <div style="flex: 2"><a :href="['/front/goodsDetail?id=' + scope.row.goodsId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/goodsDetail?id=' + scope.row.goodsId]">{{ scope.row.goodsName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总积分">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.totalPoint }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status">
            <template slot-scope="scope">
              <span v-if="scope.row.status ==='0'">待付款</span>
              <span v-if="scope.row.status ==='1'">支付完成</span>
              <span v-if="scope.row.status ==='2'">订单完成</span>
            </template>
          </el-table-column>

          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="pay(scope.row.id)" v-if="scope.row.status === '0'">付款</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="待付款" name="0">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="礼品名称" width="300">
            <template slot-scope="scope">
              <div  style="display: flex">
                <div style="flex: 2"><a :href="['/front/goodsDetail?id=' + scope.row.goodsId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/goodsDetail?id=' + scope.row.goodsId]">{{ scope.row.goodsName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总积分">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.totalPoint }}</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="pay(scope.row.id)">付款</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="支付完成" name="1">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="礼品名称" width="300">
            <template slot-scope="scope">
              <div  style="display: flex">
                <div style="flex: 2"><a :href="['/front/goodsDetail?id=' + scope.row.goodsId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/goodsDetail?id=' + scope.row.goodsId]">{{ scope.row.goodsName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总积分">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.totalPoint }}</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="preComment(scope.row.id)">评价</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="订单完成" name="2">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="礼品名称" width="300">
            <template slot-scope="scope">
              <div  style="display: flex">
                <div style="flex: 2"><a :href="['/front/goodsDetail?id=' + scope.row.goodsId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/goodsDetail?id=' + scope.row.goodsId]">{{ scope.row.goodsName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总积分">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.totalPoint }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
    </el-tabs>
    <!-- 弹窗   -->
    <el-dialog title="评价信息" :visible.sync="dialogFormVisible" width="30%"
               :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
      <el-form :model="entity">
        <el-form-item label="评价类型" label-width="120px">
          <el-radio v-model="entity.type" label="1" >好评</el-radio>
          <el-radio v-model="entity.type" label="2" >中评</el-radio>
          <el-radio v-model="entity.type" label="3" >差评</el-radio>
        </el-form-item>

        <el-form-item label="评价内容" label-width="120px">
          <el-input type="textarea" v-model="entity.content" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="comment">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import API from "@/utils/request";

export default {
  name: "cart",
  data() {
    return {
      user: {},
      pageNum: 1,
      pageSize: 10,
      total: 0,
      activeName: 'all',
      tableData: [],
      payData: [],
      entity: {},
      status: 'all',
      dialogFormVisible: false,
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    if (!this.user.id) {
      this.$message({
        type: 'warning',
        message: '请登录！'
      })
      return
    }
    this.load()
  },
  methods: {
    preComment(id) {
      this.entity = {}
      this.entity.id = id;
      this.dialogFormVisible = true
    },
    comment() {
      if (!this.entity.content) {
        this.$message({
          type: 'warning',
          message: '请填写内容'
        })
        return
      }
      API.post("/userWeb/sendOrderContent", this.entity).then(res => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '评价成功'
          })
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    handleClick(tab, event) {

      this.status = tab.name
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    load() {
      if(this.status === 'all') {
        this.status = ''
      }
      API.get("/userWeb/getOrder", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          status: this.status
        }
      }).then(res => {
        this.tableData = res.list.records
        this.total = res.list.total
      })
    },
    pay(id) {
      API.post("/userWeb/conversionGoods/" + id).then(res => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '付款成功'
          })
          this.load()
        } else {
          this.$message({
            type: 'error',
            message: res.message
          })
        }
      })
    }
  }
}
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
