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
      <div class="unpaidList">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Payments Due for This Month</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="unpaids">
            <el-table-column label="Student Full Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.studentName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Class"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.classDetails}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Guardian Name"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.guardianName}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.studentContactNumber}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Suspend"
                             prop="approve"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="suspendGuardian(row)"  type="primary" class="mt-4" :disabled = "row.suspended === 1">Guardian</base-button></span>
                        <span class="completion mr-2"><base-button  @click="suspendStudent(row)"  type="primary" class="mt-4" :disabled = "row.suspended === 1"> Student Class</base-button></span>
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
import GuardianDataService from '../services/GuardianDataService'
import ClassPaymentDataService from '../services/ClassPaymentDataService'
import classContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'UnpaidClassesList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      unpaids: [],
      role: null,
      user: null
    }
  },
  methods: {
    retrieveGuardians () {
      ClassPaymentDataService.getUnpaidForMonth().then(response => {
        this.unpaids = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    suspendGuardian(row) {
        classContractDataService.suspendGuardian(row.getGuardianId).then(response => {
            alert("Guardian suspended along with all their students");
        }).catch(e=>{
            console.log(e)
        })
    },
    suspendStudent(row) {
        classContractDataService.suspendStudentClass(row.studentId, row.classContractId).then(response => {
            alert("Student suspended from class")
        }).catch(e=> {
            console.log(e)
        })
    }
  },
  mounted () {
    this.user = localStorage.getItem("email")
    if(localStorage.getItem("role") === null || localStorage.getItem("user") === null ){
        this.$router.push({ name: 'login' })
    }
    if(localStorage.getItem("role") !== "manager"){
       this.$router.push({ name: 'login' })
    }

    this.retrieveGuardians()

  }
}
</script>
