import axios from 'axios';
import { Message } from 'element-ui';
import router from "../router/index";
import { refreshToken } from './api';


const request = axios.create({
  baseURL: 'http://127.0.0.1:8083/api/v1',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('accessToken');
    if (token) config.headers.Authorization =  token;
    config.headers['Access-Control-Allow-Origin'] = "*";
    return config;
}, error => {
  Promise.reject(error)
})

//标志当前是否正在刷洗token
let isNotRefreshing = true;
//请求队列
let requests = [];
// 响应拦截
request.interceptors.response.use(
  response => {
    if (response.data.code === 200) {
      return response;
    } else if (response.data.code === 400) {
      console.log("123");
      if (isNotRefreshing) {
        isNotRefreshing = false;
        return refreshToken({refreshToken: localStorage.getItem("refreshToken")}).then(res => {
          if(res.data.code === 200) {
            localStorage.clear();
            localStorage.setItem("accessToken", res.data.accessToken);
            localStorage.setItem("refreshToken", res.data.refreshToken);
            //执行requests队列中的请求，（requests中存的不是请求参数，而是请求的Promise函数，这里直接拿来执行就好）
            requests.forEach(run => run())
            //将请求队列置空
            requests = []
            //重新执行当前未执行成功的请求并返回
            return axios(config);
          }
          console.log("刷新失败");
          router.replace({name: 'login'});
        })
      } else {
        return new Promise(resolve => {
          //这里加入的是一个promise的解析函数，将响应的config配置对应解析的请求函数存到requests中，等到刷新token回调后再执行
          requests.push(() => {
              resolve(axios(config));
              router.replace({name: 'login'});
          })
        })
      }
    } else {
      return Message({
        message: response.data.msg,
        type: 'error',
        duration: 2000
      });
    }
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          localStorage.clear();
          router.replace({name: 'login'});
          break;
        case 500:
          Message({
            message: error.response.data.msg,
            type: 'error',
          });
          break;
        default:
          error.message = `连接错误${error.response.status}`
      }
    } else {
      // 超时处理
      if (JSON.stringify(error).includes('timeout')) {
        Message.error('服务器响应超时，请刷新当前页');
      }
      error.message('连接服务器失败');
    }
    Message.error(error.message);
    return Promise.resolve(error.response);
  }
)

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
  return new Promise((resolve, reject) =>{
    request.get(url, {
      params: params
    })
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}
/**
* post方法，对应post请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数]
*/
export function post(url, params) {
  return new Promise((resolve, reject) => {
    request.post(url, params) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}
/**
* post方法，对应post请求
* @param {*} type 
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数]
*/
export function postType(url, type, params) {
  return new Promise((resolve, reject) => {
    request.post(url + '/' + type, params) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
* post方法，对应post请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数]
*/
export function postUrl(url, params) {
  return new Promise((resolve, reject) => {
    let param = params.join('/')
    const completeUrl = `${url}/${param}`
    request.post(completeUrl, {}) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
* put方法，对应put请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数在url后面]
*/
export function put(url, params) {
  return new Promise((resolve, reject) => {
    let param = params.join('/')
    const completeUrl = `${url}/${param}`
    request.put(completeUrl, {}) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
* put方法，对应put请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数]
*/
export function putBodyId(url, id, params) {
  return new Promise((resolve, reject) => {
    const completeUrl = `${url}/${id}`
    request.put(completeUrl, params) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
* put方法，对应put请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数]
*/
export function putBody(url, params) {
  return new Promise((resolve, reject) => {
    request.put(url, params) //QS.stringify(params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}


/**
* Delete方法，对应Delete请求
* @param {String} url [请求的url地址]
* @param {Object} params [请求时携带的参数在url后面]
*/
export function Delete(url, params) {
  return new Promise((resolve, reject) => {
    let param = params.join('/')
    const completeUrl = `${url}/${param}`
    request.delete(completeUrl, {})
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
* Delete方法，对应Delete请求
* @param {String} url [请求的url地址]
* @param {Object} params
*/
export function DeleteBody(url, params) {
  return new Promise((resolve, reject) => {
    request.delete(url, {params})
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}
/**
* Delete方法，对应Delete请求
* @param {String} url [请求的url地址]
* @param {Object} params
*/
export function DeleteBody1(url, params) {
  return new Promise((resolve, reject) => {
    request.delete(url, params)
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

/**
 * get方法，对应get请求,直接在地址后面拼串的形式
 * @param {String} url [请求的url地址]
 * @param {Array} params [请求时携带的参数在url后面]
 */
export function getDynamicynamic(url, params) {
  return new Promise((resolve, reject) =>{
    let param = params.join('/')
    const completeUrl = `${url}/${param}`
    request.get(completeUrl, {})
    .then(res => {
      resolve(res.data)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}
/**
 * post方法，导出文件
 * @param {String} url [请求的url地址]
 * @param {String} params [请求时携带的参数]
 */
export function getFileUseBlobByPost(url, params = {}) {
	return new promise((resolve, reject)=> {
		request({
      method: 'post',
      url,
      data: params,
      responseType: 'blob'
    })
    .then(res => {
      resolve(res)
    })
    .catch(err => {
      reject(err.data)
    })
  })
}

