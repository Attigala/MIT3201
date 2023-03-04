<template>
    <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Register Guardian</h1>
              <p class="text-lead text-white">Fill the form to register as a guardian</p>
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
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8" >
          <b-card no-body class="bg-secondary border-0">
            <b-card-header class="bg-transparent pb-5">
            </b-card-header>
            <b-card-body class="px-lg-5 py-lg-5">
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-hat-3"
                              placeholder="First Name"
                              name="firstName"
                              :rules="{required: true}"
                              v-model="model.firstName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-hat-3"
                              placeholder="Last Name"
                              name="lastName"
                              :rules="{required: true}"
                              v-model="model.lastName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-mobile-button"
                              placeholder="contactNumber"
                              name="lastName"
                              :rules="{required: true}"
                              v-model="model.contactNumber">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-email-83"
                              placeholder="Email"
                              name="Email"
                              :rules="{required: true, email: true}"
                              v-model="model.email">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-user-run"
                              placeholder="NIC"
                              name="NIC"
                              :rules="{required: true, min: 10}"
                              v-model="model.nic">
                 </base-input>
                 <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-map-big"
                              placeholder="House Number"
                              name="houseNumber"
                              :rules="{required: true}"
                              v-model="model.houseNumber">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-map-big"
                              placeholder="Street 1 Name"
                              name="firstStreetName"
                              :rules="{required: true}"
                              v-model="model.firstStreetName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-map-big"
                              placeholder="Street 2 Name"
                              name="secondStreetName"
                              :rules="{required: true}"
                              v-model="model.secondStreetName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              prepend-icon="ni ni-map-big"
                              placeholder="City"
                              name="city"
                              :rules="{required: true}"
                              v-model="model.city">
                  </base-input>
                  <div class="text-center">
                    <b-button type="submit" variant="primary" class="mt-4">Submit Registration</b-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    </div>
</template>
<script>
import guardianDataService from '../../services/GuardianDataService'
export default{
  name: 'guardian',
  data () {
    return {
      model: {
        id: null,
        firstName: '',
        lastName: '',
        contactNumber: '',
        email: '',
        nic: '',
        relationship: '',
        houseNumber: '',
        firstStreetName: '',
        secondStreetName: '',
        city: ''
      }
    }
  },
  methods: {
    onSubmit () {
      var model = {
        firstName: this.model.firstName,
        lastName: this.model.lastName,
        contactNumber: this.model.contactNumber,
        email: this.model.email,
        nic: this.model.nic,
        relationship: this.model.relationship,
        houseNumber: this.model.houseNumber,
        firstStreetName: this.model.firstStreetName,
        secondStreetName: this.model.secondStreetName,
        city: this.model.city
      }
      guardianDataService.create(model).then(response => {
        this.model.id = response.data.id
        console.log(response.data)
        alert('Hello '+ model.firstName + ", you are registered with widumaga! Please login with your email " + model.email + " and your NIC " +model.nic + " as the password")
      }).catch(e => {
        alert('Something went wrong')
        console.log(e)
      })
    }
  }
}
</script>
