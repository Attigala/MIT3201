<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Add Hall</h1>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1"
             xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="Hall Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Hall Name"
                              v-model="hall.hallName">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="Building Number"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Building Number"
                              v-model="hall.buildingNo">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Capacity"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Capacity"
                              v-model="hall.capacity">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Hall</base-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
          </b-row>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import HallDataService from '../services/HallDataService'
export default{
  name: 'hall',
  data () {
    return {
      hall: {
        id: null,
        hallName: '',
        buildingNo: '',
        capacity: ''
      }
    }
  },
  methods: {
    onSubmit () {
      var hall = {
        hallName: this.hall.hallName,
        buildingNo: this.hall.buildingNo,
        capacity: this.hall.capacity
      }
      HallDataService.saveHall(hall).then(response => {
        this.hall.id = response.data.id
        console.log(response.data)
        alert('Hall added')
      }).catch(e => {
        alert('Something went wrong')
        console.log(e)
      })
    }
  },
  mounted() {
    if(localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
  }
}
</script>
