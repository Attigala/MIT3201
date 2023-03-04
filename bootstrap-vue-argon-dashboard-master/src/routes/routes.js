import DashboardLayout from '@/views/Layout/DashboardLayout.vue';
import AuthLayout from '@/views/Pages/AuthLayout.vue';

import NotFound from '@/views/NotFoundPage.vue';

const routes = [
  {
    path: '/',
    redirect: 'dashboard',
    component: DashboardLayout,
    children: [
      {
        path: '/dashboard',
        name: 'dashboard',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "demo" */ '../views/Dashboard.vue')
      },
      {
        path: '/icons',
        name: 'icons',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Icons.vue')
      },
      {
        path: '/profile',
        name: 'profile',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/UserProfile.vue')
      },
      {
        path: '/maps',
        name: 'maps',
        component: () => import(/* webpackChunkName: "demo" */ '../views/GoogleMaps.vue')
      },
      {
        path: '/tables',
        name: 'tables',
        component: () => import(/* webpackChunkName: "demo" */ '../views/RegularTables.vue')
      },
      {
        path: '/guardians',
        name: 'guardians',
        component: () => import(/* webpackChunkName: "demo" */ '../views/GuardianList.vue')
      },
      {
        path: '/addStudentClass',
        name: 'addStudentClass',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddStudentClass.vue')
      },
      {
        path: '/studentClasses',
        name: 'StudentClasses',
        component: () => import(/* webpackChunkName: "demo" */ '../views/StudentClasses.vue')
      },
      {
        path: '/addHall',
        name: 'addHall',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddHall.vue')
      },
      {
        path: '/addTeacher',
        name: 'addTeacher',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddTeacher.vue')
      },
      {
        path: '/halls',
        name: 'Halls',
        component: () => import(/* webpackChunkName: "demo" */ '../views/HallList.vue')
      },
      {
        path: '/teachers',
        name: 'Teachers',
        component: () => import(/* webpackChunkName: "demo" */ '../views/TeacherList.vue')
      },
      {
        path: '/addClassContract',
        name: 'addClassContract',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddClassContract.vue')
      },
      {
        path: '/addStudentForGuardian',
        name: 'addStudentForGuardian',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddStudentForGuardian.vue')
      },
      {
        path: '/timetable',
        name: 'timetable',
        component: () => import(/* webpackChunkName: "demo" */ '../views/TimeTable.vue')
      },      
      {
        path: '/addSchedule',
        name: 'addSchedule',
        component: () => import(/* webpackChunkName: "demo" */ '../views/AddSchedule.vue')
      },
      {
        path: '/teacherHome',
        name: 'teacherHome',
        component: () => import(/* webpackChunkName: "demo" */ '../views/TeacherHome.vue')
      },
      {
        path: '/teacherClass',
        name: 'TeacherClass',
        component: () => import(/* webpackChunkName: "demo" */ '../views/TeacherClass.vue')
      },
      {
        path: '/studyMaterialUpload',
        name: 'StudyMaterialUpload',
        component: () => import('../views/StudyMaterialUpload.vue')
      },
      {
        path: '/teacherForum',
        name: 'ForumTeacher',
        component: () => import('../views/ForumTeacher.vue')
      },
      {
        path: '/studentHome',
        name: 'studentHome',
        component: () => import('../views/StudentHome.vue')
      },
      {
        path: '/studentClass',
        name: 'StudentClass',
        component: () => import('../views/StudentClass.vue'),
      },
      {
        path: "/ForumStudent",
        name: "ForumStudent",
        component: () => import('../views/ForumStudent.vue')
      },
      {
        path: "/forumAnswerStudent",
        name: "ForumAnswerStudent",
        component: () => import('../views/ForumAnswerStudent.vue')
      },
      {
        path: "/assignmentUpload",
        name: "AssignmentUpload",
        component: () => import('../views/AssignmentUpload.vue')
      },
      {
        path: "/addPayment",
        name:"addPayment",
        component: () =>import('../views/AddPayment.vue')
      },
      {
        path: "/paymentList",
        name: "paymentList",
        component: () =>import('../views/PaymentList.vue')
      },
      {
        path: "/rejectedPayments",
        name: "rejectedPayments",
        component: () => import('../views/RejectedPayments.vue')
      },
      {
        path: "/ClassRequest",
        name: "ClassRequest",
        component: () => import('../views/ClassRequest.vue')
      },
      {
        path: "/ClassRequestSchedule",
        name: "ClassRequestSchedule",
        component: () => import('../views/ClassRequestSchedule.vue')
      }, 
      {
        path: "/ManuallyScheduleOnsite",
        name: "ManuallyScheduleOnsite",
        component: () => import('../views/ManuallyScheduleOnsite.vue')
      },
      {
        path:"/ManuallyScheduleOnline",
        name: "ManuallyScheduleOnline",
        component: () => import('../views/ManuallyScheduleOnline.vue')
      }, 
      {
        path: "/ForumAnswerTeacher",
        name: "ForumAnswerTeacher",
        component: () => import('../views/ForumAnswerTeacher')
      },
      {
        path: "/AssignmentSubmission",
        name: "AssignmentSubmission",
        component: () => import('../views/AssignmentSubmission')
      },
      {
        path: "/TeacherSubmissionView",
        name: "TeacherSubmissionView",
        component: () => import('../views/TeacherSubmissionView')
      },
      {
        path: "/GuardianUnpaidList",
        name: "GuardianUnpaidList",
        component: () => import('../views/GuardianUnpaidList')
      }, 
      {
        path: "/UnpaidClassesList",
        name: "UnpaidClassesList",
        component: () => import('../views/UnpaidClassesList')
      },
      {
        path: "/SuspendedList",
        name: "SuspendedList",
        component: () => import('../views/SuspendedList')
      },
      {
        path: "/ClassYearlyStatisticsReport",
        name: "ClassYearlyStatisticsReport",
        component: () => import('../views/ClassYearlyStatisticsReport')
      }, 
      {
        path: "/MarksList",
        name: "MarksList",
        component: () => import('../views/MarksList')
      }, 
      {
        path: "/ClassRequestReport",
        name: "ClassRequestReport",
        component: () => import('../views/ClassRequestReport')
      },
      {
        path: "/MessageSubscriber",
        name: "MessageSubscriber",
        component: () => import('../views/MessageSubscriber')
      },
      {
        path: "/OnlineTimetable",
        name: "OnlineTimetable",
        component: () => import('../views/OnlineTimetable')
      }, 
      {
        path: "/PendingAssignment",
        name: "PendingAssignment",
        component: () => import('../views/PendingAssignment')
      }
    ]
  },
  {
    path: '/',
    redirect: 'login',
    component: AuthLayout,
    children: [
      {
        path: '/login',
        name: 'login',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Login.vue')
      },
      {
        path: '/register',
        name: 'register',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Register.vue')
      },
      {
        path: '/registerGuardians',
        name: 'registerGuardians',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/RegisterGuardian.vue')
      },
      {
        path: '/ClassSearch',
        name: 'ClassSearch',
        component: () => import('../views/Pages/ClassSearch.vue')
      },
      { path: '*', component: NotFound }
    ]
  }
];

export default routes;
