<template>
  <div class="page-container">
	<!--工具栏-->
	<div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
        <el-date-picker
          v-model="filters.fdate"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions">
        </el-date-picker>
			</el-form-item>
      <el-form-item >
          <el-select v-model="filters.subno" placeholder="科目编号" filterable clearable>
            <el-option v-for="item in subs" :key="item.sunno" :value="item.sunno" :label="item.subname"></el-option>
          </el-select>
        </el-form-item>
      <el-form-item>
        <el-input v-model="filters.userno" placeholder="客户编号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="filters.username" placeholder="客户名称" clearable></el-input>
      </el-form-item>
      <!-- <el-form-item>
        <el-input v-model="filters.nickname" placeholder="客户昵称" clearable></el-input>
      </el-form-item> -->
			<el-form-item>
				<kt-button icon="fa fa-search" :label="$t('action.search')" perms="money:moneychange:view" type="primary" @click="findPage(null)"/>
        <kt-button icon="fa fa-download" :label="$t('action.export')" perms="money:moneychange:view" type="primary" @click="exportExcel"/>
        <ExportExcel ref="export" :columns="columns" :table-data="exportData" :info="exportInfo"></ExportExcel>
			</el-form-item>
		</el-form>
	</div>
	<!--表格内容栏-->
	<kt-table
    :data="pageResult" :columns="columns" :showOperation="showOperation" @findPage="findPage">
	</kt-table>
  </div>
</template>

<script>
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import { format } from "@/utils/datetime"
import ExportExcel from "@/views/Core/ExportExcel"
export default {
	components: {
		KtTable,
		KtButton,
    ExportExcel
	},
	data() {
		return {
			size: 'small',
      acctlist: [],//账本列表
			filters: {
        fdate: '',
        acctno: '',
        userno: '',
        subno:'',
        username:'',
        nickname:''
			},
			columns: [
				{prop:"fdate", label:"日期", minWidth:100},
				{prop:"userno", label:"客户编号", minWidth:120},
				{prop:"username", label:"客户名称", minWidth:120},
        {prop:"acctno", label:"账户编号", minWidth:120},
        {prop:"acctname", label:"账户名称", minWidth:120},
        {prop:"subno", label:"科目编号", minWidth:100},
        {prop:"subname", label:"科目名称", minWidth:100},
        {prop:"chmoney", label:"变动金额", minWidth:120},
        {prop:"befmoney", label:"变动前金额", minWidth:100},
        {prop:"aftmoney", label:"变动后金额", minWidth:100},
        {prop:"memo", label:"备注", minWidth:100}
			],
			pageRequest: { pageNum: 1, pageSize: 50 },
      pageResult: {},
      showOperation:false,
      subs:[],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      exportData:[],
      exportInfo:{
        id:'record-table',
        name:'账户资金变动明细.xlsx'
      }
		}
	},
	methods: {
		// 获取分页数据
		findPage: function (data) {
			if(data !== null) {
				this.pageRequest = data.pageRequest
			}else{
        this.pageRequest = {pageNum: 1, pageSize: 50}
      }
			this.pageRequest.params = {
        'fdate@ge':this.filters.fdate == null ? '' : this.filters.fdate[0],
        'fdate@le':this.filters.fdate == null ? '' : this.filters.fdate[1],
        'acctno':this.filters.acctno,
        'subno':this.filters.subno,
        'userno':this.filters.userno,
        'username@like':this.filters.username,
        'nickname@like':this.filters.nickname
			}
			this.$api.moneychange.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data
			}).then(data!=null?data.callback:'')
		},
    exportExcel(){
      let para = { pageNum: 1, pageSize: 10000000 }
      para.params = {
        'fdate@ge':this.filters.fdate == null ? '' : this.filters.fdate[0],
        'fdate@le':this.filters.fdate == null ? '' : this.filters.fdate[1],
        'acctno':this.filters.acctno,
        'subno':this.filters.subno,
        'userno':this.filters.userno
			}
      this.$api.moneychange.findPage(para).then((res) => {
        this.exportData = res.data.content
        setTimeout(()=>{
          this.$refs.export.exportExcel();
        },500)
      })
    },
    findacct(){
		  this.$api.account.findList({}).then(res =>{
		    this.acctlist = res.data
      })
      this.$api.subject.findList({}).then(res =>{
        this.subs = res.data
      })
    }


	},
	mounted() {
	  this.findacct()
	}
}
</script>

<style scoped>

</style>
