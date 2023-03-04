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
            <h3 class="mb-0 text-white">New Class Requests: {{year}}</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="requests">
            <el-table-column label="Subject"
                             min-width="170px"
                             prop="subject">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.subject}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="Medium"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.medium}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Grade"
                             min-width="170px"
                             prop="grade">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.grade}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="No. of Requests"
                             min-width="170px"
                             prop="frequency">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.frequency}}</span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Requested Persons: {{year}}</h3>
        </b-card-header>
        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="persons">
            <el-table-column label="Subject"
                             min-width="310px"
                             prop="subject">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.subject}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Medium"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.medium}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Grade"
                             min-width="170px"
                             prop="grade">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.grade}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Name"
                             min-width="170px"
                             prop="name">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.name}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="email"
                             min-width="170px"
                             prop="email">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.email}}</span>
                    </badge>
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
  name: 'ClassRequestReport',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      requests: [],
      persons: [],
      year: new Date().getFullYear(),
      role: null,
      user: null
    }
  },
  methods: {
    retrieveStatistics () {
      classContractDataService.getRequested().then(response => {
        this.requests = response.data
        console.log(this.classStatistics)
      }).catch(e => {
        console.log(e)
      })
    },
    retrievePersons() {
        classContractDataService.getRequestedPersons().then(response => {
            this.persons = response.data
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

    this.retrieveStatistics()
    this.retrievePersons()
  }
}
</script>
