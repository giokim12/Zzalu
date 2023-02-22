import { apiInstance } from './index.js';
const api = apiInstance();

function getProfileStat(params, res, err) {
  api.get(`/statistics/member?username=${params}`).then(res).catch(err);
}

export { getProfileStat };
