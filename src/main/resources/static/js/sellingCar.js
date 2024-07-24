async function registerSellingCar(formObj) {
    console.log(formObj)

    const response = await axios.post(`/sellingCar/register`, formObj)

    console.log(response)
    return response.data
}

async function getSellingCar({sellingCarId}){
    const result = await axios.get(`/sellingCar/get`, {params: {sellingCarId}})

    console.log(result.data)
    return result.data
}

// 추천 차량 조회
async function getRecommendSellingCar({page, size}){
    const result = await axios.get(`/sellingCar/recommend`, {params: {page}})

    console.log(result.data)
    return result.data
}

// 최근 본 차량 조회
async function getRecentlySeenSellingCar({page, size}){
    const result = await axios.get(`/sellingCar/recentlySeenCar`, {params: {page}})

    console.log(result.data)
    return result.data
}

async function modifySellingCar(formObj) {
    console.log(formObj)

    const response = await axios.post(`/sellingCar/modify`, formObj)
    return response.data
}

async function sendLike(formObj) {
    console.log(formObj)

    const response = await axios.post(`/sellingCar/like`, formObj)

    return response.data
}

