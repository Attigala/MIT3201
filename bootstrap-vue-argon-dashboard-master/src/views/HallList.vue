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
            <h3 class="mb-0 text-white">Halls</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="halls">
            <el-table-column label="Hall Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.hallName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Building Number"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.buildingNo}}</span>
                    </badge>
                </template>
            </el-table-column>

            <el-table-column label="Capacity"
                             prop="email"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2">{{row.capacity}}</span>
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
import HallDataService from '../services/HallDataService'
  import { Table, TableColumn} from 'element-ui'
export default {
  name: 'guardianList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      halls: []
    }
  },
  methods: {
    retrievehalls () {
      HallDataService.getAll().then(response => {
        this.halls = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "admin" && localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
    this.retrievehalls()
  }
}
</script>
