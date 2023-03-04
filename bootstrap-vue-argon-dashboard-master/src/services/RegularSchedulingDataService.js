import http from '../http-common'

class RegularSchedulingDataService {
  saveSchedule (schedule) {
    return http.post('/regularSchedule', schedule)
  }

  getSchedules(start, end, hallId) {
    return http.get('/schedule/find?start='+start+"&end="+end+"&hallId="+hallId)
  }

  getUnscheduledOnsiteRequests() {
    return http.get('/classContracts/unscheduled/onsite')
  }

  getOnlineSchedules(start, end) {
    return http.get('/schedule/online/find?start='+start+"&end="+end)
  }
}
export default new RegularSchedulingDataService()
