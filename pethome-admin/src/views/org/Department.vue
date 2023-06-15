<template>
  <section>
    <!--查询工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="部门名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDepartments">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--展示列表-->
    <el-table :data="departments" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="160">
      </el-table-column>
      <el-table-column prop="sn" label="部门编号" width="300" sortable>
      </el-table-column>
      <el-table-column prop="name" label="部门名称" width="300" sortable>
      </el-table-column>
      <el-table-column label="部门状态" prop="state" width="300" sortable>
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.state != '0' ? 'success' :  'danger'"
              disable-transitions
              effect="light">
            {{ scope.row.state == '0' ? "已撤销" : "正常" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="manager.username" label="主管" min-width="180" sortable>
      </el-table-column>
      <el-table-column prop="parentDepartment.name" label="上级部门" min-width="100" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页工具条-->
    <el-col :span="24" class="toolbar">
      <el-button :disabled="this.sels.length===0" type="danger" @click="batchRemove">批量删除</el-button>
      <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          style="float:right;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </el-col>

    <!--编辑 & 新增界面-->
    <el-dialog :title="title" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="部门编号" prop="sn">
          <el-input v-model="editForm.sn" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="部门名称" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="部门主管" prop="managerId">
          <el-select v-model="editForm.managerId" placeholder="请选择主管">
            <el-option
                v-for="item in employees"
                :key="item.id"
                :label="item.username"
                :value="item.id">
              <span style="float: left">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="部门状态">
          <el-radio-group v-model="editForm.state">
            <el-radio class="radio" :label="1">正常</el-radio>
            <el-radio class="radio" :label="0">已撤销</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上级部门">
          <div class="block">
            <el-cascader
                v-model="editForm.parentId"
                :options="options"
                :props="{
                  checkStrictly: true,
                  value: 'id',
                  label: 'name'
                }"
                clearable></el-cascader>
          </div>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>

    </el-dialog>

  </section>
</template>

<script>
import util from '../../common/js/util'

export default {
  data() {
    return {
      title: '',
      filters: {
        keyword: ''
      },
      departments: [],
      total: 0,
      currentPage: 1,
      pageSize: 5,
      listLoading: false,
      //列表选中列
      sels: [],
      //编辑界面是否显示
      editFormVisible: false,
      editLoading: false,
      //无限极树参数
      options: [],

      editFormRules: {
        name: [
          {required: true, message: '请输入部门名称', trigger: 'blur'}
        ],
        sn: [
          {required: true, message: '请输入部门编号', trigger: 'blur'}
        ]
      },
      employees: [],
      //编辑界面数据
      editForm: {
        id: null,
        sn: null,
        name: '',
        dirPath: '',
        state: 1,
        managerId: '',
        parentId: null,
      },
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.getDepartments();
    },
    //选择每页显示记录条数
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getDepartments();
    },

    //删除单个
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        this.$http.delete('/department/' + row.id).then((res) => {
          this.listLoading = false;
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getDepartments();
        });
      }).catch(() => {
        this.$message({
          message: '操作取消',
          type: 'false'
        });
      });
    },

    //点击编辑
    handleEdit(row) {
      this.title = '编辑部门'

      //把当前行数据赋值给 addFrom，需要使用深拷贝（地址值不一样，其他属性都一样）
      this.editForm = Object.assign({}, row);
      //查询无限极部门树
      this.getDptTree();

      //显示编辑框
      this.editFormVisible = true;
      console.log(1111111111111)
      console.log(row)
    },
    //点击新增
    handleAdd() {
      this.title = '新增部门'

      //清空新增框中的默认数据
      this.editForm = {
        id: null,
        sn: null,
        name: '',
        dirPath: '',
        state: 1,
        managerId: '',
        parentId: null
      }
      //查询无限极部门树
      this.getDptTree();
      //显示编辑框
      this.editFormVisible = true;
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {

          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            //点击提交后  未接受响应前  提交按钮处于加载等待加载状态
            this.editLoading = true;

            //this.editForm.parentId 值为数组(级联导致)，需要取出其真正父级部门id   取数组末位即可
            //前端级联   回显时 其父级部门id是单个值   所以需要先将其统一设为数组进行处理
            let parent = []
            //是数组  则赋值给parent数组   不是数组则存入parent数组
            this.editForm.parentId instanceof Array ? parent = this.editForm.parentId : parent.push(this.editForm.parentId)
            this.editForm.parentId = parent[parent.length - 1]
            // console.log(this.editForm.parentId)

            //配置父级部门级联参数   array.join(separator)取出数组所有元素 并插以分隔符 拼接为字符串
            let self = '';
            if (this.editForm.id){
              //若id存在 即为编辑   于此就地拼接好dirPath
              self = '/' + this.editForm.id;
            }
            //若无id  则为新增  在后端 返回新增的主键 用主键id在后端拼接dirPath
            if (parent){
             this.editForm.dirPath = parent.join('/')+ self
            }else
              this.editForm.dirPath = self


            let request = this.$http.put('/department', this.editForm)
            request.then((res) => {
              if (res.data.success) {
                this.$message({
                  message: res.data.message,
                  type: 'success'
                });
              } else {
                this.$message({
                  message: res.data.message,
                  type: 'error'
                });
              }
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '系统繁忙，请联络管理员'
              });
            });
            //响应完毕 关闭编辑窗口
            this.editFormVisible = false;
            this.getDepartments();
            //接受服务器响应/未响应后 结束等待提交状态
            this.editLoading = false;
          });
        } else {
          //校验失败
          return false;
        }
      });
    },
    //复选框
    selsChange: function (val) {
      this.sels = [];
      if (val != null && val.length > 0) {
        for (var i = 0; i < val.length; i++) {
          //往sels数组中存值
          this.sels.push(val[i].id);
        }
      }
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id).toString();
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        this.$http.patch('/department', this.sels).then((res) => {
          this.listLoading = false;
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getDepartments();
        });
      }).catch(() => {
        this.$message({
          message: '操作取消',
          type: 'error'
        });
      });
    },
    //获取部门列表
    getDepartments() {
      let para = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        keyword: this.filters.keyword
      };
      this.listLoading = true;

      this.$http.post('/department', para).then((res) => {
        this.total = res.data.total;
        this.departments = res.data.list;
        this.listLoading = false;

      });

    },
    //获取无限极树部门列表  用于回显
    getDptTree() {
      if (this.editForm.id ){
        console.log(this.editForm.id)
        //编辑时
        this.$http.get('/department/dptTree/'+this.editForm.id).then((res) => {
          this.options = res.data;
        });
      }else{
        //新增时
        this.$http.get('/department/dptTree').then((res) => {
          this.options = res.data;
        });
      }

    },
    //获取员工表   设置主管
    getManager() {
      this.$http.get('/employee').then((res) => {
        this.employees = res.data;
        console.log("employees-----------" + res.data)
      });
    },
  },
  mounted() {
    this.getDepartments();
    this.getManager()
  }
}

</script>

<style scoped>

</style>