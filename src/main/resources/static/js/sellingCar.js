async function registerSellingCar(formObj) {

    console.log("register SellingCar....................")
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

async function registerBuyRequest(formObj) {

    console.log("register BuyRequest....................")
    console.log(formObj)

    const response = await axios.post(`/buyRequest/register`, formObj)

    console.log(response)
    return response.data
}

async function getList({carId, sellingCarId, page, size}){
    // goLast : 마지막 페이지 호출 여부

    console.log({carId, sellingCarId, page, size})

    const result = await axios.get(`/buyRequest/list`, {params: {sellingCarId, page, size}})

    console.log(result.data)

    return result.data
}