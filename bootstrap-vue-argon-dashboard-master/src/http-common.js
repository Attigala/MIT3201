import axios from 'axios'

export default axios.create({
  // URL of the Spring Boot Server
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-type': 'application/json'
  }
})
