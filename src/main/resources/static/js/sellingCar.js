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
