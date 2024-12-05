<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="订单号">
        <el-input v-model="dataVo.orderNo" placeholder="请输入订单号"/>
      </el-form-item>
      <el-form-item label="礼品名称">
        <el-input v-model="dataVo.goodsName" placeholder="请输入礼品名称"/>
      </el-form-item>
      <el-form-item label="订单状态">
        <el-select v-model="dataVo.status" placeholder="请选择订单状态">
          <el-option label="待付款" value="0"></el-option>
          <el-option label="已支付" value="1"></el-option>
          <el-option label="订单完成 " value="2"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
        :data="list"
        border
        fit
        highlight-current-row
        empty-text="暂无数据"
    >
      <el-table-column align="center" label="礼品封面">
        <template slot-scope="scope">
          <img
              style="width: 80px; height: 80px"
              :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" label="订单号"/>
      <el-table-column prop="goodsName" label="礼品名称"/>
      <el-table-column prop="totalPoint" label="兑换积分"/>
      <el-table-column label="订单状态" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status ==='0'">待付款</span>
          <span v-if="scope.row.status ==='1'">已支付</span>
          <span v-if="scope.row.status ==='2'">订单完成</span>
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="购买用户"/>
      <el-table-column prop="createTime" label="购买时间"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="page"
        style="padding: 30px 0; text-align: center;"
        :page-size="limit"
        layout="total, prev, pager, next, jumper"
        :total="total"
        @current-change="getList"
    />
  </div>
</template>
<script>
import API from '../../utils/request'

const url = "/order/"
export default {
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值

      API.post(url+`pageQuery/${this.page}/${this.limit}`, this.dataVo).then(response => {
        this.list = response.list.records
        this.total = response.list.total
      })
    },



    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        API.delete(url+"delete/"+id)
            .then(response => { // 删除成功执行的方法
              if (response.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除成功！'
                })
                // 刷新表格
                this.getList()
              } else {
                this.$message({
                  type: 'error',
                  message: '删除失败！'
                })
              }
            })
      })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.page = 1; // 当前页
      this.limit = 10; // 当前页
      this.getList()
    }
  }
}
</script>
