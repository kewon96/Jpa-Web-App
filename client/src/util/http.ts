import _axios, {AxiosRequestConfig} from "axios";
import {toRaw, unref} from "vue";

_axios.defaults.baseURL = 'http://localhost:3030';

// csrf 토큰을 담을 cookie의 이름과 header
// 통신하는 서버의 setting과 일치해야함
// _axios.defaults.xsrfCookieName = 'XSRF-TOKEN';
// _axios.defaults.xsrfHeaderName = 'X-CSRFToken';

/**
 * _axios.interceptors.request.use(
 *   function onFulfilled(config) {
 *     blockOn()
 *     const jwt = storage.get<string>(AuthKey.JWT)
 *     if (jwt) {
 *       config.headers.Authorization = 'Bearer ' + jwt
 *     }
 *     const token = storage.get<string>('token')
 *     config.headers.token = token
 *     return config
 *   },
 *   function onRejected(error) {
 *     blockOff()
 *     console.error('axios request error =>>', error)
 *     return Promise.reject(error)
 *   }
 * )
 * // TODO: 서버 접속 안될때처리
 * _axios.interceptors.response.use(
 *   function onFulfilled(response) {
 *     blockOff()
 *     return response
 *   },
 *   function onRejected(e) {
 *     blockOff()
 *     const msg = e?.response?.data?.message
 *     console.error(e)
 *     if ( e.response.data.code === 'jwt error') {
 *       storage.remove(AuthKey.JWT)
 *     }
 *     if (msg.includes('오류가 발생')) {
 *       alert(msg)
 *     } else {
 *       alert(msg || e)
 *     }
 *     return Promise.reject(e)
 *   }
 * )
 */

_axios.interceptors.response.use(
    function (response) {
        console.log(response)
        return response;
    },
    function (error) {
        alert(error.response.data.message);
        return error
    }
)

/**
 * GET 전용 axios
 * @param url
 * @param data
 */
async function get(url: string, data?: any) {
    try {
        const plainData = toRaw(unref(data)) || {}
        if (plainData) {
            // plainData.corpCd = plainData.corpCd || useAuth()?.user?.value?.corpCd
            url += '?' + Object.keys(plainData).map(function (k) {
                return encodeURIComponent(k) + '=' + encodeURIComponent(plainData[k])
            }).join('&')
        }

        const response = await _axios.get<{
            data: any,
            result: string,
            [key: string]: any
        }>(url)
        return response.data
    } catch (e) {
        console.error(e)
        return Promise.reject(e)
    }
}

/**
 * POST전용 axios
 * @param url
 * @param data
 * @param config
 */
async function post(url: string, data?: any, config?: AxiosRequestConfig) {
    try {
        const plainData = toRaw(unref(data)) || {}

        const response = await _axios.post<{
            data: any,
            result: string,
            [key: string]: any
        }>(url, plainData, config)

        return response.data
    } catch (e) {
        return Promise.reject(e)
    }
}

async function upload(url: string, saveData: any, uploadSaveData?: /*UploadSaveData*/ any, config?: AxiosRequestConfig) {
    try {
        const plainData = toRaw(unref(saveData)) || {}

        const formData = new FormData()
        if (uploadSaveData) {
            const uploadData = {
                addFileData : uploadSaveData.addFileData,
                removeFileData : uploadSaveData.removeFileData
            }
            formData.append('uploadData', new Blob([JSON.stringify(uploadData)], { type: "application/json" }))
            // plainData.addFileData = uploadSaveData.addFileData
            // plainData.removeFileData = uploadSaveData.removeFileData
            const fileList = uploadSaveData.files
            for (let i = 0; i < fileList.length ; i++) {
                formData.append('files', fileList[i])
            }
        }

        formData.append('data', new Blob([JSON.stringify(plainData)], { type: "application/json" }))

        const response = await _axios.post<{
            data: any,
            result: string,
            [key: string]: any
        }>(url, formData, {
            ...config,
            headers: {
                contentType: 'multipart/form-data'
            }
        })

        return response.data
    } catch (e) {
        return Promise.reject(e)
    }
}

const http = {
    get,
    post
}

export default http;