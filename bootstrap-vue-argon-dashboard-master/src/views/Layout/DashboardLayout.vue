<template>
  <div class="wrapper">
    <notifications></notifications>
    <side-bar>
      <template slot="links">

        <sidebar-item v-if="role === 'admin' ||role=== 'manager'"
                  :link="{
                    name: 'Payments',
                    path: '/paymentList',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'admin' ||role=== 'manager'"
                  :link="{
                    name: 'ClassRequest',
                    path: '/ClassRequest',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'ClassRequestSchedule',
                    path: '/ClassRequestSchedule',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Manually Schedule',
                    path: '/ManuallyScheduleOnsite',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>

        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Manually Schedule Online',
                    path: '/ManuallyScheduleOnline',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Register Child',
                    path: '/addStudentForGuardian',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Classes',
                    path: '/studentClasses',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Pending Assignment',
                    path: '/PendingAssignment',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role !== 'student' && role != 'teacher'"
                  :link="{
                    name: 'Add Student To Class',
                    path: '/addStudentClass',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role !== 'guardian' && role != 'teacher' && role !== 'student'" 
                  :link="{
                    name: 'Guardians',
                    path: '/guardians',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Add Hall',
                    path: '/addHall',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role !== 'guardian' && role != 'teacher'  && role !== 'student'"
                  :link="{
                    name: 'View Halls',
                    path: '/halls',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role !== 'guardian' && role != 'teacher'  && role !== 'student'"
                  :link="{
                    name: 'Onsite Timetable',
                    path: '/timetable',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        
        </sidebar-item>
        <sidebar-item v-if="role !== 'guardian' && role != 'teacher'  && role !== 'student'"
                  :link="{
                    name: 'Online Timetable',
                    path: '/OnlineTimetable',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Add Teacher',
                    path: '/addTeacher',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Add Class Contract',
                    path: '/addClassContract',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Add Schedule',
                    path: '/addSchedule',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role !== 'guardian' && role != 'teacher'  && role !== 'student'"
                  :link="{
                    name: 'View Teachers',
                    path: '/teachers',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>

        <sidebar-item v-if="role === 'teacher'"
                  :link="{
                    name: 'Home',
                    path: '/teacherHome',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>

        <sidebar-item v-if="role === 'student'"
                  :link="{
                    name: 'Student Home',
                    path: '/studentHome',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Add Payment',
                    path: '/addPayment',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Rejected Payments',
                    path: '/rejectedPayments',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'guardian'"
                  :link="{
                    name: 'Payments Due',
                    path: '/GuardianUnpaidList',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>

        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'All Payments Due',
                    path: '/UnpaidClassesList',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'New Class Requests',
                    path: '/ClassRequestReport',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>        
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Message Requester',
                    path: '/MessageSubscriber',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>

        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Class Statistics Report',
                    path: '/ClassYearlyStatisticsReport',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'manager'"
                  :link="{
                    name: 'Suspensions',
                    path: '/SuspendedList',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>

        
        
        <sidebar-item
                  :link="{
                    name: 'Logout',
                    path: '/login',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Study Material',
                    path: '/studyMaterialUpload',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Teacher Forum',
                    path: '/teacherForum',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Teacher Forum',
                    path: '/assignmentUpload',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'AssignmentSubmission',
                    path: '/AssignmentSubmission',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'Student Forum',
                    path: '/ForumStudent',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'TeacherSubmissionView',
                    path: '/TeacherSubmissionView',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        <sidebar-item v-if="role === 'test'"
                  :link="{
                    name: 'MarksList',
                    path: '/MarksList',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
        
      </template>

      <template slot="links-after">
        <hr class="my-3">
      </template>
    </side-bar>
    <div class="main-content">
      <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar>

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>
      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template>
<script>
  /* eslint-disable no-new */
  import PerfectScrollbar from 'perfect-scrollbar';
  import 'perfect-scrollbar/css/perfect-scrollbar.css';

  function hasElement(className) {
    return document.getElementsByClassName(className).length > 0;
  }

  function initScrollbar(className) {
    if (hasElement(className)) {
      new PerfectScrollbar(`.${className}`);
    } else {
      // try to init it later in case this component is loaded async
      setTimeout(() => {
        initScrollbar(className);
      }, 100);
    }
  }

  import DashboardNavbar from './DashboardNavbar.vue';
  import ContentFooter from './ContentFooter.vue';
  import DashboardContent from './Content.vue';
import { FadeTransition } from 'vue2-transitions';

  export default {
    components: {
      FadeTransition,
      DashboardNavbar,
      ContentFooter,
      DashboardContent
    },
    data() {
      return {
        role: ''
      }

    },
    methods: {
      initScrollbar() {
        let isWindows = navigator.platform.startsWith('Win');
        if (isWindows) {
          initScrollbar('sidenav');
        }
      }
    },
    mounted() {
      this.initScrollbar()
      this.role = localStorage.getItem("role")
      if(this.role == null){
        this.role = 'admin'
      }
    }
  };
</script>
<style lang="scss">
</style>
