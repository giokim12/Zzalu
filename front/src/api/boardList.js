import { authApiInstance } from './index.js';
import { apiInstance } from './index.js';

// 로그인
const authapi = authApiInstance();
const api = apiInstance();

function getStoreList(params, data, res, err) {
    authapi.get(`/boards?gifId=${params}`)
        .then(res).catch(err)
}

function createBoard(params, datas, res, err) {
    authapi.post(`/boards`, datas)
        .then(res).catch(err)
}

function putBoardData(params, datas, res, err) {
    authapi.put(`/boards/gif/${datas[0]}`, datas[1])
        .then(res).catch(err)
}

function getUserBoard(params, res, err) {
    api.get(`/boards?username=${params}`)
        .then(res).catch(err)
}

function getBoard(params, res, err) {
    api.get(`/boards/${params}`)
        .then(res).catch(err)
}

function nameChange(datas, res, err) {
    authapi.put(`/boards/${datas[0]}`, datas[1])
        .then(res).catch(err)
}

function boardDel(datas, params, res, err) {
    authapi.delete(`/boards/${params}`)
        .then(res).catch(err)
}

function removeBoardJjal(datas, params, res, err) {
    // var result = JSON.stringify(params[1]);
    // authapi.delete(`/boards/gif/${params[0]}`, params[1])

    authapi.delete(`/boards/gif/${params[0]}`, { data: {gifIdList : params[1].gifIdList }})
        .then(res).catch(err)
}

function userstatistics(datas,params,res,err) {
    authapi.post(`/statistics/download?gifId=${params}`)
    .then(res).catch(err)
}

export { getStoreList, createBoard, putBoardData, getUserBoard, getBoard, nameChange, boardDel, removeBoardJjal, userstatistics }