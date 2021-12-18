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

interface CurrentExecutingRequest extends AxiosRequestConfig {
    [index: string]: any;
}

// https://stackoverflow.com/questions/50461746/axios-how-to-cancel-request-inside-request-interceptor-properly/67266644#67266644

const currentExecutingRequests: CurrentExecutingRequest = {};

_axios.interceptors.request.use(
    (req) => {
        let originalRequest = req;
        const url: any = originalRequest.url;

        if (currentExecutingRequests[url]) {
            const source = currentExecutingRequests[url];
            delete currentExecutingRequests[url];
            source.cancel();
        }

        const CancelToken = _axios.CancelToken;
        const source = CancelToken.source();
        originalRequest.cancelToken = source.token;
        currentExecutingRequests[url] = source;

        // here you could add the authorization header to the request
        return originalRequest;
    },
    (err) => {
        return Promise.reject(err);
    }
);

_axios.interceptors.response.use(
    (response) => {
        if (currentExecutingRequests[response.request.responseURL]) {
            // here you clean the request
            delete currentExecutingRequests[response.request.responseURL];
        }

        return response;
    },
    (error) => {
        const { config } = error;
        const originalRequest = config;

        if (_axios.isCancel(error)) {
            // here you check if this is a cancelled request to drop it silently (without error)
            return new Promise(() => {});
        }

        if (currentExecutingRequests[originalRequest.url]) {
            // here you clean the request
            delete currentExecutingRequests[originalRequest.url];
        }

        // here you could check expired token and refresh it if necessary
        throw new _axios.Cancel(error.response.data.message);
    }
);




/**
 * GET 전용 axios
 * @param url
 * @param data
 */
async function get(url: string, data?: any) {
    try {
        const plainData = toRaw(unref(data)) || {}
        if (plainData) {
            url += '?' + Object.keys(plainData).map(function (k) {
                return encodeURIComponent(k) + '=' + encodeURI(plainData[k])
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

/**
 * POST전용 axios
 * @param url
 * @param data
 * @param config
 */
async function put(url: string, data?: any, config?: AxiosRequestConfig) {
    try {
        const plainData = toRaw(unref(data)) || {}

        const response = await _axios.put<{
            data: any,
            result: string,
            [key: string]: any
        }>(url, plainData, config)

        return response.data
    } catch (e) {
        return Promise.reject(e)
    }
}

/**
 * POST전용 axios
 * @param url
 * @param data
 * @param config
 */
async function remove(url: string, data?: any, config?: AxiosRequestConfig) {
    try {
        const plainData = toRaw(unref(data)) || {}

        const response = await _axios.delete<{
            data: any,
            result: string,
            [key: string]: any
        }>(url, plainData)

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