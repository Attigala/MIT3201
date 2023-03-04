<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
      </b-row>
    </base-header>
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
      <div class="hallList">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Payments Pending Approvals</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="payments"
                  @row-click = "download">
                  <el-table-column label="Id"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.id}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Class"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.className}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Student"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.student}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Guardian"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.guardian}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Month"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.month}}</span>
                    </badge>
                </template>
            </el-table-column>

            <el-table-column label="Payment Date"
                             prop="paymentDate"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2">{{row.paymentDate}}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Download"
                             prop="download"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <a :href="row.url"  style="color: #2ecc71"> Download </a>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Approve"
                             prop="approve"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="approve(row)"  type="primary" class="mt-4">Accept</base-button></span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Reject"
                             prop="reject"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="reject(row)" type="primary" class="mt-4">Reject</base-button></span>
                    </div>
                </template>
            </el-table-column>
        </el-table>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'PaymentList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      payments: []
    }
  },
  methods: {
    retrievePayments () {
      ClassContractDataService.getAllNewPayments().then(response => {
        this.payments = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    download(row) {
        console.log(row.id)
        ClassContractDataService.downloadReciept(row.id).then(response => {
            var FILE = window.URL.createObjectURL(new Blob([response.data]));
            console.log(FILE)
            var docUrl = document.createElement('x');
            docUrl.href = FILE;
            docUrl.setAttribute('download', 'file.pdf');
            document.body.appendChild(docUrl);
            docUrl.click();
        }).catch(e => {
            console.log(e)
        })
    },
    approve(row) {
      console.log(row.id) 
      ClassContractDataService.changeRecieptStatus(row.id, "ACCEPTED").then(response => {
        if(response.status === 200){
          alert('Payment Accepted');
          this.retrievePayments();
        }
      }).catch(e => {
        console.log(e)
      })
    },
    reject(row) {
      console.log(row.id)
      ClassContractDataService.changeRecieptStatus(row.id, "REJECTED").then(response => {
        if(response.status === 200){
          alert('Payment Rejected');
          this.retrievePayments();
        }
      }).catch(e => {
        console.log(e)
      })
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "admin" && localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
    this.retrievePayments()
  }
}
</script>
