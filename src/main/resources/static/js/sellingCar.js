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

async function cancelSellingCar(formObj) {
    console.log(formObj)

    const response = await axios.post(`/sellingCar/cancel`, formObj)

    return response.data
}

