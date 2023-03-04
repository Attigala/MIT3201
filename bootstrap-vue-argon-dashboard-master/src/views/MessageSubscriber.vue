<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Send a Message to Requesters</h1>
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
                  <b-form-group label="Message" label-class="form-control-label" class="mb-0" label-for="message-textarea">
                  <b-form-textarea rows="4" id="message-textarea" placeholder="Type your message" v-model="message"> </b-form-textarea>
                  </b-form-group>
                  <div class="form-group">
                        <label for="teacher"> Request </label>
                        <select v-model="requested">
                            <option disabled value="">Please select one</option>
                            <option v-for="request in requests" :value="request" :key="request.id">{{request.subject}} {{request.grade}} {{request.medium}}</option>
                        </select>
                  </div>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Send Message</base-button>
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
import GuardianDataService from '../services/GuardianDataService'
import ClassPaymentDataService from '../services/ClassPaymentDataService'
import classContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'MessageSubscriber',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      requests: [],
      year: new Date().getFullYear(),
      requested: null,
      message: '',
      role: null,
      user: null
    }
  },
  methods: {
    retrievePersons() {
        classContractDataService.getRequestsForYear().then(response => {
            this.requests = response.data
        }).catch(e=> {
            console.log(e)
        })
    },
    onSubmit() {
        var messageRequest = {
            id : this.requested.id,
            subject: this.requested.subject,
            medium: this.requested.medium,
            grade: this.requested.grade,
            message: this.message
        }
        console.log(messageRequest)
        classContractDataService.saveMessageToRequester(messageRequest).then(response => {
            alert("Message will be sent to requesters")
        }).catch(e => {
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
    this.retrievePersons()
  }
}
</script>
